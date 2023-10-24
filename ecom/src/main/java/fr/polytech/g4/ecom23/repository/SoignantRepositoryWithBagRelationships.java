package fr.polytech.g4.ecom23.repository;

import fr.polytech.g4.ecom23.domain.Soignant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SoignantRepositoryWithBagRelationships {
    Optional<Soignant> fetchBagRelationships(Optional<Soignant> soignant);

    List<Soignant> fetchBagRelationships(List<Soignant> soignants);

    Page<Soignant> fetchBagRelationships(Page<Soignant> soignants);
}
