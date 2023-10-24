package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Compte;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Compte entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {}
