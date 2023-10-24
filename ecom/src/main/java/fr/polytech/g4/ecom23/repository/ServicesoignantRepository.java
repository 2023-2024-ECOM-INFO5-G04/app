package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Servicesoignant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Servicesoignant entity.
 */
@Repository
public interface ServicesoignantRepository extends JpaRepository<Servicesoignant, Long> {
    default Optional<Servicesoignant> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Servicesoignant> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Servicesoignant> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct servicesoignant from Servicesoignant servicesoignant left join fetch servicesoignant.infrastructure",
        countQuery = "select count(distinct servicesoignant) from Servicesoignant servicesoignant"
    )
    Page<Servicesoignant> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct servicesoignant from Servicesoignant servicesoignant left join fetch servicesoignant.infrastructure")
    List<Servicesoignant> findAllWithToOneRelationships();

    @Query(
        "select servicesoignant from Servicesoignant servicesoignant left join fetch servicesoignant.infrastructure where servicesoignant.id =:id"
    )
    Optional<Servicesoignant> findOneWithToOneRelationships(@Param("id") Long id);
}
