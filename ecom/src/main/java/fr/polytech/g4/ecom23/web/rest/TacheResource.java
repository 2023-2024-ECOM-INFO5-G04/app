package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.TacheRepository;
import fr.polytech.g4.ecom23.service.*;
import fr.polytech.g4.ecom23.service.dto.*;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.undertow.util.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Tache}.
 */
@RestController
@RequestMapping("/api")
public class TacheResource {

    private final Logger log = LoggerFactory.getLogger(TacheResource.class);

    private static final String ENTITY_NAME = "tache";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TacheService tacheService;

    private final SoignantService soignantService;

    private final MedecinService medecinService;

    private final PatientService patientService;

    private final ServicesoignantService servicesoignantService;

    private final TacheRepository tacheRepository;

    public TacheResource(TacheService tacheService, SoignantService soignantService, MedecinService medecinService, PatientService patientService, ServicesoignantService servicesoignantService, TacheRepository tacheRepository) {
        this.tacheService = tacheService;
        this.soignantService = soignantService;
        this.medecinService = medecinService;
        this.patientService = patientService;
        this.servicesoignantService = servicesoignantService;
        this.tacheRepository = tacheRepository;
    }

    /**
     * {@code POST  /taches} : Create a new tache.
     *
     * @param tacheDTO the tacheDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tacheDTO, or with status {@code 400 (Bad Request)} if the tache has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/taches")
    public ResponseEntity<TacheDTO> createTache(@Valid @RequestBody TacheDTO tacheDTO) throws URISyntaxException {
        log.debug("REST request to save Tache : {}", tacheDTO);
        if (tacheDTO.getId() != null) {
            throw new BadRequestAlertException("A new tache cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TacheDTO result = tacheService.save(tacheDTO);
        return ResponseEntity
            .created(new URI("/api/taches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /taches/:id} : Updates an existing tache.
     *
     * @param id the id of the tacheDTO to save.
     * @param tacheDTO the tacheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tacheDTO,
     * or with status {@code 400 (Bad Request)} if the tacheDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tacheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/taches/{id}")
    public ResponseEntity<TacheDTO> updateTache(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TacheDTO tacheDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tache : {}, {}", id, tacheDTO);
        if (tacheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tacheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tacheRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TacheDTO result = tacheService.update(tacheDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tacheDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /taches/:id} : Partial updates given fields of an existing tache, field will ignore if it is null
     *
     * @param id the id of the tacheDTO to save.
     * @param tacheDTO the tacheDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tacheDTO,
     * or with status {@code 400 (Bad Request)} if the tacheDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tacheDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tacheDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/taches/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TacheDTO> partialUpdateTache(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TacheDTO tacheDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tache partially : {}, {}", id, tacheDTO);
        if (tacheDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tacheDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tacheRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TacheDTO> result = tacheService.partialUpdate(tacheDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tacheDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /taches} : get all the taches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taches in body.
     */
    @GetMapping("/taches")
    public List<TacheDTO> getAllTaches() {
        log.debug("REST request to get all Taches");
        return tacheService.findAll();
    }

    /**
     * {@code GET  /taches/:id} : get the "id" tache.
     *
     * @param id the id of the tacheDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tacheDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/taches/{id}")
    public ResponseEntity<TacheDTO> getTache(@PathVariable Long id) {
        log.debug("REST request to get Tache : {}", id);
        Optional<TacheDTO> tacheDTO = tacheService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tacheDTO);
    }

    /**
     * {@code DELETE  /taches/:id} : delete the "id" tache.
     *
     * @param id the id of the tacheDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/taches/{id}")
    public ResponseEntity<Void> deleteTache(@PathVariable Long id) {
        log.debug("REST request to delete Tache : {}", id);
        tacheService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/taches/form")
    public List<TacheDTO> createTaches(
        @RequestParam Long medecin,
        @RequestParam Long patient,
        @RequestParam Boolean soignant,
        @RequestParam String nom,
        @RequestParam LocalDate debut,
        @RequestParam(defaultValue = "") LocalDate fin,
        @RequestParam(defaultValue = "") Long frequence,
        @RequestParam(defaultValue = "") Long joursperiode,
        @RequestParam(defaultValue = "") String commentaire
    ) throws BadRequestException {
        if (frequence == null && joursperiode != null || frequence != null && joursperiode == null)
            throw new BadRequestException("frequence et typeFrequence doivent être soit tous les deux remplis soit tous les deux vides");

        SoignantDTO soignantDTO = null;
        ServicesoignantDTO servicesoignantDTO = null;

        Optional<MedecinDTO> optionalMedecinDTO = medecinService.findOne(medecin);
        if (optionalMedecinDTO.isEmpty())
            throw new BadRequestException("Ce médecin n'existe pas");
        MedecinDTO medecinDTO = optionalMedecinDTO.get();
        Optional<PatientDTO> optionalPatientDTO = patientService.findOne(patient);
        if (optionalPatientDTO.isEmpty())
            throw new BadRequestException("Ce patient n'existe pas");
        PatientDTO patientDTO = optionalPatientDTO.get();

        if (soignant) {
            List<SoignantDTO> soignants = soignantService.findAll();
            for (SoignantDTO s : soignants) {
                if (s.getNom().equals(nom)) {
                    soignantDTO = s;
                    break;
                }
            }
            if (soignantDTO == null)
                throw new BadRequestException("Le soignant \"" + nom + "\" n'existe pas");
        }
        else {
            List<ServicesoignantDTO> services = servicesoignantService.findAll();
            for (ServicesoignantDTO s : services) {
                if (s.getType().equals(nom) && s.getEtablissement().equals(patientDTO.getEtablissement())) {
                    servicesoignantDTO = s;
                    break;
                }
            }
            if (servicesoignantDTO == null)
                throw new BadRequestException("Le service soignant \"" + nom + "\" n'existe pas");
        }

        if (joursperiode == null && frequence == null) {
            TacheDTO t = new TacheDTO();
            t.setServicesoignant(servicesoignantDTO);
            t.setSoignant(soignantDTO);
            t.setDate(debut);
            t.setPatient(patientDTO);
            t.setEffectuee(false);
            t.setMedecin(medecinDTO);
            t.setCommentaire(commentaire);
            t = tacheService.save(t);
            return Collections.singletonList(t);
        }

        if (fin == null)
            fin = debut.plusYears(3);

        List<TacheDTO> taches = new LinkedList<>();


        int nTaches = 0;
        int i = 0;
        float limit = (float) frequence / joursperiode;
        while (!debut.plusDays(i).isAfter(fin)) {
            if ((float) nTaches / (i+1) >= limit) {
                System.out.println("HEY");
                i++;
            }
            else {
                System.out.println("YO");
                TacheDTO t = new TacheDTO();
                t.setDate(debut.plusDays(i));
                t.setCommentaire(commentaire);
                t.setEffectuee(false);
                t.setMedecin(medecinDTO);
                t.setPatient(patientDTO);
                t.setSoignant(soignantDTO);
                t.setServicesoignant(servicesoignantDTO);
                t = tacheService.save(t);
                taches.add(t);
                nTaches++;
            }

        }

        return taches;

    }
}
