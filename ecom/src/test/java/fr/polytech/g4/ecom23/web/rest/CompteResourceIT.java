package fr.polytech.g4.ecom23.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.polytech.g4.ecom23.IntegrationTest;
import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.domain.enumeration.Role;
import fr.polytech.g4.ecom23.repository.CompteRepository;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import fr.polytech.g4.ecom23.service.mapper.CompteMapper;
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
 * Integration tests for the {@link CompteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompteResourceIT {

    private static final String DEFAULT_NOMUTILISATEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOMUTILISATEUR = "BBBBBBBBBB";

    private static final String DEFAULT_MOTDEPASSE = "AAAAAAAAAA";
    private static final String UPDATED_MOTDEPASSE = "BBBBBBBBBB";

    private static final Role DEFAULT_ROLE = Role.Medecin;
    private static final Role UPDATED_ROLE = Role.Soignant;

    private static final String ENTITY_API_URL = "/api/comptes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private CompteMapper compteMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompteMockMvc;

    private Compte compte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createEntity(EntityManager em) {
        Compte compte = new Compte().nomutilisateur(DEFAULT_NOMUTILISATEUR).motdepasse(DEFAULT_MOTDEPASSE).role(DEFAULT_ROLE);
        return compte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compte createUpdatedEntity(EntityManager em) {
        Compte compte = new Compte().nomutilisateur(UPDATED_NOMUTILISATEUR).motdepasse(UPDATED_MOTDEPASSE).role(UPDATED_ROLE);
        return compte;
    }

    @BeforeEach
    public void initTest() {
        compte = createEntity(em);
    }

    @Test
    @Transactional
    void createCompte() throws Exception {
        int databaseSizeBeforeCreate = compteRepository.findAll().size();
        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);
        restCompteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isCreated());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate + 1);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNomutilisateur()).isEqualTo(DEFAULT_NOMUTILISATEUR);
        assertThat(testCompte.getMotdepasse()).isEqualTo(DEFAULT_MOTDEPASSE);
        assertThat(testCompte.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createCompteWithExistingId() throws Exception {
        // Create the Compte with an existing ID
        compte.setId(1L);
        CompteDTO compteDTO = compteMapper.toDto(compte);

        int databaseSizeBeforeCreate = compteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComptes() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get all the compteList
        restCompteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compte.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomutilisateur").value(hasItem(DEFAULT_NOMUTILISATEUR)))
            .andExpect(jsonPath("$.[*].motdepasse").value(hasItem(DEFAULT_MOTDEPASSE)))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    void getCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        // Get the compte
        restCompteMockMvc
            .perform(get(ENTITY_API_URL_ID, compte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compte.getId().intValue()))
            .andExpect(jsonPath("$.nomutilisateur").value(DEFAULT_NOMUTILISATEUR))
            .andExpect(jsonPath("$.motdepasse").value(DEFAULT_MOTDEPASSE))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCompte() throws Exception {
        // Get the compte
        restCompteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte
        Compte updatedCompte = compteRepository.findById(compte.getId()).get();
        // Disconnect from session so that the updates on updatedCompte are not directly saved in db
        em.detach(updatedCompte);
        updatedCompte.nomutilisateur(UPDATED_NOMUTILISATEUR).motdepasse(UPDATED_MOTDEPASSE).role(UPDATED_ROLE);
        CompteDTO compteDTO = compteMapper.toDto(updatedCompte);

        restCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNomutilisateur()).isEqualTo(UPDATED_NOMUTILISATEUR);
        assertThat(testCompte.getMotdepasse()).isEqualTo(UPDATED_MOTDEPASSE);
        assertThat(testCompte.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, compteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(compteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompteWithPatch() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte using partial update
        Compte partialUpdatedCompte = new Compte();
        partialUpdatedCompte.setId(compte.getId());

        partialUpdatedCompte.nomutilisateur(UPDATED_NOMUTILISATEUR);

        restCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompte))
            )
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNomutilisateur()).isEqualTo(UPDATED_NOMUTILISATEUR);
        assertThat(testCompte.getMotdepasse()).isEqualTo(DEFAULT_MOTDEPASSE);
        assertThat(testCompte.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateCompteWithPatch() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeUpdate = compteRepository.findAll().size();

        // Update the compte using partial update
        Compte partialUpdatedCompte = new Compte();
        partialUpdatedCompte.setId(compte.getId());

        partialUpdatedCompte.nomutilisateur(UPDATED_NOMUTILISATEUR).motdepasse(UPDATED_MOTDEPASSE).role(UPDATED_ROLE);

        restCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompte))
            )
            .andExpect(status().isOk());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
        Compte testCompte = compteList.get(compteList.size() - 1);
        assertThat(testCompte.getNomutilisateur()).isEqualTo(UPDATED_NOMUTILISATEUR);
        assertThat(testCompte.getMotdepasse()).isEqualTo(UPDATED_MOTDEPASSE);
        assertThat(testCompte.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, compteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompte() throws Exception {
        int databaseSizeBeforeUpdate = compteRepository.findAll().size();
        compte.setId(count.incrementAndGet());

        // Create the Compte
        CompteDTO compteDTO = compteMapper.toDto(compte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(compteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Compte in the database
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompte() throws Exception {
        // Initialize the database
        compteRepository.saveAndFlush(compte);

        int databaseSizeBeforeDelete = compteRepository.findAll().size();

        // Delete the compte
        restCompteMockMvc
            .perform(delete(ENTITY_API_URL_ID, compte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compte> compteList = compteRepository.findAll();
        assertThat(compteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
