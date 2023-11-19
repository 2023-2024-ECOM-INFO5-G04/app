package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.domain.Soignant;
import fr.polytech.g4.ecom23.domain.Tache;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import fr.polytech.g4.ecom23.service.dto.TacheDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tache} and its DTO {@link TacheDTO}.
 */
@Mapper(componentModel = "spring")
public interface TacheMapper extends EntityMapper<TacheDTO, Tache> {
    @Mapping(target = "patient", source = "patient", qualifiedByName = "patientId")
    @Mapping(target = "servicesoignant", source = "servicesoignant", qualifiedByName = "servicesoignantId")
    @Mapping(target = "soigant", source = "soigant", qualifiedByName = "soignantId")
    @Mapping(target = "medecin", source = "medecin", qualifiedByName = "medecinId")
    TacheDTO toDto(Tache s);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);

    @Named("servicesoignantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ServicesoignantDTO toDtoServicesoignantId(Servicesoignant servicesoignant);

    @Named("soignantId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SoignantDTO toDtoSoignantId(Soignant soignant);

    @Named("medecinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MedecinDTO toDtoMedecinId(Medecin medecin);
}
