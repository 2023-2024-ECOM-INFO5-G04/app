package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Tache;
import fr.polytech.g4.ecom23.repository.TacheRepository;
import fr.polytech.g4.ecom23.service.TacheService;
import fr.polytech.g4.ecom23.service.dto.TacheDTO;
import fr.polytech.g4.ecom23.service.mapper.TacheMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TacheResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TacheResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EFFECTUEE = false;
    private static final Boolean UPDATED_EFFECTUEE = true;

    private static final String ENTITY_API_URL = "/api/taches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TacheRepository tacheRepository;

    @Mock
    private TacheRepository tacheRepositoryMock;

    @Autowired
    private TacheMapper tacheMapper;

    @Mock
    private TacheService tacheServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTacheMockMvc;

    private Tache tache;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createEntity(EntityManager em) {
        Tache tache = new Tache().date(DEFAULT_DATE).commentaire(DEFAULT_COMMENTAIRE).effectuee(DEFAULT_EFFECTUEE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        tache.setPatient(patient);
        return tache;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tache createUpdatedEntity(EntityManager em) {
        Tache tache = new Tache().date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectuee(UPDATED_EFFECTUEE);
        // Add required entity
        Patient patient;
        if (TestUtil.findAll(em, Patient.class).isEmpty()) {
            patient = PatientResourceIT.createUpdatedEntity(em);
            em.persist(patient);
            em.flush();
        } else {
            patient = TestUtil.findAll(em, Patient.class).get(0);
        }
        tache.setPatient(patient);
        return tache;
    }

    @BeforeEach
    public void initTest() {
        tache = createEntity(em);
    }

    @Test
    @Transactional
    void createTache() throws Exception {
        int databaseSizeBeforeCreate = tacheRepository.findAll().size();
        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);
        restTacheMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tacheDTO)))
            .andExpect(status().isCreated());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate + 1);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTache.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testTache.getEffectuee()).isEqualTo(DEFAULT_EFFECTUEE);
    }

    @Test
    @Transactional
    void createTacheWithExistingId() throws Exception {
        // Create the Tache with an existing ID
        tache.setId(1L);
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        int databaseSizeBeforeCreate = tacheRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTacheMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tacheDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTaches() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get all the tacheList
        restTacheMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tache.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].effectuee").value(hasItem(DEFAULT_EFFECTUEE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTachesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tacheServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTacheMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tacheServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTachesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tacheServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTacheMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tacheRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        // Get the tache
        restTacheMockMvc
            .perform(get(ENTITY_API_URL_ID, tache.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tache.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.effectuee").value(DEFAULT_EFFECTUEE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingTache() throws Exception {
        // Get the tache
        restTacheMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache
        Tache updatedTache = tacheRepository.findById(tache.getId()).get();
        // Disconnect from session so that the updates on updatedTache are not directly saved in db
        em.detach(updatedTache);
        updatedTache.date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectuee(UPDATED_EFFECTUEE);
        TacheDTO tacheDTO = tacheMapper.toDto(updatedTache);

        restTacheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tacheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tacheDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTache.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testTache.getEffectuee()).isEqualTo(UPDATED_EFFECTUEE);
    }

    @Test
    @Transactional
    void putNonExistingTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tacheDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tacheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tacheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tacheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTacheWithPatch() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache using partial update
        Tache partialUpdatedTache = new Tache();
        partialUpdatedTache.setId(tache.getId());

        partialUpdatedTache.date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE);

        restTacheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTache.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTache))
            )
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTache.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testTache.getEffectuee()).isEqualTo(DEFAULT_EFFECTUEE);
    }

    @Test
    @Transactional
    void fullUpdateTacheWithPatch() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();

        // Update the tache using partial update
        Tache partialUpdatedTache = new Tache();
        partialUpdatedTache.setId(tache.getId());

        partialUpdatedTache.date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectuee(UPDATED_EFFECTUEE);

        restTacheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTache.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTache))
            )
            .andExpect(status().isOk());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
        Tache testTache = tacheList.get(tacheList.size() - 1);
        assertThat(testTache.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTache.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testTache.getEffectuee()).isEqualTo(UPDATED_EFFECTUEE);
    }

    @Test
    @Transactional
    void patchNonExistingTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tacheDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tacheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tacheDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTache() throws Exception {
        int databaseSizeBeforeUpdate = tacheRepository.findAll().size();
        tache.setId(count.incrementAndGet());

        // Create the Tache
        TacheDTO tacheDTO = tacheMapper.toDto(tache);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTacheMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tacheDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tache in the database
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTache() throws Exception {
        // Initialize the database
        tacheRepository.saveAndFlush(tache);

        int databaseSizeBeforeDelete = tacheRepository.findAll().size();

        // Delete the tache
        restTacheMockMvc
            .perform(delete(ENTITY_API_URL_ID, tache.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tache> tacheList = tacheRepository.findAll();
        assertThat(tacheList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
