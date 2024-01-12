package fr.polytech.g4.ecom23.service.mapper;

import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.domain.Notes;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.dto.NotesDTO;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notes} and its DTO {@link NotesDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotesMapper extends EntityMapper<NotesDTO, Notes> {
    @Mapping(target = "medecin", source = "medecin")
    @Mapping(target = "patient", source = "patient")
    NotesDTO toDto(Notes s);

    @Named("medecinId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MedecinDTO toDtoMedecinId(Medecin medecin);

    @Named("patientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoPatientId(Patient patient);
}
