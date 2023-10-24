package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Alerte;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Alerte} and its DTO {@link AlerteDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlerteMapper extends EntityMapper<AlerteDTO, Alerte> {}
