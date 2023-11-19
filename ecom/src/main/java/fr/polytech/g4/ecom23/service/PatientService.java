package fr.polytech.g4.ecom23.service;

import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.polytech.g4.ecom23.domain.Patient}.
 */
public interface PatientService {
    /**
     * Save a patient.
     *
     * @param patientDTO the entity to save.
     * @return the persisted entity.
     */
    PatientDTO save(PatientDTO patientDTO);

    /**
     * Updates a patient.
     *
     * @param patientDTO the entity to update.
     * @return the persisted entity.
     */
    PatientDTO update(PatientDTO patientDTO);

    /**
     * Partially updates a patient.
     *
     * @param patientDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PatientDTO> partialUpdate(PatientDTO patientDTO);

    /**
     * Get all the patients.
     *
     * @return the list of entities.
     */
    List<PatientDTO> findAll();

    /**
     * Get the "id" patient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientDTO> findOne(Long id);

    /**
     * Delete the "id" patient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    public void importDataFromCSVForPatient(InputStream inputstream);

}
