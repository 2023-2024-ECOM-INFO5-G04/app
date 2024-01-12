package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Alerte;
import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring")
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Mapping(target = "alerte", source = "alerte")
    @Mapping(target = "etablissement", source = "etablissement")
    PatientDTO toDto(Patient s);

    @Named("alerteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AlerteDTO toDtoAlerteId(Alerte alerte);

    @Named("etablissementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EtablissementDTO toDtoEtablissementId(Etablissement etablissement);
}
