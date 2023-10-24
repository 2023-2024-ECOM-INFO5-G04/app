package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.EtablissementRepository;
import fr.polytech.g4.ecom23.service.EtablissementService;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Etablissement}.
 */
@RestController
@RequestMapping("/api")
public class EtablissementResource {

    private final Logger log = LoggerFactory.getLogger(EtablissementResource.class);

    private static final String ENTITY_NAME = "etablissement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EtablissementService etablissementService;

    private final EtablissementRepository etablissementRepository;

    public EtablissementResource(EtablissementService etablissementService, EtablissementRepository etablissementRepository) {
        this.etablissementService = etablissementService;
        this.etablissementRepository = etablissementRepository;
    }

    /**
     * {@code POST  /etablissements} : Create a new etablissement.
     *
     * @param etablissementDTO the etablissementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new etablissementDTO, or with status {@code 400 (Bad Request)} if the etablissement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/etablissements")
    public ResponseEntity<EtablissementDTO> createEtablissement(@Valid @RequestBody EtablissementDTO etablissementDTO)
        throws URISyntaxException {
        log.debug("REST request to save Etablissement : {}", etablissementDTO);
        if (etablissementDTO.getId() != null) {
            throw new BadRequestAlertException("A new etablissement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EtablissementDTO result = etablissementService.save(etablissementDTO);
        return ResponseEntity
            .created(new URI("/api/etablissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /etablissements/:id} : Updates an existing etablissement.
     *
     * @param id the id of the etablissementDTO to save.
     * @param etablissementDTO the etablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablissementDTO,
     * or with status {@code 400 (Bad Request)} if the etablissementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the etablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/etablissements/{id}")
    public ResponseEntity<EtablissementDTO> updateEtablissement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EtablissementDTO etablissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Etablissement : {}, {}", id, etablissementDTO);
        if (etablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EtablissementDTO result = etablissementService.update(etablissementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablissementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /etablissements/:id} : Partial updates given fields of an existing etablissement, field will ignore if it is null
     *
     * @param id the id of the etablissementDTO to save.
     * @param etablissementDTO the etablissementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated etablissementDTO,
     * or with status {@code 400 (Bad Request)} if the etablissementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the etablissementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the etablissementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/etablissements/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EtablissementDTO> partialUpdateEtablissement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EtablissementDTO etablissementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Etablissement partially : {}, {}", id, etablissementDTO);
        if (etablissementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, etablissementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!etablissementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EtablissementDTO> result = etablissementService.partialUpdate(etablissementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, etablissementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /etablissements} : get all the etablissements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of etablissements in body.
     */
    @GetMapping("/etablissements")
    public List<EtablissementDTO> getAllEtablissements() {
        log.debug("REST request to get all Etablissements");
        return etablissementService.findAll();
    }

    /**
     * {@code GET  /etablissements/:id} : get the "id" etablissement.
     *
     * @param id the id of the etablissementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the etablissementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/etablissements/{id}")
    public ResponseEntity<EtablissementDTO> getEtablissement(@PathVariable Long id) {
        log.debug("REST request to get Etablissement : {}", id);
        Optional<EtablissementDTO> etablissementDTO = etablissementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(etablissementDTO);
    }

    /**
     * {@code DELETE  /etablissements/:id} : delete the "id" etablissement.
     *
     * @param id the id of the etablissementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/etablissements/{id}")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable Long id) {
        log.debug("REST request to delete Etablissement : {}", id);
        etablissementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
