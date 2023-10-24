package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.RappelDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Rappel}.
 */
public interface RappelService {
    /**
     * Save a rappel.
     *
     * @param rappelDTO the entity to save.
     * @return the persisted entity.
     */
    RappelDTO save(RappelDTO rappelDTO);

    /**
     * Updates a rappel.
     *
     * @param rappelDTO the entity to update.
     * @return the persisted entity.
     */
    RappelDTO update(RappelDTO rappelDTO);

    /**
     * Partially updates a rappel.
     *
     * @param rappelDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RappelDTO> partialUpdate(RappelDTO rappelDTO);

    /**
     * Get all the rappels.
     *
     * @return the list of entities.
     */
    List<RappelDTO> findAll();

    /**
     * Get the "id" rappel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RappelDTO> findOne(Long id);

    /**
     * Delete the "id" rappel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
