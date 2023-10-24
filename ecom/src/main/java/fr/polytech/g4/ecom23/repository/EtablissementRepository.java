package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Etablissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Etablissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {}
