package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Administrateur;
import fr.polytech.g4.ecom23.repository.AdministrateurRepository;
import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
import fr.polytech.g4.ecom23.service.mapper.AdministrateurMapper;
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
 * Integration tests for the {@link AdministrateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AdministrateurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/administrateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AdministrateurRepository administrateurRepository;

    @Autowired
    private AdministrateurMapper administrateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdministrateurMockMvc;

    private Administrateur administrateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administrateur createEntity(EntityManager em) {
        Administrateur administrateur = new Administrateur().nom(DEFAULT_NOM).prenom(DEFAULT_PRENOM);
        return administrateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Administrateur createUpdatedEntity(EntityManager em) {
        Administrateur administrateur = new Administrateur().nom(UPDATED_NOM).prenom(UPDATED_PRENOM);
        return administrateur;
    }

    @BeforeEach
    public void initTest() {
        administrateur = createEntity(em);
    }

    @Test
    @Transactional
    void createAdministrateur() throws Exception {
        int databaseSizeBeforeCreate = administrateurRepository.findAll().size();
        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);
        restAdministrateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeCreate + 1);
        Administrateur testAdministrateur = administrateurList.get(administrateurList.size() - 1);
        assertThat(testAdministrateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAdministrateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    void createAdministrateurWithExistingId() throws Exception {
        // Create the Administrateur with an existing ID
        administrateur.setId(1L);
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        int databaseSizeBeforeCreate = administrateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdministrateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAdministrateurs() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        // Get all the administrateurList
        restAdministrateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(administrateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)));
    }

    @Test
    @Transactional
    void getAdministrateur() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        // Get the administrateur
        restAdministrateurMockMvc
            .perform(get(ENTITY_API_URL_ID, administrateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(administrateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM));
    }

    @Test
    @Transactional
    void getNonExistingAdministrateur() throws Exception {
        // Get the administrateur
        restAdministrateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAdministrateur() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();

        // Update the administrateur
        Administrateur updatedAdministrateur = administrateurRepository.findById(administrateur.getId()).get();
        // Disconnect from session so that the updates on updatedAdministrateur are not directly saved in db
        em.detach(updatedAdministrateur);
        updatedAdministrateur.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(updatedAdministrateur);

        restAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
        Administrateur testAdministrateur = administrateurList.get(administrateurList.size() - 1);
        assertThat(testAdministrateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdministrateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void putNonExistingAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, administrateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAdministrateurWithPatch() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();

        // Update the administrateur using partial update
        Administrateur partialUpdatedAdministrateur = new Administrateur();
        partialUpdatedAdministrateur.setId(administrateur.getId());

        partialUpdatedAdministrateur.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);

        restAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrateur))
            )
            .andExpect(status().isOk());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
        Administrateur testAdministrateur = administrateurList.get(administrateurList.size() - 1);
        assertThat(testAdministrateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdministrateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void fullUpdateAdministrateurWithPatch() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();

        // Update the administrateur using partial update
        Administrateur partialUpdatedAdministrateur = new Administrateur();
        partialUpdatedAdministrateur.setId(administrateur.getId());

        partialUpdatedAdministrateur.nom(UPDATED_NOM).prenom(UPDATED_PRENOM);

        restAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdministrateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdministrateur))
            )
            .andExpect(status().isOk());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
        Administrateur testAdministrateur = administrateurList.get(administrateurList.size() - 1);
        assertThat(testAdministrateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAdministrateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    void patchNonExistingAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, administrateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdministrateur() throws Exception {
        int databaseSizeBeforeUpdate = administrateurRepository.findAll().size();
        administrateur.setId(count.incrementAndGet());

        // Create the Administrateur
        AdministrateurDTO administrateurDTO = administrateurMapper.toDto(administrateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdministrateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(administrateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Administrateur in the database
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAdministrateur() throws Exception {
        // Initialize the database
        administrateurRepository.saveAndFlush(administrateur);

        int databaseSizeBeforeDelete = administrateurRepository.findAll().size();

        // Delete the administrateur
        restAdministrateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, administrateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Administrateur> administrateurList = administrateurRepository.findAll();
        assertThat(administrateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
