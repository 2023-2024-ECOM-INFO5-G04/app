package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.repository.MedecinRepository;
import fr.polytech.g4.ecom23.service.MedecinService;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.mapper.MedecinMapper;
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
 * Integration tests for the {@link MedecinResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MedecinResourceIT {

    private static final Long DEFAULT_ID_M = 1L;
    private static final Long UPDATED_ID_M = 2L;

    private static final String DEFAULT_NOM_M = "AAAAAAAAAA";
    private static final String UPDATED_NOM_M = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_M = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_M = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medecins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedecinRepository medecinRepository;

    @Mock
    private MedecinRepository medecinRepositoryMock;

    @Autowired
    private MedecinMapper medecinMapper;

    @Mock
    private MedecinService medecinServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedecinMockMvc;

    private Medecin medecin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createEntity(EntityManager em) {
        Medecin medecin = new Medecin().idM(DEFAULT_ID_M).nomM(DEFAULT_NOM_M).prenomM(DEFAULT_PRENOM_M);
        return medecin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medecin createUpdatedEntity(EntityManager em) {
        Medecin medecin = new Medecin().idM(UPDATED_ID_M).nomM(UPDATED_NOM_M).prenomM(UPDATED_PRENOM_M);
        return medecin;
    }

    @BeforeEach
    public void initTest() {
        medecin = createEntity(em);
    }

    @Test
    @Transactional
    void createMedecin() throws Exception {
        int databaseSizeBeforeCreate = medecinRepository.findAll().size();
        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);
        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecinDTO)))
            .andExpect(status().isCreated());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate + 1);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdM()).isEqualTo(DEFAULT_ID_M);
        assertThat(testMedecin.getNomM()).isEqualTo(DEFAULT_NOM_M);
        assertThat(testMedecin.getPrenomM()).isEqualTo(DEFAULT_PRENOM_M);
    }

    @Test
    @Transactional
    void createMedecinWithExistingId() throws Exception {
        // Create the Medecin with an existing ID
        medecin.setId(1L);
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        int databaseSizeBeforeCreate = medecinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdMIsRequired() throws Exception {
        int databaseSizeBeforeTest = medecinRepository.findAll().size();
        // set the field null
        medecin.setIdM(null);

        // Create the Medecin, which fails.
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        restMedecinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecinDTO)))
            .andExpect(status().isBadRequest());

        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedecins() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get all the medecinList
        restMedecinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medecin.getId().intValue())))
            .andExpect(jsonPath("$.[*].idM").value(hasItem(DEFAULT_ID_M.intValue())))
            .andExpect(jsonPath("$.[*].nomM").value(hasItem(DEFAULT_NOM_M)))
            .andExpect(jsonPath("$.[*].prenomM").value(hasItem(DEFAULT_PRENOM_M)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMedecinsWithEagerRelationshipsIsEnabled() throws Exception {
        when(medecinServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMedecinMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(medecinServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllMedecinsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(medecinServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMedecinMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(medecinRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        // Get the medecin
        restMedecinMockMvc
            .perform(get(ENTITY_API_URL_ID, medecin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medecin.getId().intValue()))
            .andExpect(jsonPath("$.idM").value(DEFAULT_ID_M.intValue()))
            .andExpect(jsonPath("$.nomM").value(DEFAULT_NOM_M))
            .andExpect(jsonPath("$.prenomM").value(DEFAULT_PRENOM_M));
    }

    @Test
    @Transactional
    void getNonExistingMedecin() throws Exception {
        // Get the medecin
        restMedecinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin
        Medecin updatedMedecin = medecinRepository.findById(medecin.getId()).get();
        // Disconnect from session so that the updates on updatedMedecin are not directly saved in db
        em.detach(updatedMedecin);
        updatedMedecin.idM(UPDATED_ID_M).nomM(UPDATED_NOM_M).prenomM(UPDATED_PRENOM_M);
        MedecinDTO medecinDTO = medecinMapper.toDto(updatedMedecin);

        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medecinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdM()).isEqualTo(UPDATED_ID_M);
        assertThat(testMedecin.getNomM()).isEqualTo(UPDATED_NOM_M);
        assertThat(testMedecin.getPrenomM()).isEqualTo(UPDATED_PRENOM_M);
    }

    @Test
    @Transactional
    void putNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medecinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medecinDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedecinWithPatch() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin using partial update
        Medecin partialUpdatedMedecin = new Medecin();
        partialUpdatedMedecin.setId(medecin.getId());

        partialUpdatedMedecin.nomM(UPDATED_NOM_M).prenomM(UPDATED_PRENOM_M);

        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedecin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedecin))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdM()).isEqualTo(DEFAULT_ID_M);
        assertThat(testMedecin.getNomM()).isEqualTo(UPDATED_NOM_M);
        assertThat(testMedecin.getPrenomM()).isEqualTo(UPDATED_PRENOM_M);
    }

    @Test
    @Transactional
    void fullUpdateMedecinWithPatch() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();

        // Update the medecin using partial update
        Medecin partialUpdatedMedecin = new Medecin();
        partialUpdatedMedecin.setId(medecin.getId());

        partialUpdatedMedecin.idM(UPDATED_ID_M).nomM(UPDATED_NOM_M).prenomM(UPDATED_PRENOM_M);

        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedecin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedecin))
            )
            .andExpect(status().isOk());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
        Medecin testMedecin = medecinList.get(medecinList.size() - 1);
        assertThat(testMedecin.getIdM()).isEqualTo(UPDATED_ID_M);
        assertThat(testMedecin.getNomM()).isEqualTo(UPDATED_NOM_M);
        assertThat(testMedecin.getPrenomM()).isEqualTo(UPDATED_PRENOM_M);
    }

    @Test
    @Transactional
    void patchNonExistingMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medecinDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedecin() throws Exception {
        int databaseSizeBeforeUpdate = medecinRepository.findAll().size();
        medecin.setId(count.incrementAndGet());

        // Create the Medecin
        MedecinDTO medecinDTO = medecinMapper.toDto(medecin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedecinMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medecinDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medecin in the database
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedecin() throws Exception {
        // Initialize the database
        medecinRepository.saveAndFlush(medecin);

        int databaseSizeBeforeDelete = medecinRepository.findAll().size();

        // Delete the medecin
        restMedecinMockMvc
            .perform(delete(ENTITY_API_URL_ID, medecin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medecin> medecinList = medecinRepository.findAll();
        assertThat(medecinList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
