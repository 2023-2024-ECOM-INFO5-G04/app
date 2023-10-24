package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Etablissement} and its DTO {@link EtablissementDTO}.
 */
@Mapper(componentModel = "spring")
public interface EtablissementMapper extends EntityMapper<EtablissementDTO, Etablissement> {}
