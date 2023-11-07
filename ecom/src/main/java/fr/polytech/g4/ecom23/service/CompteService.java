package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Compte}.
 */
public interface CompteService {
    /**
     * Save a compte.
     *
     * @param compteDTO the entity to save.
     * @return the persisted entity.
     */
    CompteDTO save(CompteDTO compteDTO);

    /**
     * Updates a compte.
     *
     * @param compteDTO the entity to update.
     * @return the persisted entity.
     */
    CompteDTO update(CompteDTO compteDTO);

    /**
     * Partially updates a compte.
     *
     * @param compteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompteDTO> partialUpdate(CompteDTO compteDTO);

    /**
     * Get all the comptes.
     *
     * @return the list of entities.
     */
    List<CompteDTO> findAll();
    /**
     * Get all the CompteDTO where Soignant is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CompteDTO> findAllWhereSoignantIsNull();
    /**
     * Get all the CompteDTO where Medecin is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CompteDTO> findAllWhereMedecinIsNull();
    /**
     * Get all the CompteDTO where Administrateur is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CompteDTO> findAllWhereAdministrateurIsNull();

    /**
     * Get the "id" compte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompteDTO> findOne(Long id);

    /**
     * Delete the "id" compte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
