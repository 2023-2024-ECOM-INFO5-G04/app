package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.enumeration.Sexe;
import fr.polytech.g4.ecom23.repository.PatientRepository;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.mapper.PatientMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PatientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatientResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final LocalDate DEFAULT_DATEARRIVEE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEARRIVEE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_POIDSACTUEL = 1F;
    private static final Float UPDATED_POIDSACTUEL = 2F;

    private static final Float DEFAULT_ALBUMINE = 1F;
    private static final Float UPDATED_ALBUMINE = 2F;

    private static final Float DEFAULT_TAILLE = 1F;
    private static final Float UPDATED_TAILLE = 2F;

    private static final Sexe DEFAULT_SEXE = Sexe.M;
    private static final Sexe UPDATED_SEXE = Sexe.F;

    private static final Boolean DEFAULT_FAVORI = false;
    private static final Boolean UPDATED_FAVORI = true;

    private static final Boolean DEFAULT_SARCOPENIE = false;
    private static final Boolean UPDATED_SARCOPENIE = true;

    private static final Boolean DEFAULT_ABSORPTIONREDUITE = false;
    private static final Boolean UPDATED_ABSORPTIONREDUITE = true;

    private static final Boolean DEFAULT_AGRESSION = false;
    private static final Boolean UPDATED_AGRESSION = true;

    private static final String ENTITY_API_URL = "/api/patients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientMockMvc;

    private Patient patient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .age(DEFAULT_AGE)
            .datearrivee(DEFAULT_DATEARRIVEE)
            .poidsactuel(DEFAULT_POIDSACTUEL)
            .albumine(DEFAULT_ALBUMINE)
            .taille(DEFAULT_TAILLE)
            .sexe(DEFAULT_SEXE)
            .favori(DEFAULT_FAVORI)
            .sarcopenie(DEFAULT_SARCOPENIE)
            .absorptionreduite(DEFAULT_ABSORPTIONREDUITE)
            .agression(DEFAULT_AGRESSION);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        patient.setEtablissement(etablissement);
        return patient;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patient createUpdatedEntity(EntityManager em) {
        Patient patient = new Patient()
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .age(UPDATED_AGE)
            .datearrivee(UPDATED_DATEARRIVEE)
            .poidsactuel(UPDATED_POIDSACTUEL)
            .albumine(UPDATED_ALBUMINE)
            .taille(UPDATED_TAILLE)
            .sexe(UPDATED_SEXE)
            .favori(UPDATED_FAVORI)
            .sarcopenie(UPDATED_SARCOPENIE)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createUpdatedEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        patient.setEtablissement(etablissement);
        return patient;
    }

    @BeforeEach
    public void initTest() {
        patient = createEntity(em);
    }

    @Test
    @Transactional
    void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();
        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testPatient.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPatient.getDatearrivee()).isEqualTo(DEFAULT_DATEARRIVEE);
        assertThat(testPatient.getPoidsactuel()).isEqualTo(DEFAULT_POIDSACTUEL);
        assertThat(testPatient.getAlbumine()).isEqualTo(DEFAULT_ALBUMINE);
        assertThat(testPatient.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testPatient.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testPatient.getFavori()).isEqualTo(DEFAULT_FAVORI);
        assertThat(testPatient.getSarcopenie()).isEqualTo(DEFAULT_SARCOPENIE);
        assertThat(testPatient.getAbsorptionreduite()).isEqualTo(DEFAULT_ABSORPTIONREDUITE);
        assertThat(testPatient.getAgression()).isEqualTo(DEFAULT_AGRESSION);
    }

    @Test
    @Transactional
    void createPatientWithExistingId() throws Exception {
        // Create the Patient with an existing ID
        patient.setId(1L);
        PatientDTO patientDTO = patientMapper.toDto(patient);

        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].datearrivee").value(hasItem(DEFAULT_DATEARRIVEE.toString())))
            .andExpect(jsonPath("$.[*].poidsactuel").value(hasItem(DEFAULT_POIDSACTUEL.doubleValue())))
            .andExpect(jsonPath("$.[*].albumine").value(hasItem(DEFAULT_ALBUMINE.doubleValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE.doubleValue())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].favori").value(hasItem(DEFAULT_FAVORI.booleanValue())))
            .andExpect(jsonPath("$.[*].sarcopenie").value(hasItem(DEFAULT_SARCOPENIE.booleanValue())))
            .andExpect(jsonPath("$.[*].absorptionreduite").value(hasItem(DEFAULT_ABSORPTIONREDUITE.booleanValue())))
            .andExpect(jsonPath("$.[*].agression").value(hasItem(DEFAULT_AGRESSION.booleanValue())));
    }

    @Test
    @Transactional
    void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc
            .perform(get(ENTITY_API_URL_ID, patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.datearrivee").value(DEFAULT_DATEARRIVEE.toString()))
            .andExpect(jsonPath("$.poidsactuel").value(DEFAULT_POIDSACTUEL.doubleValue()))
            .andExpect(jsonPath("$.albumine").value(DEFAULT_ALBUMINE.doubleValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE.doubleValue()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.favori").value(DEFAULT_FAVORI.booleanValue()))
            .andExpect(jsonPath("$.sarcopenie").value(DEFAULT_SARCOPENIE.booleanValue()))
            .andExpect(jsonPath("$.absorptionreduite").value(DEFAULT_ABSORPTIONREDUITE.booleanValue()))
            .andExpect(jsonPath("$.agression").value(DEFAULT_AGRESSION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .age(UPDATED_AGE)
            .datearrivee(UPDATED_DATEARRIVEE)
            .poidsactuel(UPDATED_POIDSACTUEL)
            .albumine(UPDATED_ALBUMINE)
            .taille(UPDATED_TAILLE)
            .sexe(UPDATED_SEXE)
            .favori(UPDATED_FAVORI)
            .sarcopenie(UPDATED_SARCOPENIE)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);
        PatientDTO patientDTO = patientMapper.toDto(updatedPatient);

        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatient.getDatearrivee()).isEqualTo(UPDATED_DATEARRIVEE);
        assertThat(testPatient.getPoidsactuel()).isEqualTo(UPDATED_POIDSACTUEL);
        assertThat(testPatient.getAlbumine()).isEqualTo(UPDATED_ALBUMINE);
        assertThat(testPatient.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testPatient.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatient.getFavori()).isEqualTo(UPDATED_FAVORI);
        assertThat(testPatient.getSarcopenie()).isEqualTo(UPDATED_SARCOPENIE);
        assertThat(testPatient.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testPatient.getAgression()).isEqualTo(UPDATED_AGRESSION);
    }

    @Test
    @Transactional
    void putNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .age(UPDATED_AGE)
            .datearrivee(UPDATED_DATEARRIVEE)
            .albumine(UPDATED_ALBUMINE)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatient.getDatearrivee()).isEqualTo(UPDATED_DATEARRIVEE);
        assertThat(testPatient.getPoidsactuel()).isEqualTo(DEFAULT_POIDSACTUEL);
        assertThat(testPatient.getAlbumine()).isEqualTo(UPDATED_ALBUMINE);
        assertThat(testPatient.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testPatient.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testPatient.getFavori()).isEqualTo(DEFAULT_FAVORI);
        assertThat(testPatient.getSarcopenie()).isEqualTo(DEFAULT_SARCOPENIE);
        assertThat(testPatient.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testPatient.getAgression()).isEqualTo(DEFAULT_AGRESSION);
    }

    @Test
    @Transactional
    void fullUpdatePatientWithPatch() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient using partial update
        Patient partialUpdatedPatient = new Patient();
        partialUpdatedPatient.setId(patient.getId());

        partialUpdatedPatient
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .age(UPDATED_AGE)
            .datearrivee(UPDATED_DATEARRIVEE)
            .poidsactuel(UPDATED_POIDSACTUEL)
            .albumine(UPDATED_ALBUMINE)
            .taille(UPDATED_TAILLE)
            .sexe(UPDATED_SEXE)
            .favori(UPDATED_FAVORI)
            .sarcopenie(UPDATED_SARCOPENIE)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);

        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatient.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatient))
            )
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPatient.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testPatient.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPatient.getDatearrivee()).isEqualTo(UPDATED_DATEARRIVEE);
        assertThat(testPatient.getPoidsactuel()).isEqualTo(UPDATED_POIDSACTUEL);
        assertThat(testPatient.getAlbumine()).isEqualTo(UPDATED_ALBUMINE);
        assertThat(testPatient.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testPatient.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatient.getFavori()).isEqualTo(UPDATED_FAVORI);
        assertThat(testPatient.getSarcopenie()).isEqualTo(UPDATED_SARCOPENIE);
        assertThat(testPatient.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testPatient.getAgression()).isEqualTo(UPDATED_AGRESSION);
    }

    @Test
    @Transactional
    void patchNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patientDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();
        patient.setId(count.incrementAndGet());

        // Create the Patient
        PatientDTO patientDTO = patientMapper.toDto(patient);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatientMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patientDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Delete the patient
        restPatientMockMvc
            .perform(delete(ENTITY_API_URL_ID, patient.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
