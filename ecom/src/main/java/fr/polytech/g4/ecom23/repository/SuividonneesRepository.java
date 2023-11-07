package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Suividonnees;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Suividonnees entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuividonneesRepository extends JpaRepository<Suividonnees, Long> {}
