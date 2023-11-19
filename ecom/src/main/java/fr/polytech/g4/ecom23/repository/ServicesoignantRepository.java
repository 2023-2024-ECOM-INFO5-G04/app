package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Servicesoignant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Servicesoignant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServicesoignantRepository extends JpaRepository<Servicesoignant, Long> {}
