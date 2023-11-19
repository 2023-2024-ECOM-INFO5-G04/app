package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Tache;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {}
