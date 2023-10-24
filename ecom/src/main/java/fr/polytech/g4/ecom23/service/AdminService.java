package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.AdminDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Admin}.
 */
public interface AdminService {
    /**
     * Save a admin.
     *
     * @param adminDTO the entity to save.
     * @return the persisted entity.
     */
    AdminDTO save(AdminDTO adminDTO);

    /**
     * Updates a admin.
     *
     * @param adminDTO the entity to update.
     * @return the persisted entity.
     */
    AdminDTO update(AdminDTO adminDTO);

    /**
     * Partially updates a admin.
     *
     * @param adminDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AdminDTO> partialUpdate(AdminDTO adminDTO);

    /**
     * Get all the admins.
     *
     * @return the list of entities.
     */
    List<AdminDTO> findAll();

    /**
     * Get the "id" admin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AdminDTO> findOne(Long id);

    /**
     * Delete the "id" admin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
