package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.SuividonneesRepository;
import fr.polytech.g4.ecom23.service.SuividonneesService;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.LinkedList;
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
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Suividonnees}.
 */
@RestController
@RequestMapping("/api")
public class SuividonneesResource {

    private final Logger log = LoggerFactory.getLogger(SuividonneesResource.class);

    private static final String ENTITY_NAME = "suividonnees";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SuividonneesService suividonneesService;

    private final SuividonneesRepository suividonneesRepository;

    public SuividonneesResource(SuividonneesService suividonneesService, SuividonneesRepository suividonneesRepository) {
        this.suividonneesService = suividonneesService;
        this.suividonneesRepository = suividonneesRepository;
    }

    /**
     * {@code POST  /suividonnees} : Create a new suividonnees.
     *
     * @param suividonneesDTO the suividonneesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new suividonneesDTO, or with status {@code 400 (Bad Request)} if the suividonnees has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/suividonnees")
    public ResponseEntity<SuividonneesDTO> createSuividonnees(@RequestBody SuividonneesDTO suividonneesDTO)
        throws URISyntaxException {
        log.debug("REST request to save Suividonnees : {}", suividonneesDTO);
        if (suividonneesDTO.getId() != null) {
            throw new BadRequestAlertException("A new suividonnees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuividonneesDTO result = suividonneesService.save(suividonneesDTO);
        return ResponseEntity
            .created(new URI("/api/suividonnees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /suividonnees/:id} : Updates an existing suividonnees.
     *
     * @param id the id of the suividonneesDTO to save.
     * @param suividonneesDTO the suividonneesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suividonneesDTO,
     * or with status {@code 400 (Bad Request)} if the suividonneesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the suividonneesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/suividonnees/{id}")
    public ResponseEntity<SuividonneesDTO> updateSuividonnees(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SuividonneesDTO suividonneesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Suividonnees : {}, {}", id, suividonneesDTO);
        if (suividonneesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suividonneesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suividonneesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SuividonneesDTO result = suividonneesService.update(suividonneesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suividonneesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /suividonnees/:id} : Partial updates given fields of an existing suividonnees, field will ignore if it is null
     *
     * @param id the id of the suividonneesDTO to save.
     * @param suividonneesDTO the suividonneesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated suividonneesDTO,
     * or with status {@code 400 (Bad Request)} if the suividonneesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the suividonneesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the suividonneesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/suividonnees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SuividonneesDTO> partialUpdateSuividonnees(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SuividonneesDTO suividonneesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Suividonnees partially : {}, {}", id, suividonneesDTO);
        if (suividonneesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, suividonneesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!suividonneesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SuividonneesDTO> result = suividonneesService.partialUpdate(suividonneesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, suividonneesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /suividonnees} : get all the suividonnees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suividonnees in body.
     */
    @GetMapping("/suividonnees")
    public List<SuividonneesDTO> getAllSuividonnees() {
        log.debug("REST request to get all Suividonnees");
        return suividonneesService.findAll();
    }

    /**
     * {@code GET  /suividonnees/:id} : get the "id" suividonnees.
     *
     * @param id the id of the suividonneesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the suividonneesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/suividonnees/{id}")
    public ResponseEntity<SuividonneesDTO> getSuividonnees(@PathVariable Long id) {
        log.debug("REST request to get Suividonnees : {}", id);
        Optional<SuividonneesDTO> suividonneesDTO = suividonneesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suividonneesDTO);
    }

    /**
     * {@code GET  /suividonnees/patient/:id : get the suividonnees from the patient with the id.
     *
     * @param id the id of the patient to retrieve the respectives suividonneesDTO.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of suividonnees in body.
     */
    @GetMapping("/suividonnees/patient/{id}")
    public List<SuividonneesDTO> getSuividonneesPatient(@PathVariable Long id) {
       //TODO
//        log.debug("REST request to get all Suividonnees from patient with id : {}", id_patient);
        List<SuividonneesDTO> allSuividonnees =  suividonneesService.findAll();
        List<SuividonneesDTO> filteredSuividonnees = new LinkedList<SuividonneesDTO>();
        for (SuividonneesDTO sd : allSuividonnees) {
            if (sd.getPatient().getId().equals(id))
                filteredSuividonnees.add(sd);
        }
        return filteredSuividonnees;
    }

    /**
     * {@code DELETE  /suividonnees/:id} : delete the "id" suividonnees.
     *
     * @param id the id of the suividonneesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/suividonnees/{id}")
    public ResponseEntity<Void> deleteSuividonnees(@PathVariable Long id) {
        log.debug("REST request to delete Suividonnees : {}", id);
        suividonneesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     *  A DTO for the variables from SuiviDonnees entity necessary for plotting graphs
     */
    public class CourbesDTO implements Serializable {
        private Float poids;
        private Float epa;
        private Float imc;
        public void setPoids(Float poids) {
            this.poids = poids;
        }
        public Float getPoids() {
            return this.poids;
        }
        public void setEpa(Float epa) {
            this.epa = epa;
        }
        public Float getEpa() {
            return this.epa;
        }
        public void setImc(Float imc) {
            this.imc = imc;
        }
        public Float getImc() {
            return this.imc;
        }
    }
    /**
     *  A DTO for the variables from SuiviDonnees entity necessary for plotting graphs with their respective data
     */
    public class DateCourbesDTO implements Serializable {
        private LocalDate date;
        private CourbesDTO courbes;
        public void setDate(LocalDate date) {
            this.date = date;
        }
        public LocalDate getDate() {
            return this.date;
        }
        public void setCourbes(CourbesDTO courbes) {
            this.courbes = courbes;
        }
        public CourbesDTO getCourbes() {
            return this.courbes;
        }
    }
    /**
     * {@code GET  /suividonnees/courbes/patient/:id : get the DateCourbes from the patient with the id.
     *
     * @param id the id of the patient to retrieve the respectives DateCourbesDTO.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of DateCourbes in body.
     */
    @GetMapping("/suividonnees/courbes/patient/{id}")
    public List<DateCourbesDTO>getCourbesPatient(@PathVariable Long id) {
        List<SuividonneesDTO> allSuividonnees =  suividonneesService.findAll();
        List<DateCourbesDTO> allDateCourbes = new LinkedList<DateCourbesDTO>();
        for (SuividonneesDTO sd : allSuividonnees) {
            PatientDTO patient = sd.getPatient();
            if (patient.getId().equals(id)) {
                Float poids = sd.getPoids();
                CourbesDTO courbes = new CourbesDTO();
                courbes.setPoids(poids);
                courbes.setEpa(sd.getEpa());
                courbes.setImc(poids/((float) Math.pow(patient.getTaille(),2)));
                DateCourbesDTO dateCourbes = new DateCourbesDTO();
                dateCourbes.setCourbes(courbes);
                dateCourbes.setDate(sd.getDate());
                allDateCourbes.add(dateCourbes);
            }
        }
        return allDateCourbes;
    }
}
