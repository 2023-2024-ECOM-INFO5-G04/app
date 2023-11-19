package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.TacheDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Tache}.
 */
public interface TacheService {
    /**
     * Save a tache.
     *
     * @param tacheDTO the entity to save.
     * @return the persisted entity.
     */
    TacheDTO save(TacheDTO tacheDTO);

    /**
     * Updates a tache.
     *
     * @param tacheDTO the entity to update.
     * @return the persisted entity.
     */
    TacheDTO update(TacheDTO tacheDTO);

    /**
     * Partially updates a tache.
     *
     * @param tacheDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TacheDTO> partialUpdate(TacheDTO tacheDTO);

    /**
     * Get all the taches.
     *
     * @return the list of entities.
     */
    List<TacheDTO> findAll();

    /**
     * Get the "id" tache.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TacheDTO> findOne(Long id);

    /**
     * Delete the "id" tache.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
