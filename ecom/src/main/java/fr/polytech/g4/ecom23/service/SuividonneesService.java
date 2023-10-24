package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Suividonnees}.
 */
public interface SuividonneesService {
    /**
     * Save a suividonnees.
     *
     * @param suividonneesDTO the entity to save.
     * @return the persisted entity.
     */
    SuividonneesDTO save(SuividonneesDTO suividonneesDTO);

    /**
     * Updates a suividonnees.
     *
     * @param suividonneesDTO the entity to update.
     * @return the persisted entity.
     */
    SuividonneesDTO update(SuividonneesDTO suividonneesDTO);

    /**
     * Partially updates a suividonnees.
     *
     * @param suividonneesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SuividonneesDTO> partialUpdate(SuividonneesDTO suividonneesDTO);

    /**
     * Get all the suividonnees.
     *
     * @return the list of entities.
     */
    List<SuividonneesDTO> findAll();

    /**
     * Get all the suividonnees with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SuividonneesDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" suividonnees.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SuividonneesDTO> findOne(Long id);

    /**
     * Delete the "id" suividonnees.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
