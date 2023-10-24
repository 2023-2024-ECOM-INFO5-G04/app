package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patient entity.
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    default Optional<Patient> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Patient> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Patient> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct patient from Patient patient left join fetch patient.infrastructure",
        countQuery = "select count(distinct patient) from Patient patient"
    )
    Page<Patient> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct patient from Patient patient left join fetch patient.infrastructure")
    List<Patient> findAllWithToOneRelationships();

    @Query("select patient from Patient patient left join fetch patient.infrastructure where patient.id =:id")
    Optional<Patient> findOneWithToOneRelationships(@Param("id") Long id);
}
