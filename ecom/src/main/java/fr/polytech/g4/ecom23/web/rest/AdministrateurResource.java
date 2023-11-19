package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.AdministrateurRepository;
import fr.polytech.g4.ecom23.service.AdministrateurService;
import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Administrateur}.
 */
@RestController
@RequestMapping("/api")
public class AdministrateurResource {

    private final Logger log = LoggerFactory.getLogger(AdministrateurResource.class);

    private static final String ENTITY_NAME = "administrateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdministrateurService administrateurService;

    private final AdministrateurRepository administrateurRepository;

    public AdministrateurResource(AdministrateurService administrateurService, AdministrateurRepository administrateurRepository) {
        this.administrateurService = administrateurService;
        this.administrateurRepository = administrateurRepository;
    }

    /**
     * {@code POST  /administrateurs} : Create a new administrateur.
     *
     * @param administrateurDTO the administrateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new administrateurDTO, or with status {@code 400 (Bad Request)} if the administrateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/administrateurs")
    public ResponseEntity<AdministrateurDTO> createAdministrateur(@Valid @RequestBody AdministrateurDTO administrateurDTO)
        throws URISyntaxException {
        log.debug("REST request to save Administrateur : {}", administrateurDTO);
        if (administrateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new administrateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdministrateurDTO result = administrateurService.save(administrateurDTO);
        return ResponseEntity
            .created(new URI("/api/administrateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /administrateurs/:id} : Updates an existing administrateur.
     *
     * @param id the id of the administrateurDTO to save.
     * @param administrateurDTO the administrateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrateurDTO,
     * or with status {@code 400 (Bad Request)} if the administrateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the administrateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/administrateurs/{id}")
    public ResponseEntity<AdministrateurDTO> updateAdministrateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AdministrateurDTO administrateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Administrateur : {}, {}", id, administrateurDTO);
        if (administrateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AdministrateurDTO result = administrateurService.update(administrateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /administrateurs/:id} : Partial updates given fields of an existing administrateur, field will ignore if it is null
     *
     * @param id the id of the administrateurDTO to save.
     * @param administrateurDTO the administrateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated administrateurDTO,
     * or with status {@code 400 (Bad Request)} if the administrateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the administrateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the administrateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/administrateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AdministrateurDTO> partialUpdateAdministrateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AdministrateurDTO administrateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Administrateur partially : {}, {}", id, administrateurDTO);
        if (administrateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, administrateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!administrateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AdministrateurDTO> result = administrateurService.partialUpdate(administrateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, administrateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /administrateurs} : get all the administrateurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of administrateurs in body.
     */
    @GetMapping("/administrateurs")
    public List<AdministrateurDTO> getAllAdministrateurs() {
        log.debug("REST request to get all Administrateurs");
        return administrateurService.findAll();
    }

    /**
     * {@code GET  /administrateurs/:id} : get the "id" administrateur.
     *
     * @param id the id of the administrateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the administrateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/administrateurs/{id}")
    public ResponseEntity<AdministrateurDTO> getAdministrateur(@PathVariable Long id) {
        log.debug("REST request to get Administrateur : {}", id);
        Optional<AdministrateurDTO> administrateurDTO = administrateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(administrateurDTO);
    }

    /**
     * {@code DELETE  /administrateurs/:id} : delete the "id" administrateur.
     *
     * @param id the id of the administrateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/administrateurs/{id}")
    public ResponseEntity<Void> deleteAdministrateur(@PathVariable Long id) {
        log.debug("REST request to delete Administrateur : {}", id);
        administrateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
