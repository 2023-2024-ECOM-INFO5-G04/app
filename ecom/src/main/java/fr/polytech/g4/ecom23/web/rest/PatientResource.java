package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.PatientRepository;
import fr.polytech.g4.ecom23.service.PatientService;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Patient}.
 */
@RestController
@RequestMapping("/api")
public class PatientResource {

    private final Logger log = LoggerFactory.getLogger(PatientResource.class);

    private static final String ENTITY_NAME = "patient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientService patientService;

    private final PatientRepository patientRepository;

    public PatientResource(PatientService patientService, PatientRepository patientRepository) {
        this.patientService = patientService;
        this.patientRepository = patientRepository;
    }

    /**
     * {@code POST  /patients} : Create a new patient.
     *
     * @param patientDTO the patientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientDTO, or with status {@code 400 (Bad Request)} if the patient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patients")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) throws URISyntaxException {
        log.debug("REST request to save Patient : {}", patientDTO);
        if (patientDTO.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientDTO result = patientService.save(patientDTO);
        return ResponseEntity
            .created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patients/:id} : Updates an existing patient.
     *
     * @param id the id of the patientDTO to save.
     * @param patientDTO the patientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientDTO,
     * or with status {@code 400 (Bad Request)} if the patientDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> updatePatient(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PatientDTO patientDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Patient : {}, {}", id, patientDTO);
        if (patientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PatientDTO result = patientService.update(patientDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /patients/:id} : Partial updates given fields of an existing patient, field will ignore if it is null
     *
     * @param id the id of the patientDTO to save.
     * @param patientDTO the patientDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientDTO,
     * or with status {@code 400 (Bad Request)} if the patientDTO is not valid,
     * or with status {@code 404 (Not Found)} if the patientDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the patientDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/patients/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PatientDTO> partialUpdatePatient(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PatientDTO patientDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Patient partially : {}, {}", id, patientDTO);
        if (patientDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, patientDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!patientRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PatientDTO> result = patientService.partialUpdate(patientDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patientDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /patients} : get all the patients.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patients in body.
     */
    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Patients");
        return patientService.findAll();
    }

    /**
     * {@code GET  /patients/:id} : get the "id" patient.
     *
     * @param id the id of the patientDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        Optional<PatientDTO> patientDTO = patientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientDTO);
    }

    /**
     * {@code DELETE  /patients/:id} : delete the "id" patient.
     *
     * @param id the id of the patientDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete Patient : {}", id);
        patientService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
