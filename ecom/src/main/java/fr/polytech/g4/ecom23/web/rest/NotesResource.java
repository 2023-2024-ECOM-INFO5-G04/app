package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.NotesRepository;
import fr.polytech.g4.ecom23.service.NotesService;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.dto.NotesDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Notes}.
 */
@RestController
@RequestMapping("/api")
public class NotesResource {

    private final Logger log = LoggerFactory.getLogger(NotesResource.class);

    private static final String ENTITY_NAME = "notes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NotesService notesService;

    private final NotesRepository notesRepository;

    public NotesResource(NotesService notesService, NotesRepository notesRepository) {
        this.notesService = notesService;
        this.notesRepository = notesRepository;
    }

    /**
     * {@code POST  /notes} : Create a new notes.
     *
     * @param notesDTO the notesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new notesDTO, or with status {@code 400 (Bad Request)} if the notes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/notes")
    public ResponseEntity<NotesDTO> createNotes(@RequestBody NotesDTO notesDTO) throws URISyntaxException {
        log.debug("REST request to save Notes : {}", notesDTO);
        if (notesDTO.getId() != null) {
            throw new BadRequestAlertException("A new notes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NotesDTO result = notesService.save(notesDTO);
        return ResponseEntity
            .created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /notes/:id} : Updates an existing notes.
     *
     * @param id the id of the notesDTO to save.
     * @param notesDTO the notesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notesDTO,
     * or with status {@code 400 (Bad Request)} if the notesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the notesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/notes/{id}")
    public ResponseEntity<NotesDTO> updateNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotesDTO notesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Notes : {}, {}", id, notesDTO);
        if (notesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NotesDTO result = notesService.update(notesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /notes/:id} : Partial updates given fields of an existing notes, field will ignore if it is null
     *
     * @param id the id of the notesDTO to save.
     * @param notesDTO the notesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated notesDTO,
     * or with status {@code 400 (Bad Request)} if the notesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the notesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the notesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/notes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NotesDTO> partialUpdateNotes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NotesDTO notesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Notes partially : {}, {}", id, notesDTO);
        if (notesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, notesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!notesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NotesDTO> result = notesService.partialUpdate(notesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /notes} : get all the notes, filtered by parameters 'medecin' and 'patient'
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of notes in body.
     */
    @GetMapping("/notes")
    public List<NotesDTO> getAllNotes(@RequestParam(name = "medecin", defaultValue = "") Long medecinId, @RequestParam(name = "patient", defaultValue = "") Long patientId) {
        log.debug("REST request to get Notes");
        List<NotesDTO> list = notesService.findAll();
        List<NotesDTO> filteredList = new LinkedList<NotesDTO>();
        for (NotesDTO n : list) {
            MedecinDTO medecinDTO = n.getMedecin();
            PatientDTO patientDTO = n.getPatient();
            if (medecinDTO == null || patientDTO == null)
                continue;
            if ((medecinId == null || n.getMedecin().getId().equals(medecinId)) && (patientId == null || n.getPatient().getId().equals(patientId)))
                filteredList.add(n);
        }

        return filteredList;
    }

    /**
     * {@code GET  /notes/:id} : get the "id" notes.
     *
     * @param id the id of the notesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the notesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/notes/{id}")
    public ResponseEntity<NotesDTO> getNotes(@PathVariable Long id) {
        log.debug("REST request to get Notes : {}", id);
        Optional<NotesDTO> notesDTO = notesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(notesDTO);
    }

    /**
     * {@code DELETE  /notes/:id} : delete the "id" notes.
     *
     * @param id the id of the notesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/notes/{id}")
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id) {
        log.debug("REST request to delete Notes : {}", id);
        notesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PatchMapping("/notes/{id}/commentaire")
    public ResponseEntity<NotesDTO> updateCommentaire(@PathVariable Long id, @RequestParam String commentaire) {
        log.debug("REST request to update commentaire for Notes : {}", id);
        if (!notesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        Optional<NotesDTO> result = notesService.findOne(id);
        NotesDTO notesDTO = result.get();
        notesDTO.setCommentaire(commentaire);
        result = notesService.partialUpdate(notesDTO);
        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, notesDTO.getId().toString())
        );
    }

}
