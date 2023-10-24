package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Notes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Notes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {}
