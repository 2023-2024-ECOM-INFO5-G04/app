package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.AlerteRepository;
import fr.polytech.g4.ecom23.service.AlerteService;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Alerte}.
 */
@RestController
@RequestMapping("/api")
public class AlerteResource {

    private final Logger log = LoggerFactory.getLogger(AlerteResource.class);

    private static final String ENTITY_NAME = "alerte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlerteService alerteService;

    private final AlerteRepository alerteRepository;

    public AlerteResource(AlerteService alerteService, AlerteRepository alerteRepository) {
        this.alerteService = alerteService;
        this.alerteRepository = alerteRepository;
    }

    /**
     * {@code POST  /alertes} : Create a new alerte.
     *
     * @param alerteDTO the alerteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alerteDTO, or with status {@code 400 (Bad Request)} if the alerte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/alertes")
    public ResponseEntity<AlerteDTO> createAlerte(@Valid @RequestBody AlerteDTO alerteDTO) throws URISyntaxException {
        log.debug("REST request to save Alerte : {}", alerteDTO);
        if (alerteDTO.getId() != null) {
            throw new BadRequestAlertException("A new alerte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AlerteDTO result = alerteService.save(alerteDTO);
        return ResponseEntity
            .created(new URI("/api/alertes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /alertes/:id} : Updates an existing alerte.
     *
     * @param id the id of the alerteDTO to save.
     * @param alerteDTO the alerteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alerteDTO,
     * or with status {@code 400 (Bad Request)} if the alerteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alerteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/alertes/{id}")
    public ResponseEntity<AlerteDTO> updateAlerte(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlerteDTO alerteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Alerte : {}, {}", id, alerteDTO);
        if (alerteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alerteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alerteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AlerteDTO result = alerteService.update(alerteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alerteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /alertes/:id} : Partial updates given fields of an existing alerte, field will ignore if it is null
     *
     * @param id the id of the alerteDTO to save.
     * @param alerteDTO the alerteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alerteDTO,
     * or with status {@code 400 (Bad Request)} if the alerteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alerteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alerteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/alertes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AlerteDTO> partialUpdateAlerte(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlerteDTO alerteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Alerte partially : {}, {}", id, alerteDTO);
        if (alerteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alerteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!alerteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AlerteDTO> result = alerteService.partialUpdate(alerteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, alerteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /alertes} : get all the alertes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alertes in body.
     */
    @GetMapping("/alertes")
    public List<AlerteDTO> getAllAlertes(@RequestParam(required = false) String filter) {
        if ("patient-is-null".equals(filter)) {
            log.debug("REST request to get all Alertes where patient is null");
            return alerteService.findAllWherePatientIsNull();
        }
        log.debug("REST request to get all Alertes");
        return alerteService.findAll();
    }

    /**
     * {@code GET  /alertes/:id} : get the "id" alerte.
     *
     * @param id the id of the alerteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alerteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/alertes/{id}")
    public ResponseEntity<AlerteDTO> getAlerte(@PathVariable Long id) {
        log.debug("REST request to get Alerte : {}", id);
        Optional<AlerteDTO> alerteDTO = alerteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alerteDTO);
    }

    /**
     * {@code DELETE  /alertes/:id} : delete the "id" alerte.
     *
     * @param id the id of the alerteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/alertes/{id}")
    public ResponseEntity<Void> deleteAlerte(@PathVariable Long id) {
        log.debug("REST request to delete Alerte : {}", id);
        alerteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
