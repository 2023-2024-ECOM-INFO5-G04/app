package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Medecin}.
 */
public interface MedecinService {
    /**
     * Save a medecin.
     *
     * @param medecinDTO the entity to save.
     * @return the persisted entity.
     */
    MedecinDTO save(MedecinDTO medecinDTO);

    /**
     * Updates a medecin.
     *
     * @param medecinDTO the entity to update.
     * @return the persisted entity.
     */
    MedecinDTO update(MedecinDTO medecinDTO);

    /**
     * Partially updates a medecin.
     *
     * @param medecinDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MedecinDTO> partialUpdate(MedecinDTO medecinDTO);

    /**
     * Get all the medecins.
     *
     * @return the list of entities.
     */
    List<MedecinDTO> findAll();

    /**
     * Get all the medecins with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedecinDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" medecin.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedecinDTO> findOne(Long id);

    /**
     * Delete the "id" medecin.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
