package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.domain.Rappel;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.dto.RappelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rappel} and its DTO {@link RappelDTO}.
 */
@Mapper(componentModel = "spring")
public interface RappelMapper extends EntityMapper<RappelDTO, Rappel> {
    @Mapping(target = "medecin", source = "medecin")
    RappelDTO toDto(Rappel s);

    @Named("medecinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MedecinDTO toDtoMedecinId(Medecin medecin);
}
