package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Administrateur;
import fr.polytech.g4.ecom23.domain.User;
import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
import fr.polytech.g4.ecom23.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Administrateur} and its DTO {@link AdministrateurDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdministrateurMapper extends EntityMapper<AdministrateurDTO, Administrateur> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    AdministrateurDTO toDto(Administrateur s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
