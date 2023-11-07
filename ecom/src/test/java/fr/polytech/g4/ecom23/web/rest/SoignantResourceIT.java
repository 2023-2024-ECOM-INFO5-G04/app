package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.domain.Soignant;
import fr.polytech.g4.ecom23.domain.enumeration.SoignantMetier;
import fr.polytech.g4.ecom23.repository.SoignantRepository;
import fr.polytech.g4.ecom23.service.SoignantService;
import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import fr.polytech.g4.ecom23.service.mapper.SoignantMapper;
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
 * Integration tests for the {@link SoignantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SoignantResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final SoignantMetier DEFAULT_METIER = SoignantMetier.Aidesoignant;
    private static final SoignantMetier UPDATED_METIER = SoignantMetier.Infirmier;

    private static final String ENTITY_API_URL = "/api/soignants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoignantRepository soignantRepository;

    @Mock
    private SoignantRepository soignantRepositoryMock;

    @Autowired
    private SoignantMapper soignantMapper;

    @Mock
    private SoignantService soignantServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoignantMockMvc;

    private Soignant soignant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soignant createEntity(EntityManager em) {
        Soignant soignant = new Soignant().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM).metier(DEFAULT_METIER);
        // Add required entity
        Servicesoignant servicesoignant;
        if (TestUtil.findAll(em, Servicesoignant.class).isEmpty()) {
            servicesoignant = ServicesoignantResourceIT.createEntity(em);
            em.persist(servicesoignant);
            em.flush();
        } else {
            servicesoignant = TestUtil.findAll(em, Servicesoignant.class).get(0);
        }
        soignant.setServicesoignant(servicesoignant);
        return soignant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Soignant createUpdatedEntity(EntityManager em) {
        Soignant soignant = new Soignant().nom(UPDATED_NOM).prenom(UPDATED_PRENOM).metier(UPDATED_METIER);
        // Add required entity
        Servicesoignant servicesoignant;
        if (TestUtil.findAll(em, Servicesoignant.class).isEmpty()) {
            servicesoignant = ServicesoignantResourceIT.createUpdatedEntity(em);
            em.persist(servicesoignant);
            em.flush();
        } else {
            servicesoignant = TestUtil.findAll(em, Servicesoignant.class).get(0);
        }
        soignant.setServicesoignant(servicesoignant);
        return soignant;
    }

    @BeforeEach
    public void initTest() {
        soignant = createEntity(em);
    }

    @Test
    @Transactional
    void createSoignant() throws Exception {
        int databaseSizeBeforeCreate = soignantRepository.findAll().size();
        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);
        restSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soignantDTO)))
            .andExpect(status().isCreated());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeCreate + 1);
        Soignant testSoignant = soignantList.get(soignantList.size() - 1);
        assertThat(testSoignant.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSoignant.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testSoignant.getMetier()).isEqualTo(DEFAULT_METIER);
    }

    @Test
    @Transactional
    void createSoignantWithExistingId() throws Exception {
        // Create the Soignant with an existing ID
        soignant.setId(1L);
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        int databaseSizeBeforeCreate = soignantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoignantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soignantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoignants() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        // Get all the soignantList
        restSoignantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].metier").value(hasItem(DEFAULT_METIER.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSoignantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(soignantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(soignantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSoignantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(soignantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(soignantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSoignant() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        // Get the soignant
        restSoignantMockMvc
            .perform(get(ENTITY_API_URL_ID, soignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soignant.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.metier").value(DEFAULT_METIER.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSoignant() throws Exception {
        // Get the soignant
        restSoignantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSoignant() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();

        // Update the soignant
        Soignant updatedSoignant = soignantRepository.findById(soignant.getId()).get();
        // Disconnect from session so that the updates on updatedSoignant are not directly saved in db
        em.detach(updatedSoignant);
        updatedSoignant.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).metier(UPDATED_METIER);
        SoignantDTO soignantDTO = soignantMapper.toDto(updatedSoignant);

        restSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soignantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
        Soignant testSoignant = soignantList.get(soignantList.size() - 1);
        assertThat(testSoignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSoignant.getMetier()).isEqualTo(UPDATED_METIER);
    }

    @Test
    @Transactional
    void putNonExistingSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soignantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soignantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoignantWithPatch() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();

        // Update the soignant using partial update
        Soignant partialUpdatedSoignant = new Soignant();
        partialUpdatedSoignant.setId(soignant.getId());

        partialUpdatedSoignant.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).metier(UPDATED_METIER);

        restSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoignant))
            )
            .andExpect(status().isOk());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
        Soignant testSoignant = soignantList.get(soignantList.size() - 1);
        assertThat(testSoignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSoignant.getMetier()).isEqualTo(UPDATED_METIER);
    }

    @Test
    @Transactional
    void fullUpdateSoignantWithPatch() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();

        // Update the soignant using partial update
        Soignant partialUpdatedSoignant = new Soignant();
        partialUpdatedSoignant.setId(soignant.getId());

        partialUpdatedSoignant.nom(UPDATED_NOM).prenom(UPDATED_PRENOM).metier(UPDATED_METIER);

        restSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoignant))
            )
            .andExpect(status().isOk());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
        Soignant testSoignant = soignantList.get(soignantList.size() - 1);
        assertThat(testSoignant.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSoignant.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testSoignant.getMetier()).isEqualTo(UPDATED_METIER);
    }

    @Test
    @Transactional
    void patchNonExistingSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soignantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoignant() throws Exception {
        int databaseSizeBeforeUpdate = soignantRepository.findAll().size();
        soignant.setId(count.incrementAndGet());

        // Create the Soignant
        SoignantDTO soignantDTO = soignantMapper.toDto(soignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoignantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soignantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Soignant in the database
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoignant() throws Exception {
        // Initialize the database
        soignantRepository.saveAndFlush(soignant);

        int databaseSizeBeforeDelete = soignantRepository.findAll().size();

        // Delete the soignant
        restSoignantMockMvc
            .perform(delete(ENTITY_API_URL_ID, soignant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Soignant> soignantList = soignantRepository.findAll();
        assertThat(soignantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
