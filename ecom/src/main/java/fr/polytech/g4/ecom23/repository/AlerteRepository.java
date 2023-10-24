package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Alerte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Alerte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {}
