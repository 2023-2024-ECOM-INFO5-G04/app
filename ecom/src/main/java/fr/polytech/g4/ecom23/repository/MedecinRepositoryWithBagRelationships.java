package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Medecin;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface MedecinRepositoryWithBagRelationships {
    Optional<Medecin> fetchBagRelationships(Optional<Medecin> medecin);

    List<Medecin> fetchBagRelationships(List<Medecin> medecins);

    Page<Medecin> fetchBagRelationships(Page<Medecin> medecins);
}
