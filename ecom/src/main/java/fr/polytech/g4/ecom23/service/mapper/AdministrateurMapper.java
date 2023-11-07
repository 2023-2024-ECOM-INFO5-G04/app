package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Administrateur;
import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administrateur} and its DTO {@link AdministrateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministrateurMapper extends EntityMapper<AdministrateurDTO, Administrateur> {
    @Mapping(target = "compte", source = "compte", qualifiedByName = "compteId")
    AdministrateurDTO toDto(Administrateur s);

    @Named("compteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteDTO toDtoCompteId(Compte compte);
}
