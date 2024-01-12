package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Alerte;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.repository.AlerteRepository;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import fr.polytech.g4.ecom23.service.mapper.AlerteMapper;
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
 * Integration tests for the {@link AlerteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AlerteResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DENUTRITION = false;
    private static final Boolean UPDATED_DENUTRITION = true;

    private static final Boolean DEFAULT_SEVERITE = false;
    private static final Boolean UPDATED_SEVERITE = true;

    private static final Boolean DEFAULT_CONSULTE = false;
    private static final Boolean UPDATED_CONSULTE = true;

    private static final String ENTITY_API_URL = "/api/alertes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AlerteRepository alerteRepository;

    @Autowired
    private AlerteMapper alerteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAlerteMockMvc;

    private Alerte alerte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerte createEntity(EntityManager em) {
        Alerte alerte = new Alerte()
            .date(DEFAULT_DATE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .denutrition(DEFAULT_DENUTRITION)
            .severite(DEFAULT_SEVERITE)
            .consulte(DEFAULT_CONSULTE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        alerte.setPatient(patient);
        return alerte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alerte createUpdatedEntity(EntityManager em) {
        Alerte alerte = new Alerte()
            .date(UPDATED_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .denutrition(UPDATED_DENUTRITION)
            .severite(UPDATED_SEVERITE)
            .consulte(UPDATED_CONSULTE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        alerte.setPatient(patient);
        return alerte;
    }

    @BeforeEach
    public void initTest() {
        alerte = createEntity(em);
    }

    @Test
    @Transactional
    void createAlerte() throws Exception {
        int databaseSizeBeforeCreate = alerteRepository.findAll().size();
        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);
        restAlerteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alerteDTO)))
            .andExpect(status().isCreated());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeCreate + 1);
        Alerte testAlerte = alerteList.get(alerteList.size() - 1);
        assertThat(testAlerte.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAlerte.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testAlerte.getDenutrition()).isEqualTo(DEFAULT_DENUTRITION);
        assertThat(testAlerte.getSeverite()).isEqualTo(DEFAULT_SEVERITE);
        assertThat(testAlerte.getConsulte()).isEqualTo(DEFAULT_CONSULTE);
    }

    @Test
    @Transactional
    void createAlerteWithExistingId() throws Exception {
        // Create the Alerte with an existing ID
        alerte.setId(1L);
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        int databaseSizeBeforeCreate = alerteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlerteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alerteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAlertes() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        // Get all the alerteList
        restAlerteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alerte.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].denutrition").value(hasItem(DEFAULT_DENUTRITION.booleanValue())))
            .andExpect(jsonPath("$.[*].severite").value(hasItem(DEFAULT_SEVERITE.booleanValue())))
            .andExpect(jsonPath("$.[*].consulte").value(hasItem(DEFAULT_CONSULTE.booleanValue())));
    }

    @Test
    @Transactional
    void getAlerte() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        // Get the alerte
        restAlerteMockMvc
            .perform(get(ENTITY_API_URL_ID, alerte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(alerte.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.denutrition").value(DEFAULT_DENUTRITION.booleanValue()))
            .andExpect(jsonPath("$.severite").value(DEFAULT_SEVERITE.booleanValue()))
            .andExpect(jsonPath("$.consulte").value(DEFAULT_CONSULTE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAlerte() throws Exception {
        // Get the alerte
        restAlerteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAlerte() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();

        // Update the alerte
        Alerte updatedAlerte = alerteRepository.findById(alerte.getId()).get();
        // Disconnect from session so that the updates on updatedAlerte are not directly saved in db
        em.detach(updatedAlerte);
        updatedAlerte
            .date(UPDATED_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .denutrition(UPDATED_DENUTRITION)
            .severite(UPDATED_SEVERITE)
            .consulte(UPDATED_CONSULTE);
        AlerteDTO alerteDTO = alerteMapper.toDto(updatedAlerte);

        restAlerteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alerteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
        Alerte testAlerte = alerteList.get(alerteList.size() - 1);
        assertThat(testAlerte.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAlerte.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAlerte.getDenutrition()).isEqualTo(UPDATED_DENUTRITION);
        assertThat(testAlerte.getSeverite()).isEqualTo(UPDATED_SEVERITE);
        assertThat(testAlerte.getConsulte()).isEqualTo(UPDATED_CONSULTE);
    }

    @Test
    @Transactional
    void putNonExistingAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, alerteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(alerteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAlerteWithPatch() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();

        // Update the alerte using partial update
        Alerte partialUpdatedAlerte = new Alerte();
        partialUpdatedAlerte.setId(alerte.getId());

        partialUpdatedAlerte
            .date(UPDATED_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .denutrition(UPDATED_DENUTRITION)
            .consulte(UPDATED_CONSULTE);

        restAlerteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlerte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlerte))
            )
            .andExpect(status().isOk());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
        Alerte testAlerte = alerteList.get(alerteList.size() - 1);
        assertThat(testAlerte.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAlerte.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAlerte.getDenutrition()).isEqualTo(UPDATED_DENUTRITION);
        assertThat(testAlerte.getSeverite()).isEqualTo(DEFAULT_SEVERITE);
        assertThat(testAlerte.getConsulte()).isEqualTo(UPDATED_CONSULTE);
    }

    @Test
    @Transactional
    void fullUpdateAlerteWithPatch() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();

        // Update the alerte using partial update
        Alerte partialUpdatedAlerte = new Alerte();
        partialUpdatedAlerte.setId(alerte.getId());

        partialUpdatedAlerte
            .date(UPDATED_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .denutrition(UPDATED_DENUTRITION)
            .severite(UPDATED_SEVERITE)
            .consulte(UPDATED_CONSULTE);

        restAlerteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAlerte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAlerte))
            )
            .andExpect(status().isOk());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
        Alerte testAlerte = alerteList.get(alerteList.size() - 1);
        assertThat(testAlerte.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAlerte.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAlerte.getDenutrition()).isEqualTo(UPDATED_DENUTRITION);
        assertThat(testAlerte.getSeverite()).isEqualTo(UPDATED_SEVERITE);
        assertThat(testAlerte.getConsulte()).isEqualTo(UPDATED_CONSULTE);
    }

    @Test
    @Transactional
    void patchNonExistingAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, alerteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAlerte() throws Exception {
        int databaseSizeBeforeUpdate = alerteRepository.findAll().size();
        alerte.setId(count.incrementAndGet());

        // Create the Alerte
        AlerteDTO alerteDTO = alerteMapper.toDto(alerte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAlerteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(alerteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Alerte in the database
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAlerte() throws Exception {
        // Initialize the database
        alerteRepository.saveAndFlush(alerte);

        int databaseSizeBeforeDelete = alerteRepository.findAll().size();

        // Delete the alerte
        restAlerteMockMvc
            .perform(delete(ENTITY_API_URL_ID, alerte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Alerte> alerteList = alerteRepository.findAll();
        assertThat(alerteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
