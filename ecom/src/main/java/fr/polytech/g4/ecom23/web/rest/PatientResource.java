package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.PatientRepository;
import fr.polytech.g4.ecom23.service.AlerteService;
import fr.polytech.g4.ecom23.service.PatientService;
import fr.polytech.g4.ecom23.service.SuividonneesService;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.SuiviComparator;
import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.ResponseCache;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    private final AlerteService alerteService;

    private final SuividonneesService suividonneesService;

    private final PatientRepository patientRepository;

    public PatientResource(PatientService patientService, AlerteService alerteService, SuividonneesService suividonneesService, PatientRepository patientRepository) {
        this.patientService = patientService;
        this.alerteService = alerteService;
        this.suividonneesService = suividonneesService;
        this.patientRepository = patientRepository;
    }

    /**
     * {@code POST  /import-patient} : Create new patients and suividonnees by importation of data.
     *
     * @param file a CSV file containing a list of patient and their measures associated.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)}.
     * @throws IOException if there is a problem with the reading of the CSV.
     */
    @PostMapping("/import-patient")
    public ResponseEntity<String> importDataForPatient(@RequestBody MultipartFile file) throws IOException {
        patientService.importDataFromCSVForPatient(file.getInputStream());
        String message = "L'importation a réussi.";
        return ResponseEntity.ok(message);
    }

    /**
     * {@code POST  /patients} : Create a new patient.
     *
     * @param patientDTO the patientDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientDTO, or with status {@code 400 (Bad Request)} if the patient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patients")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO patientDTO) throws URISyntaxException {
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
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patients in body.
     */
    @GetMapping("/patients")
    public List<PatientDTO> getAllPatients() {
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
     * {@code GET  /patients/filter/etablissement/:id} : get patients in the "id" etablissment.
     *
     * @param id the id of the etablissablissement used as a filter.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patients in body.
     */
    @GetMapping("/patients/filter/etablissement/{id}")
    public List<PatientDTO> getPatientsFilteredByEtablissement(@PathVariable Long id) {
        log.debug("REST request to get all Patients in Etablissement : {}", id);
        List<PatientDTO> allPatients = patientService.findAll();
        List<PatientDTO> filteredPatients = new LinkedList<PatientDTO>();
        for (PatientDTO p : allPatients) {
            if (p.getEtablissement().getId().equals(id))
                filteredPatients.add(p);
        }
        return filteredPatients;
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

    @PatchMapping("/patients/updatedenutrition")
    public List<PatientDTO> updateDenutrition() throws URISyntaxException {
        log.debug("REST request to update Alerte of all Patients");
        List<PatientDTO> list = patientService.findAll();
        for (PatientDTO patientDTO : list) {
            AlerteDTO alerteDTO = patientDTO.getAlerte();
            if (alerteDTO == null) {
                alerteDTO = alerteService.save(new AlerteDTO());
                patientDTO.setAlerte(alerteDTO);
                patientService.partialUpdate(patientDTO);
            }
            AlerteDTO newAlerteDTO = patientDTO.denutrition(suividonneesService.findAll());
            alerteDTO.setDenutrition(newAlerteDTO.getDenutrition());
            alerteDTO.setSeverite(newAlerteDTO.getSeverite());
            alerteDTO.setDate(newAlerteDTO.getDate());
            alerteDTO.setCommentaire(newAlerteDTO.getCommentaire());
            alerteService.partialUpdate(alerteDTO);
        }
        return patientService.findAll();
    }

    @GetMapping("/patients/{id}/epa")
    public Float getLastEPA(@PathVariable Long id) {
        List<SuividonneesDTO> list = suividonneesService.findAll();
        list.sort(new SuiviComparator());
        for (SuividonneesDTO suivi : list) {
            if (!suivi.getPatient().getId().equals(id))
                continue;
            Float epa = suivi.getEpa();
            if (epa != null)
                return epa;
        }
        throw new RuntimeException("Patient " + id + " does not exist or does not have EPA");
    }

}
