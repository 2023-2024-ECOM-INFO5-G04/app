package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.domain.User;
import fr.polytech.g4.ecom23.repository.MedecinRepository;
import fr.polytech.g4.ecom23.service.MedecinService;
import fr.polytech.g4.ecom23.service.UserService;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.mapper.MedecinMapper;
import fr.polytech.g4.ecom23.service.mapper.MedecinMapperImpl;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Medecin}.
 */
@RestController
@RequestMapping("/api")
public class MedecinResource {

    private final Logger log = LoggerFactory.getLogger(MedecinResource.class);

    private static final String ENTITY_NAME = "medecin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedecinService medecinService;

    private final UserService userService;

    private final MedecinRepository medecinRepository;

    private final MedecinMapper medecinMapper;

    public MedecinResource(MedecinService medecinService, UserService userService, MedecinRepository medecinRepository, MedecinMapper medecinMapper) {
        this.medecinService = medecinService;
        this.userService = userService;
        this.medecinRepository = medecinRepository;
        this.medecinMapper = medecinMapper;
    }

    /**
     * {@code POST  /medecins} : Create a new medecin.
     *
     * @param medecinDTO the medecinDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medecinDTO, or with status {@code 400 (Bad Request)} if the medecin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medecins")
    public ResponseEntity<MedecinDTO> createMedecin(@RequestBody MedecinDTO medecinDTO) throws URISyntaxException {
        log.debug("REST request to save Medecin : {}", medecinDTO);
        if (medecinDTO.getId() != null) {
            throw new BadRequestAlertException("A new medecin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedecinDTO result = medecinService.save(medecinDTO);
        return ResponseEntity
            .created(new URI("/api/medecins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medecins/:id} : Updates an existing medecin.
     *
     * @param id the id of the medecinDTO to save.
     * @param medecinDTO the medecinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medecinDTO,
     * or with status {@code 400 (Bad Request)} if the medecinDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medecinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medecins/{id}")
    public ResponseEntity<MedecinDTO> updateMedecin(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MedecinDTO medecinDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Medecin : {}, {}", id, medecinDTO);
        if (medecinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medecinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medecinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedecinDTO result = medecinService.update(medecinDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medecinDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medecins/:id} : Partial updates given fields of an existing medecin, field will ignore if it is null
     *
     * @param id the id of the medecinDTO to save.
     * @param medecinDTO the medecinDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medecinDTO,
     * or with status {@code 400 (Bad Request)} if the medecinDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medecinDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medecinDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medecins/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedecinDTO> partialUpdateMedecin(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MedecinDTO medecinDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medecin partially : {}, {}", id, medecinDTO);
        if (medecinDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medecinDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medecinRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedecinDTO> result = medecinService.partialUpdate(medecinDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medecinDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medecins} : get all the medecins.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medecins in body.
     */
    @GetMapping("/medecins")
    public List<MedecinDTO> getAllMedecins(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Medecins");
        return medecinService.findAll();
    }

    /**
     * {@code GET  /medecins/:id} : get the "id" medecin.
     *
     * @param id the id of the medecinDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medecinDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medecins/{id}")
    public ResponseEntity<MedecinDTO> getMedecin(@PathVariable Long id) {
        log.debug("REST request to get Medecin : {}", id);
        Optional<MedecinDTO> medecinDTO = medecinService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medecinDTO);
    }

    /**
     * {@code DELETE  /medecins/:id} : delete the "id" medecin.
     *
     * @param id the id of the medecinDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medecins/{id}")
    public ResponseEntity<Void> deleteMedecin(@PathVariable Long id) {
        log.debug("REST request to delete Medecin : {}", id);
        medecinService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/medecins/user/{id}")
    public ResponseEntity<MedecinDTO> getMedecinByUser(@PathVariable Long id){
        log.debug("REST request to get Medecin with user: {}", id);
        List<MedecinDTO> medecins = medecinService.findAll();
        for (MedecinDTO medecin : medecins) {
            if (medecin.getUser().getId().equals(id)){
                return ResponseEntity.ok(medecin);
            }
        }
            return ResponseEntity.notFound().build();

    }
}
