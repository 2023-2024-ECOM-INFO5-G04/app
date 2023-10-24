package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Soignant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class SoignantRepositoryWithBagRelationshipsImpl implements SoignantRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Soignant> fetchBagRelationships(Optional<Soignant> soignant) {
        return soignant.map(this::fetchPatients);
    }

    @Override
    public Page<Soignant> fetchBagRelationships(Page<Soignant> soignants) {
        return new PageImpl<>(fetchBagRelationships(soignants.getContent()), soignants.getPageable(), soignants.getTotalElements());
    }

    @Override
    public List<Soignant> fetchBagRelationships(List<Soignant> soignants) {
        return Optional.of(soignants).map(this::fetchPatients).orElse(Collections.emptyList());
    }

    Soignant fetchPatients(Soignant result) {
        return entityManager
            .createQuery(
                "select soignant from Soignant soignant left join fetch soignant.patients where soignant is :soignant",
                Soignant.class
            )
            .setParameter("soignant", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Soignant> fetchPatients(List<Soignant> soignants) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, soignants.size()).forEach(index -> order.put(soignants.get(index).getId(), index));
        List<Soignant> result = entityManager
            .createQuery(
                "select distinct soignant from Soignant soignant left join fetch soignant.patients where soignant in :soignants",
                Soignant.class
            )
            .setParameter("soignants", soignants)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
