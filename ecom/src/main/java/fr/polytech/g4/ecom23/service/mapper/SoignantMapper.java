package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.domain.Soignant;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Soignant} and its DTO {@link SoignantDTO}.
 */
@Mapper(componentModel = "spring")
public interface SoignantMapper extends EntityMapper<SoignantDTO, Soignant> {
    @Mapping(target = "compte", source = "compte", qualifiedByName = "compteId")
    @Mapping(target = "servicesoignant", source = "servicesoignant", qualifiedByName = "servicesoignantId")
    @Mapping(target = "patients", source = "patients", qualifiedByName = "patientIdSet")
    SoignantDTO toDto(Soignant s);

    @Mapping(target = "removePatients", ignore = true)
    Soignant toEntity(SoignantDTO soignantDTO);

    @Named("compteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompteDTO toDtoCompteId(Compte compte);

    @Named("servicesoignantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServicesoignantDTO toDtoServicesoignantId(Servicesoignant servicesoignant);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("patientIdSet")
    default Set<PatientDTO> toDtoPatientIdSet(Set<Patient> patient) {
        return patient.stream().map(this::toDtoPatientId).collect(Collectors.toSet());
    }
}
