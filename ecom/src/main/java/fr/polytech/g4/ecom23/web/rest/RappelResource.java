package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.RappelRepository;
import fr.polytech.g4.ecom23.service.RappelService;
import fr.polytech.g4.ecom23.service.dto.RappelDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Rappel}.
 */
@RestController
@RequestMapping("/api")
public class RappelResource {

    private final Logger log = LoggerFactory.getLogger(RappelResource.class);

    private static final String ENTITY_NAME = "rappel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RappelService rappelService;

    private final RappelRepository rappelRepository;

    public RappelResource(RappelService rappelService, RappelRepository rappelRepository) {
        this.rappelService = rappelService;
        this.rappelRepository = rappelRepository;
    }

    /**
     * {@code POST  /rappels} : Create a new rappel.
     *
     * @param rappelDTO the rappelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rappelDTO, or with status {@code 400 (Bad Request)} if the rappel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rappels")
    public ResponseEntity<RappelDTO> createRappel(@RequestBody RappelDTO rappelDTO) throws URISyntaxException {
        log.debug("REST request to save Rappel : {}", rappelDTO);
        if (rappelDTO.getId() != null) {
            throw new BadRequestAlertException("A new rappel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RappelDTO result = rappelService.save(rappelDTO);
        return ResponseEntity
            .created(new URI("/api/rappels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rappels/:id} : Updates an existing rappel.
     *
     * @param id the id of the rappelDTO to save.
     * @param rappelDTO the rappelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rappelDTO,
     * or with status {@code 400 (Bad Request)} if the rappelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rappelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rappels/{id}")
    public ResponseEntity<RappelDTO> updateRappel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RappelDTO rappelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Rappel : {}, {}", id, rappelDTO);
        if (rappelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rappelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rappelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RappelDTO result = rappelService.update(rappelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rappelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rappels/:id} : Partial updates given fields of an existing rappel, field will ignore if it is null
     *
     * @param id the id of the rappelDTO to save.
     * @param rappelDTO the rappelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rappelDTO,
     * or with status {@code 400 (Bad Request)} if the rappelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rappelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rappelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rappels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RappelDTO> partialUpdateRappel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RappelDTO rappelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rappel partially : {}, {}", id, rappelDTO);
        if (rappelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rappelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rappelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RappelDTO> result = rappelService.partialUpdate(rappelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rappelDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rappels} : get all the rappels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rappels in body.
     */
    @GetMapping("/rappels")
    public List<RappelDTO> getAllRappels() {
        log.debug("REST request to get all Rappels");
        return rappelService.findAll();
    }

    /**
     * {@code GET  /rappels/:id} : get the "id" rappel.
     *
     * @param id the id of the rappelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rappelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rappels/{id}")
    public ResponseEntity<RappelDTO> getRappel(@PathVariable Long id) {
        log.debug("REST request to get Rappel : {}", id);
        Optional<RappelDTO> rappelDTO = rappelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rappelDTO);
    }

    /**
     * {@code DELETE  /rappels/:id} : delete the "id" rappel.
     *
     * @param id the id of the rappelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rappels/{id}")
    public ResponseEntity<Void> deleteRappel(@PathVariable Long id) {
        log.debug("REST request to delete Rappel : {}", id);
        rappelService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
