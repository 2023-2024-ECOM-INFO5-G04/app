package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Rappel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rappel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RappelRepository extends JpaRepository<Rappel, Long> {}
