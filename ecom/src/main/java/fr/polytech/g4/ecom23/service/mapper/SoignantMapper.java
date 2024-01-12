package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.domain.Soignant;
import fr.polytech.g4.ecom23.domain.User;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import fr.polytech.g4.ecom23.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Soignant} and its DTO {@link SoignantDTO}.
 */
@Mapper(componentModel = "spring")
public interface SoignantMapper extends EntityMapper<SoignantDTO, Soignant> {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "servicesoignant", source = "servicesoignant")
    @Mapping(target = "patients", source = "patients")
    SoignantDTO toDto(Soignant s);

    @Mapping(target = "removePatients", ignore = true)
    Soignant toEntity(SoignantDTO soignantDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

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
