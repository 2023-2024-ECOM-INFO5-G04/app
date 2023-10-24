package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Medecin;
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
public class MedecinRepositoryWithBagRelationshipsImpl implements MedecinRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Medecin> fetchBagRelationships(Optional<Medecin> medecin) {
        return medecin.map(this::fetchPatients).map(this::fetchEtablissements);
    }

    @Override
    public Page<Medecin> fetchBagRelationships(Page<Medecin> medecins) {
        return new PageImpl<>(fetchBagRelationships(medecins.getContent()), medecins.getPageable(), medecins.getTotalElements());
    }

    @Override
    public List<Medecin> fetchBagRelationships(List<Medecin> medecins) {
        return Optional.of(medecins).map(this::fetchPatients).map(this::fetchEtablissements).orElse(Collections.emptyList());
    }

    Medecin fetchPatients(Medecin result) {
        return entityManager
            .createQuery("select medecin from Medecin medecin left join fetch medecin.patients where medecin is :medecin", Medecin.class)
            .setParameter("medecin", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Medecin> fetchPatients(List<Medecin> medecins) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, medecins.size()).forEach(index -> order.put(medecins.get(index).getId(), index));
        List<Medecin> result = entityManager
            .createQuery(
                "select distinct medecin from Medecin medecin left join fetch medecin.patients where medecin in :medecins",
                Medecin.class
            )
            .setParameter("medecins", medecins)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Medecin fetchEtablissements(Medecin result) {
        return entityManager
            .createQuery(
                "select medecin from Medecin medecin left join fetch medecin.etablissements where medecin is :medecin",
                Medecin.class
            )
            .setParameter("medecin", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Medecin> fetchEtablissements(List<Medecin> medecins) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, medecins.size()).forEach(index -> order.put(medecins.get(index).getId(), index));
        List<Medecin> result = entityManager
            .createQuery(
                "select distinct medecin from Medecin medecin left join fetch medecin.etablissements where medecin in :medecins",
                Medecin.class
            )
            .setParameter("medecins", medecins)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
