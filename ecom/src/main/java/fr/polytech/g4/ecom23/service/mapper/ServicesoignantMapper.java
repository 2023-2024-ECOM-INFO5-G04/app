package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Servicesoignant} and its DTO {@link ServicesoignantDTO}.
 */
@Mapper(componentModel = "spring")
public interface ServicesoignantMapper extends EntityMapper<ServicesoignantDTO, Servicesoignant> {
    @Mapping(target = "infrastructure", source = "infrastructure", qualifiedByName = "etablissementIdE")
    ServicesoignantDTO toDto(Servicesoignant s);

    @Named("etablissementIdE")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idE", source = "idE")
    EtablissementDTO toDtoEtablissementIdE(Etablissement etablissement);
}
