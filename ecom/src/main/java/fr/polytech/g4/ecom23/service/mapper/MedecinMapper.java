package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.User;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medecin} and its DTO {@link MedecinDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedecinMapper extends EntityMapper<MedecinDTO, Medecin> {
    @Mapping(target = "user", source = "user")
    @Mapping(target = "patients", source = "patients")
    @Mapping(target = "etablissements", source = "etablissements")
    MedecinDTO toDto(Medecin s);

    @Mapping(target = "removePatients", ignore = true)
    @Mapping(target = "removeEtablissement", ignore = true)
    Medecin toEntity(MedecinDTO medecinDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("patientIdSet")
    default Set<PatientDTO> toDtoPatientIdSet(Set<Patient> patient) {
        return patient.stream().map(this::toDtoPatientId).collect(Collectors.toSet());
    }

    @Named("etablissementId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EtablissementDTO toDtoEtablissementId(Etablissement etablissement);

    @Named("etablissementIdSet")
    default Set<EtablissementDTO> toDtoEtablissementIdSet(Set<Etablissement> etablissement) {
        return etablissement.stream().map(this::toDtoEtablissementId).collect(Collectors.toSet());
    }
}
