package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.service.dto.NotesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Notes}.
 */
public interface NotesService {
    /**
     * Save a notes.
     *
     * @param notesDTO the entity to save.
     * @return the persisted entity.
     */
    NotesDTO save(NotesDTO notesDTO);

    /**
     * Updates a notes.
     *
     * @param notesDTO the entity to update.
     * @return the persisted entity.
     */
    NotesDTO update(NotesDTO notesDTO);

    /**
     * Partially updates a notes.
     *
     * @param notesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NotesDTO> partialUpdate(NotesDTO notesDTO);

    /**
     * Get all the notes.
     *
     * @return the list of entities.
     */
    List<NotesDTO> findAll();

    /**
     * Get the "id" notes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NotesDTO> findOne(Long id);

    /**
     * Delete the "id" notes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
