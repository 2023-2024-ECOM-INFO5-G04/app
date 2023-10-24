package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Compte} and its DTO {@link CompteDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompteMapper extends EntityMapper<CompteDTO, Compte> {}
