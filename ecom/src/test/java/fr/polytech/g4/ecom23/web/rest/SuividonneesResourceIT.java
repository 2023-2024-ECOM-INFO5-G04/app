package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Suividonnees;
import fr.polytech.g4.ecom23.repository.SuividonneesRepository;
import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import fr.polytech.g4.ecom23.service.mapper.SuividonneesMapper;
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
 * Integration tests for the {@link SuividonneesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SuividonneesResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_POIDS = 1F;
    private static final Float UPDATED_POIDS = 2F;

    private static final Float DEFAULT_EPA = 1F;
    private static final Float UPDATED_EPA = 2F;

    private static final Float DEFAULT_MASSECORPORELLE = 1F;
    private static final Float UPDATED_MASSECORPORELLE = 2F;

    private static final Float DEFAULT_QUANTITEPOIDSALIMENTS = 1F;
    private static final Float UPDATED_QUANTITEPOIDSALIMENTS = 2F;

    private static final Float DEFAULT_QUANTITECALORIESALIMENTS = 1F;
    private static final Float UPDATED_QUANTITECALORIESALIMENTS = 2F;

    private static final Boolean DEFAULT_ABSORPTIONREDUITE = false;
    private static final Boolean UPDATED_ABSORPTIONREDUITE = true;

    private static final Boolean DEFAULT_AGRESSION = false;
    private static final Boolean UPDATED_AGRESSION = true;

    private static final String ENTITY_API_URL = "/api/suividonnees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SuividonneesRepository suividonneesRepository;

    @Autowired
    private SuividonneesMapper suividonneesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuividonneesMockMvc;

    private Suividonnees suividonnees;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suividonnees createEntity(EntityManager em) {
        Suividonnees suividonnees = new Suividonnees()
            .date(DEFAULT_DATE)
            .poids(DEFAULT_POIDS)
            .epa(DEFAULT_EPA)
            .massecorporelle(DEFAULT_MASSECORPORELLE)
            .quantitepoidsaliments(DEFAULT_QUANTITEPOIDSALIMENTS)
            .quantitecaloriesaliments(DEFAULT_QUANTITECALORIESALIMENTS)
            .absorptionreduite(DEFAULT_ABSORPTIONREDUITE)
            .agression(DEFAULT_AGRESSION);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        suividonnees.setPatient(patient);
        return suividonnees;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suividonnees createUpdatedEntity(EntityManager em) {
        Suividonnees suividonnees = new Suividonnees()
            .date(UPDATED_DATE)
            .poids(UPDATED_POIDS)
            .epa(UPDATED_EPA)
            .massecorporelle(UPDATED_MASSECORPORELLE)
            .quantitepoidsaliments(UPDATED_QUANTITEPOIDSALIMENTS)
            .quantitecaloriesaliments(UPDATED_QUANTITECALORIESALIMENTS)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        suividonnees.setPatient(patient);
        return suividonnees;
    }

    @BeforeEach
    public void initTest() {
        suividonnees = createEntity(em);
    }

    @Test
    @Transactional
    void createSuividonnees() throws Exception {
        int databaseSizeBeforeCreate = suividonneesRepository.findAll().size();
        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);
        restSuividonneesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeCreate + 1);
        Suividonnees testSuividonnees = suividonneesList.get(suividonneesList.size() - 1);
        assertThat(testSuividonnees.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSuividonnees.getPoids()).isEqualTo(DEFAULT_POIDS);
        assertThat(testSuividonnees.getEpa()).isEqualTo(DEFAULT_EPA);
        assertThat(testSuividonnees.getMassecorporelle()).isEqualTo(DEFAULT_MASSECORPORELLE);
        assertThat(testSuividonnees.getQuantitepoidsaliments()).isEqualTo(DEFAULT_QUANTITEPOIDSALIMENTS);
        assertThat(testSuividonnees.getQuantitecaloriesaliments()).isEqualTo(DEFAULT_QUANTITECALORIESALIMENTS);
        assertThat(testSuividonnees.getAbsorptionreduite()).isEqualTo(DEFAULT_ABSORPTIONREDUITE);
        assertThat(testSuividonnees.getAgression()).isEqualTo(DEFAULT_AGRESSION);
    }

    @Test
    @Transactional
    void createSuividonneesWithExistingId() throws Exception {
        // Create the Suividonnees with an existing ID
        suividonnees.setId(1L);
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        int databaseSizeBeforeCreate = suividonneesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuividonneesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = suividonneesRepository.findAll().size();
        // set the field null
        suividonnees.setDate(null);

        // Create the Suividonnees, which fails.
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        restSuividonneesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSuividonnees() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        // Get all the suividonneesList
        restSuividonneesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suividonnees.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].poids").value(hasItem(DEFAULT_POIDS.doubleValue())))
            .andExpect(jsonPath("$.[*].epa").value(hasItem(DEFAULT_EPA.doubleValue())))
            .andExpect(jsonPath("$.[*].massecorporelle").value(hasItem(DEFAULT_MASSECORPORELLE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantitepoidsaliments").value(hasItem(DEFAULT_QUANTITEPOIDSALIMENTS.doubleValue())))
            .andExpect(jsonPath("$.[*].quantitecaloriesaliments").value(hasItem(DEFAULT_QUANTITECALORIESALIMENTS.doubleValue())))
            .andExpect(jsonPath("$.[*].absorptionreduite").value(hasItem(DEFAULT_ABSORPTIONREDUITE.booleanValue())))
            .andExpect(jsonPath("$.[*].agression").value(hasItem(DEFAULT_AGRESSION.booleanValue())));
    }

    @Test
    @Transactional
    void getSuividonnees() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        // Get the suividonnees
        restSuividonneesMockMvc
            .perform(get(ENTITY_API_URL_ID, suividonnees.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suividonnees.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.poids").value(DEFAULT_POIDS.doubleValue()))
            .andExpect(jsonPath("$.epa").value(DEFAULT_EPA.doubleValue()))
            .andExpect(jsonPath("$.massecorporelle").value(DEFAULT_MASSECORPORELLE.doubleValue()))
            .andExpect(jsonPath("$.quantitepoidsaliments").value(DEFAULT_QUANTITEPOIDSALIMENTS.doubleValue()))
            .andExpect(jsonPath("$.quantitecaloriesaliments").value(DEFAULT_QUANTITECALORIESALIMENTS.doubleValue()))
            .andExpect(jsonPath("$.absorptionreduite").value(DEFAULT_ABSORPTIONREDUITE.booleanValue()))
            .andExpect(jsonPath("$.agression").value(DEFAULT_AGRESSION.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingSuividonnees() throws Exception {
        // Get the suividonnees
        restSuividonneesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSuividonnees() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();

        // Update the suividonnees
        Suividonnees updatedSuividonnees = suividonneesRepository.findById(suividonnees.getId()).get();
        // Disconnect from session so that the updates on updatedSuividonnees are not directly saved in db
        em.detach(updatedSuividonnees);
        updatedSuividonnees
            .date(UPDATED_DATE)
            .poids(UPDATED_POIDS)
            .epa(UPDATED_EPA)
            .massecorporelle(UPDATED_MASSECORPORELLE)
            .quantitepoidsaliments(UPDATED_QUANTITEPOIDSALIMENTS)
            .quantitecaloriesaliments(UPDATED_QUANTITECALORIESALIMENTS)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(updatedSuividonnees);

        restSuividonneesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, suividonneesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
        Suividonnees testSuividonnees = suividonneesList.get(suividonneesList.size() - 1);
        assertThat(testSuividonnees.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSuividonnees.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testSuividonnees.getEpa()).isEqualTo(UPDATED_EPA);
        assertThat(testSuividonnees.getMassecorporelle()).isEqualTo(UPDATED_MASSECORPORELLE);
        assertThat(testSuividonnees.getQuantitepoidsaliments()).isEqualTo(UPDATED_QUANTITEPOIDSALIMENTS);
        assertThat(testSuividonnees.getQuantitecaloriesaliments()).isEqualTo(UPDATED_QUANTITECALORIESALIMENTS);
        assertThat(testSuividonnees.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testSuividonnees.getAgression()).isEqualTo(UPDATED_AGRESSION);
    }

    @Test
    @Transactional
    void putNonExistingSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, suividonneesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSuividonneesWithPatch() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();

        // Update the suividonnees using partial update
        Suividonnees partialUpdatedSuividonnees = new Suividonnees();
        partialUpdatedSuividonnees.setId(suividonnees.getId());

        partialUpdatedSuividonnees
            .poids(UPDATED_POIDS)
            .massecorporelle(UPDATED_MASSECORPORELLE)
            .quantitecaloriesaliments(UPDATED_QUANTITECALORIESALIMENTS)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);

        restSuividonneesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuividonnees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuividonnees))
            )
            .andExpect(status().isOk());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
        Suividonnees testSuividonnees = suividonneesList.get(suividonneesList.size() - 1);
        assertThat(testSuividonnees.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testSuividonnees.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testSuividonnees.getEpa()).isEqualTo(DEFAULT_EPA);
        assertThat(testSuividonnees.getMassecorporelle()).isEqualTo(UPDATED_MASSECORPORELLE);
        assertThat(testSuividonnees.getQuantitepoidsaliments()).isEqualTo(DEFAULT_QUANTITEPOIDSALIMENTS);
        assertThat(testSuividonnees.getQuantitecaloriesaliments()).isEqualTo(UPDATED_QUANTITECALORIESALIMENTS);
        assertThat(testSuividonnees.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testSuividonnees.getAgression()).isEqualTo(UPDATED_AGRESSION);
    }

    @Test
    @Transactional
    void fullUpdateSuividonneesWithPatch() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();

        // Update the suividonnees using partial update
        Suividonnees partialUpdatedSuividonnees = new Suividonnees();
        partialUpdatedSuividonnees.setId(suividonnees.getId());

        partialUpdatedSuividonnees
            .date(UPDATED_DATE)
            .poids(UPDATED_POIDS)
            .epa(UPDATED_EPA)
            .massecorporelle(UPDATED_MASSECORPORELLE)
            .quantitepoidsaliments(UPDATED_QUANTITEPOIDSALIMENTS)
            .quantitecaloriesaliments(UPDATED_QUANTITECALORIESALIMENTS)
            .absorptionreduite(UPDATED_ABSORPTIONREDUITE)
            .agression(UPDATED_AGRESSION);

        restSuividonneesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSuividonnees.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSuividonnees))
            )
            .andExpect(status().isOk());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
        Suividonnees testSuividonnees = suividonneesList.get(suividonneesList.size() - 1);
        assertThat(testSuividonnees.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testSuividonnees.getPoids()).isEqualTo(UPDATED_POIDS);
        assertThat(testSuividonnees.getEpa()).isEqualTo(UPDATED_EPA);
        assertThat(testSuividonnees.getMassecorporelle()).isEqualTo(UPDATED_MASSECORPORELLE);
        assertThat(testSuividonnees.getQuantitepoidsaliments()).isEqualTo(UPDATED_QUANTITEPOIDSALIMENTS);
        assertThat(testSuividonnees.getQuantitecaloriesaliments()).isEqualTo(UPDATED_QUANTITECALORIESALIMENTS);
        assertThat(testSuividonnees.getAbsorptionreduite()).isEqualTo(UPDATED_ABSORPTIONREDUITE);
        assertThat(testSuividonnees.getAgression()).isEqualTo(UPDATED_AGRESSION);
    }

    @Test
    @Transactional
    void patchNonExistingSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, suividonneesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSuividonnees() throws Exception {
        int databaseSizeBeforeUpdate = suividonneesRepository.findAll().size();
        suividonnees.setId(count.incrementAndGet());

        // Create the Suividonnees
        SuividonneesDTO suividonneesDTO = suividonneesMapper.toDto(suividonnees);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSuividonneesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(suividonneesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Suividonnees in the database
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSuividonnees() throws Exception {
        // Initialize the database
        suividonneesRepository.saveAndFlush(suividonnees);

        int databaseSizeBeforeDelete = suividonneesRepository.findAll().size();

        // Delete the suividonnees
        restSuividonneesMockMvc
            .perform(delete(ENTITY_API_URL_ID, suividonnees.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suividonnees> suividonneesList = suividonneesRepository.findAll();
        assertThat(suividonneesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
