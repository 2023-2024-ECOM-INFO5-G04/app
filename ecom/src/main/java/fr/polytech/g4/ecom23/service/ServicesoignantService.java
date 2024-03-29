package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Servicesoignant}.
 */
public interface ServicesoignantService {
    /**
     * Save a servicesoignant.
     *
     * @param servicesoignantDTO the entity to save.
     * @return the persisted entity.
     */
    ServicesoignantDTO save(ServicesoignantDTO servicesoignantDTO);

    /**
     * Updates a servicesoignant.
     *
     * @param servicesoignantDTO the entity to update.
     * @return the persisted entity.
     */
    ServicesoignantDTO update(ServicesoignantDTO servicesoignantDTO);

    /**
     * Partially updates a servicesoignant.
     *
     * @param servicesoignantDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ServicesoignantDTO> partialUpdate(ServicesoignantDTO servicesoignantDTO);

    /**
     * Get all the servicesoignants.
     *
     * @return the list of entities.
     */
    List<ServicesoignantDTO> findAll();

    /**
     * Get the "id" servicesoignant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServicesoignantDTO> findOne(Long id);

    /**
     * Delete the "id" servicesoignant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
