package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Soignant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Soignant entity.
 *
 * When extending this class, extend SoignantRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface SoignantRepository extends SoignantRepositoryWithBagRelationships, JpaRepository<Soignant, Long> {
    default Optional<Soignant> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Soignant> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Soignant> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct soignant from Soignant soignant left join fetch soignant.servicesoignant",
        countQuery = "select count(distinct soignant) from Soignant soignant"
    )
    Page<Soignant> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct soignant from Soignant soignant left join fetch soignant.servicesoignant")
    List<Soignant> findAllWithToOneRelationships();

    @Query("select soignant from Soignant soignant left join fetch soignant.servicesoignant where soignant.id =:id")
    Optional<Soignant> findOneWithToOneRelationships(@Param("id") Long id);
}
