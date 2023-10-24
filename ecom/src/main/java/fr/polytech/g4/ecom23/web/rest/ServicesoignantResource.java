package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.ServicesoignantRepository;
import fr.polytech.g4.ecom23.service.ServicesoignantService;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Servicesoignant}.
 */
@RestController
@RequestMapping("/api")
public class ServicesoignantResource {

    private final Logger log = LoggerFactory.getLogger(ServicesoignantResource.class);

    private static final String ENTITY_NAME = "servicesoignant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicesoignantService servicesoignantService;

    private final ServicesoignantRepository servicesoignantRepository;

    public ServicesoignantResource(ServicesoignantService servicesoignantService, ServicesoignantRepository servicesoignantRepository) {
        this.servicesoignantService = servicesoignantService;
        this.servicesoignantRepository = servicesoignantRepository;
    }

    /**
     * {@code POST  /servicesoignants} : Create a new servicesoignant.
     *
     * @param servicesoignantDTO the servicesoignantDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicesoignantDTO, or with status {@code 400 (Bad Request)} if the servicesoignant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/servicesoignants")
    public ResponseEntity<ServicesoignantDTO> createServicesoignant(@Valid @RequestBody ServicesoignantDTO servicesoignantDTO)
        throws URISyntaxException {
        log.debug("REST request to save Servicesoignant : {}", servicesoignantDTO);
        if (servicesoignantDTO.getId() != null) {
            throw new BadRequestAlertException("A new servicesoignant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicesoignantDTO result = servicesoignantService.save(servicesoignantDTO);
        return ResponseEntity
            .created(new URI("/api/servicesoignants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /servicesoignants/:id} : Updates an existing servicesoignant.
     *
     * @param id the id of the servicesoignantDTO to save.
     * @param servicesoignantDTO the servicesoignantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicesoignantDTO,
     * or with status {@code 400 (Bad Request)} if the servicesoignantDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicesoignantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/servicesoignants/{id}")
    public ResponseEntity<ServicesoignantDTO> updateServicesoignant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicesoignantDTO servicesoignantDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Servicesoignant : {}, {}", id, servicesoignantDTO);
        if (servicesoignantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicesoignantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicesoignantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServicesoignantDTO result = servicesoignantService.update(servicesoignantDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicesoignantDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /servicesoignants/:id} : Partial updates given fields of an existing servicesoignant, field will ignore if it is null
     *
     * @param id the id of the servicesoignantDTO to save.
     * @param servicesoignantDTO the servicesoignantDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicesoignantDTO,
     * or with status {@code 400 (Bad Request)} if the servicesoignantDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servicesoignantDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicesoignantDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/servicesoignants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicesoignantDTO> partialUpdateServicesoignant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicesoignantDTO servicesoignantDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Servicesoignant partially : {}, {}", id, servicesoignantDTO);
        if (servicesoignantDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicesoignantDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicesoignantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicesoignantDTO> result = servicesoignantService.partialUpdate(servicesoignantDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicesoignantDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /servicesoignants} : get all the servicesoignants.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of servicesoignants in body.
     */
    @GetMapping("/servicesoignants")
    public List<ServicesoignantDTO> getAllServicesoignants(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Servicesoignants");
        return servicesoignantService.findAll();
    }

    /**
     * {@code GET  /servicesoignants/:id} : get the "id" servicesoignant.
     *
     * @param id the id of the servicesoignantDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicesoignantDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/servicesoignants/{id}")
    public ResponseEntity<ServicesoignantDTO> getServicesoignant(@PathVariable Long id) {
        log.debug("REST request to get Servicesoignant : {}", id);
        Optional<ServicesoignantDTO> servicesoignantDTO = servicesoignantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicesoignantDTO);
    }

    /**
     * {@code DELETE  /servicesoignants/:id} : delete the "id" servicesoignant.
     *
     * @param id the id of the servicesoignantDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/servicesoignants/{id}")
    public ResponseEntity<Void> deleteServicesoignant(@PathVariable Long id) {
        log.debug("REST request to delete Servicesoignant : {}", id);
        servicesoignantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
