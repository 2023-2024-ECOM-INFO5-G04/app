package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Suividonnees;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Suividonnees entity.
 */
@Repository
public interface SuividonneesRepository extends JpaRepository<Suividonnees, Long> {
    default Optional<Suividonnees> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Suividonnees> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Suividonnees> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct suividonnees from Suividonnees suividonnees left join fetch suividonnees.patient",
        countQuery = "select count(distinct suividonnees) from Suividonnees suividonnees"
    )
    Page<Suividonnees> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct suividonnees from Suividonnees suividonnees left join fetch suividonnees.patient")
    List<Suividonnees> findAllWithToOneRelationships();

    @Query("select suividonnees from Suividonnees suividonnees left join fetch suividonnees.patient where suividonnees.id =:id")
    Optional<Suividonnees> findOneWithToOneRelationships(@Param("id") Long id);
}
