package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Tache;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tache entity.
 */
@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {
    default Optional<Tache> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Tache> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Tache> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct tache from Tache tache left join fetch tache.patient",
        countQuery = "select count(distinct tache) from Tache tache"
    )
    Page<Tache> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct tache from Tache tache left join fetch tache.patient")
    List<Tache> findAllWithToOneRelationships();

    @Query("select tache from Tache tache left join fetch tache.patient where tache.id =:id")
    Optional<Tache> findOneWithToOneRelationships(@Param("id") Long id);
}
