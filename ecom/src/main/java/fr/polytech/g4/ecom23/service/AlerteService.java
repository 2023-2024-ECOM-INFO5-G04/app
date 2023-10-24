package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Alerte}.
 */
public interface AlerteService {
    /**
     * Save a alerte.
     *
     * @param alerteDTO the entity to save.
     * @return the persisted entity.
     */
    AlerteDTO save(AlerteDTO alerteDTO);

    /**
     * Updates a alerte.
     *
     * @param alerteDTO the entity to update.
     * @return the persisted entity.
     */
    AlerteDTO update(AlerteDTO alerteDTO);

    /**
     * Partially updates a alerte.
     *
     * @param alerteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AlerteDTO> partialUpdate(AlerteDTO alerteDTO);

    /**
     * Get all the alertes.
     *
     * @return the list of entities.
     */
    List<AlerteDTO> findAll();
    /**
     * Get all the AlerteDTO where Patient is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<AlerteDTO> findAllWherePatientIsNull();

    /**
     * Get the "id" alerte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AlerteDTO> findOne(Long id);

    /**
     * Delete the "id" alerte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
