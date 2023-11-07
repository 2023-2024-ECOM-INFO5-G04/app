package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Administrateur}.
 */
public interface AdministrateurService {
    /**
     * Save a administrateur.
     *
     * @param administrateurDTO the entity to save.
     * @return the persisted entity.
     */
    AdministrateurDTO save(AdministrateurDTO administrateurDTO);

    /**
     * Updates a administrateur.
     *
     * @param administrateurDTO the entity to update.
     * @return the persisted entity.
     */
    AdministrateurDTO update(AdministrateurDTO administrateurDTO);

    /**
     * Partially updates a administrateur.
     *
     * @param administrateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdministrateurDTO> partialUpdate(AdministrateurDTO administrateurDTO);

    /**
     * Get all the administrateurs.
     *
     * @return the list of entities.
     */
    List<AdministrateurDTO> findAll();

    /**
     * Get the "id" administrateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdministrateurDTO> findOne(Long id);

    /**
     * Delete the "id" administrateur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
