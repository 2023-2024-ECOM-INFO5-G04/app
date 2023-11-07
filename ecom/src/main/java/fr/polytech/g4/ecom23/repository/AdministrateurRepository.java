package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Administrateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Administrateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdministrateurRepository extends JpaRepository<Administrateur, Long> {}
