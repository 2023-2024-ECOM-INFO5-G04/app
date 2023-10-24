package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Admin;
import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.service.dto.AdminDTO;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admin} and its DTO {@link AdminDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminMapper extends EntityMapper<AdminDTO, Admin> {
    @Mapping(target = "compte", source = "compte", qualifiedByName = "compteId")
    AdminDTO toDto(Admin s);

    @Named("compteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteDTO toDtoCompteId(Compte compte);
}
