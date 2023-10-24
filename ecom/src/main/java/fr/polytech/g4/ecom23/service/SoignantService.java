package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Soignant}.
 */
public interface SoignantService {
    /**
     * Save a soignant.
     *
     * @param soignantDTO the entity to save.
     * @return the persisted entity.
     */
    SoignantDTO save(SoignantDTO soignantDTO);

    /**
     * Updates a soignant.
     *
     * @param soignantDTO the entity to update.
     * @return the persisted entity.
     */
    SoignantDTO update(SoignantDTO soignantDTO);

    /**
     * Partially updates a soignant.
     *
     * @param soignantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SoignantDTO> partialUpdate(SoignantDTO soignantDTO);

    /**
     * Get all the soignants.
     *
     * @return the list of entities.
     */
    List<SoignantDTO> findAll();

    /**
     * Get all the soignants with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoignantDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" soignant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoignantDTO> findOne(Long id);

    /**
     * Delete the "id" soignant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
