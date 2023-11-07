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
    @Mapping(target = "etablissement", source = "etablissement", qualifiedByName = "etablissementId")
    ServicesoignantDTO toDto(Servicesoignant s);

    @Named("etablissementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EtablissementDTO toDtoEtablissementId(Etablissement etablissement);
}
