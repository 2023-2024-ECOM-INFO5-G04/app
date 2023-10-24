package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Suividonnees;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Suividonnees} and its DTO {@link SuividonneesDTO}.
 */
@Mapper(componentModel = "spring")
public interface SuividonneesMapper extends EntityMapper<SuividonneesDTO, Suividonnees> {
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientIdP")
    SuividonneesDTO toDto(Suividonnees s);

    @Named("patientIdP")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idP", source = "idP")
    PatientDTO toDtoPatientIdP(Patient patient);
}
