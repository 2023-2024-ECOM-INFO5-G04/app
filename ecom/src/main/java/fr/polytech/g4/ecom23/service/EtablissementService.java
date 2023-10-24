package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Etablissement}.
 */
public interface EtablissementService {
    /**
     * Save a etablissement.
     *
     * @param etablissementDTO the entity to save.
     * @return the persisted entity.
     */
    EtablissementDTO save(EtablissementDTO etablissementDTO);

    /**
     * Updates a etablissement.
     *
     * @param etablissementDTO the entity to update.
     * @return the persisted entity.
     */
    EtablissementDTO update(EtablissementDTO etablissementDTO);

    /**
     * Partially updates a etablissement.
     *
     * @param etablissementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EtablissementDTO> partialUpdate(EtablissementDTO etablissementDTO);

    /**
     * Get all the etablissements.
     *
     * @return the list of entities.
     */
    List<EtablissementDTO> findAll();

    /**
     * Get the "id" etablissement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EtablissementDTO> findOne(Long id);

    /**
     * Delete the "id" etablissement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
