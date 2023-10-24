package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.repository.ServicesoignantRepository;
import fr.polytech.g4.ecom23.service.ServicesoignantService;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import fr.polytech.g4.ecom23.service.mapper.ServicesoignantMapper;
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
 * Integration tests for the {@link ServicesoignantResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ServicesoignantResourceIT {

    private static final Long DEFAULT_ID_SER = 1L;
    private static final Long UPDATED_ID_SER = 2L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NBSOIGNANTS = "AAAAAAAAAA";
    private static final String UPDATED_NBSOIGNANTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/servicesoignants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ServicesoignantRepository servicesoignantRepository;

    @Mock
    private ServicesoignantRepository servicesoignantRepositoryMock;

    @Autowired
    private ServicesoignantMapper servicesoignantMapper;

    @Mock
    private ServicesoignantService servicesoignantServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServicesoignantMockMvc;

    private Servicesoignant servicesoignant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicesoignant createEntity(EntityManager em) {
        Servicesoignant servicesoignant = new Servicesoignant().idSer(DEFAULT_ID_SER).type(DEFAULT_TYPE).nbsoignants(DEFAULT_NBSOIGNANTS);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        servicesoignant.setInfrastructure(etablissement);
        return servicesoignant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Servicesoignant createUpdatedEntity(EntityManager em) {
        Servicesoignant servicesoignant = new Servicesoignant().idSer(UPDATED_ID_SER).type(UPDATED_TYPE).nbsoignants(UPDATED_NBSOIGNANTS);
        // Add required entity
        Etablissement etablissement;
        if (TestUtil.findAll(em, Etablissement.class).isEmpty()) {
            etablissement = EtablissementResourceIT.createUpdatedEntity(em);
            em.persist(etablissement);
            em.flush();
        } else {
            etablissement = TestUtil.findAll(em, Etablissement.class).get(0);
        }
        servicesoignant.setInfrastructure(etablissement);
        return servicesoignant;
    }

    @BeforeEach
    public void initTest() {
        servicesoignant = createEntity(em);
    }

    @Test
    @Transactional
    void createServicesoignant() throws Exception {
        int databaseSizeBeforeCreate = servicesoignantRepository.findAll().size();
        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);
        restServicesoignantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeCreate + 1);
        Servicesoignant testServicesoignant = servicesoignantList.get(servicesoignantList.size() - 1);
        assertThat(testServicesoignant.getIdSer()).isEqualTo(DEFAULT_ID_SER);
        assertThat(testServicesoignant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testServicesoignant.getNbsoignants()).isEqualTo(DEFAULT_NBSOIGNANTS);
    }

    @Test
    @Transactional
    void createServicesoignantWithExistingId() throws Exception {
        // Create the Servicesoignant with an existing ID
        servicesoignant.setId(1L);
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        int databaseSizeBeforeCreate = servicesoignantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restServicesoignantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdSerIsRequired() throws Exception {
        int databaseSizeBeforeTest = servicesoignantRepository.findAll().size();
        // set the field null
        servicesoignant.setIdSer(null);

        // Create the Servicesoignant, which fails.
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        restServicesoignantMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllServicesoignants() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        // Get all the servicesoignantList
        restServicesoignantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(servicesoignant.getId().intValue())))
            .andExpect(jsonPath("$.[*].idSer").value(hasItem(DEFAULT_ID_SER.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nbsoignants").value(hasItem(DEFAULT_NBSOIGNANTS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicesoignantsWithEagerRelationshipsIsEnabled() throws Exception {
        when(servicesoignantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicesoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(servicesoignantServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllServicesoignantsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(servicesoignantServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restServicesoignantMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(servicesoignantRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getServicesoignant() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        // Get the servicesoignant
        restServicesoignantMockMvc
            .perform(get(ENTITY_API_URL_ID, servicesoignant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(servicesoignant.getId().intValue()))
            .andExpect(jsonPath("$.idSer").value(DEFAULT_ID_SER.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.nbsoignants").value(DEFAULT_NBSOIGNANTS));
    }

    @Test
    @Transactional
    void getNonExistingServicesoignant() throws Exception {
        // Get the servicesoignant
        restServicesoignantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingServicesoignant() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();

        // Update the servicesoignant
        Servicesoignant updatedServicesoignant = servicesoignantRepository.findById(servicesoignant.getId()).get();
        // Disconnect from session so that the updates on updatedServicesoignant are not directly saved in db
        em.detach(updatedServicesoignant);
        updatedServicesoignant.idSer(UPDATED_ID_SER).type(UPDATED_TYPE).nbsoignants(UPDATED_NBSOIGNANTS);
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(updatedServicesoignant);

        restServicesoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicesoignantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
        Servicesoignant testServicesoignant = servicesoignantList.get(servicesoignantList.size() - 1);
        assertThat(testServicesoignant.getIdSer()).isEqualTo(UPDATED_ID_SER);
        assertThat(testServicesoignant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testServicesoignant.getNbsoignants()).isEqualTo(UPDATED_NBSOIGNANTS);
    }

    @Test
    @Transactional
    void putNonExistingServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, servicesoignantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateServicesoignantWithPatch() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();

        // Update the servicesoignant using partial update
        Servicesoignant partialUpdatedServicesoignant = new Servicesoignant();
        partialUpdatedServicesoignant.setId(servicesoignant.getId());

        partialUpdatedServicesoignant.idSer(UPDATED_ID_SER).nbsoignants(UPDATED_NBSOIGNANTS);

        restServicesoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicesoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicesoignant))
            )
            .andExpect(status().isOk());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
        Servicesoignant testServicesoignant = servicesoignantList.get(servicesoignantList.size() - 1);
        assertThat(testServicesoignant.getIdSer()).isEqualTo(UPDATED_ID_SER);
        assertThat(testServicesoignant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testServicesoignant.getNbsoignants()).isEqualTo(UPDATED_NBSOIGNANTS);
    }

    @Test
    @Transactional
    void fullUpdateServicesoignantWithPatch() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();

        // Update the servicesoignant using partial update
        Servicesoignant partialUpdatedServicesoignant = new Servicesoignant();
        partialUpdatedServicesoignant.setId(servicesoignant.getId());

        partialUpdatedServicesoignant.idSer(UPDATED_ID_SER).type(UPDATED_TYPE).nbsoignants(UPDATED_NBSOIGNANTS);

        restServicesoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedServicesoignant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedServicesoignant))
            )
            .andExpect(status().isOk());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
        Servicesoignant testServicesoignant = servicesoignantList.get(servicesoignantList.size() - 1);
        assertThat(testServicesoignant.getIdSer()).isEqualTo(UPDATED_ID_SER);
        assertThat(testServicesoignant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testServicesoignant.getNbsoignants()).isEqualTo(UPDATED_NBSOIGNANTS);
    }

    @Test
    @Transactional
    void patchNonExistingServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, servicesoignantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamServicesoignant() throws Exception {
        int databaseSizeBeforeUpdate = servicesoignantRepository.findAll().size();
        servicesoignant.setId(count.incrementAndGet());

        // Create the Servicesoignant
        ServicesoignantDTO servicesoignantDTO = servicesoignantMapper.toDto(servicesoignant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restServicesoignantMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(servicesoignantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Servicesoignant in the database
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteServicesoignant() throws Exception {
        // Initialize the database
        servicesoignantRepository.saveAndFlush(servicesoignant);

        int databaseSizeBeforeDelete = servicesoignantRepository.findAll().size();

        // Delete the servicesoignant
        restServicesoignantMockMvc
            .perform(delete(ENTITY_API_URL_ID, servicesoignant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Servicesoignant> servicesoignantList = servicesoignantRepository.findAll();
        assertThat(servicesoignantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
