package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Rappel;
import fr.polytech.g4.ecom23.repository.RappelRepository;
import fr.polytech.g4.ecom23.service.dto.RappelDTO;
import fr.polytech.g4.ecom23.service.mapper.RappelMapper;
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
 * Integration tests for the {@link RappelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RappelResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EFFECTUE = false;
    private static final Boolean UPDATED_EFFECTUE = true;

    private static final String ENTITY_API_URL = "/api/rappels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RappelRepository rappelRepository;

    @Autowired
    private RappelMapper rappelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRappelMockMvc;

    private Rappel rappel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rappel createEntity(EntityManager em) {
        Rappel rappel = new Rappel().date(DEFAULT_DATE).commentaire(DEFAULT_COMMENTAIRE).effectue(DEFAULT_EFFECTUE);
        return rappel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rappel createUpdatedEntity(EntityManager em) {
        Rappel rappel = new Rappel().date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectue(UPDATED_EFFECTUE);
        return rappel;
    }

    @BeforeEach
    public void initTest() {
        rappel = createEntity(em);
    }

    @Test
    @Transactional
    void createRappel() throws Exception {
        int databaseSizeBeforeCreate = rappelRepository.findAll().size();
        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);
        restRappelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rappelDTO)))
            .andExpect(status().isCreated());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeCreate + 1);
        Rappel testRappel = rappelList.get(rappelList.size() - 1);
        assertThat(testRappel.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testRappel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testRappel.getEffectue()).isEqualTo(DEFAULT_EFFECTUE);
    }

    @Test
    @Transactional
    void createRappelWithExistingId() throws Exception {
        // Create the Rappel with an existing ID
        rappel.setId(1L);
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        int databaseSizeBeforeCreate = rappelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRappelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rappelDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRappels() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        // Get all the rappelList
        restRappelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rappel.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].effectue").value(hasItem(DEFAULT_EFFECTUE.booleanValue())));
    }

    @Test
    @Transactional
    void getRappel() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        // Get the rappel
        restRappelMockMvc
            .perform(get(ENTITY_API_URL_ID, rappel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rappel.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.effectue").value(DEFAULT_EFFECTUE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingRappel() throws Exception {
        // Get the rappel
        restRappelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRappel() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();

        // Update the rappel
        Rappel updatedRappel = rappelRepository.findById(rappel.getId()).get();
        // Disconnect from session so that the updates on updatedRappel are not directly saved in db
        em.detach(updatedRappel);
        updatedRappel.date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectue(UPDATED_EFFECTUE);
        RappelDTO rappelDTO = rappelMapper.toDto(updatedRappel);

        restRappelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rappelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
        Rappel testRappel = rappelList.get(rappelList.size() - 1);
        assertThat(testRappel.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRappel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testRappel.getEffectue()).isEqualTo(UPDATED_EFFECTUE);
    }

    @Test
    @Transactional
    void putNonExistingRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rappelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rappelDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRappelWithPatch() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();

        // Update the rappel using partial update
        Rappel partialUpdatedRappel = new Rappel();
        partialUpdatedRappel.setId(rappel.getId());

        partialUpdatedRappel.date(UPDATED_DATE).effectue(UPDATED_EFFECTUE);

        restRappelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRappel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRappel))
            )
            .andExpect(status().isOk());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
        Rappel testRappel = rappelList.get(rappelList.size() - 1);
        assertThat(testRappel.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRappel.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testRappel.getEffectue()).isEqualTo(UPDATED_EFFECTUE);
    }

    @Test
    @Transactional
    void fullUpdateRappelWithPatch() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();

        // Update the rappel using partial update
        Rappel partialUpdatedRappel = new Rappel();
        partialUpdatedRappel.setId(rappel.getId());

        partialUpdatedRappel.date(UPDATED_DATE).commentaire(UPDATED_COMMENTAIRE).effectue(UPDATED_EFFECTUE);

        restRappelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRappel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRappel))
            )
            .andExpect(status().isOk());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
        Rappel testRappel = rappelList.get(rappelList.size() - 1);
        assertThat(testRappel.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testRappel.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testRappel.getEffectue()).isEqualTo(UPDATED_EFFECTUE);
    }

    @Test
    @Transactional
    void patchNonExistingRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rappelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRappel() throws Exception {
        int databaseSizeBeforeUpdate = rappelRepository.findAll().size();
        rappel.setId(count.incrementAndGet());

        // Create the Rappel
        RappelDTO rappelDTO = rappelMapper.toDto(rappel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRappelMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rappelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rappel in the database
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRappel() throws Exception {
        // Initialize the database
        rappelRepository.saveAndFlush(rappel);

        int databaseSizeBeforeDelete = rappelRepository.findAll().size();

        // Delete the rappel
        restRappelMockMvc
            .perform(delete(ENTITY_API_URL_ID, rappel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rappel> rappelList = rappelRepository.findAll();
        assertThat(rappelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
