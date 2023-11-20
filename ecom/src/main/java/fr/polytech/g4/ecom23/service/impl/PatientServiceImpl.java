package fr.polytech.g4.ecom23.service.impl;

import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.domain.Patient;
import fr.polytech.g4.ecom23.domain.Suividonnees;
import fr.polytech.g4.ecom23.repository.PatientRepository;
import fr.polytech.g4.ecom23.repository.SuividonneesRepository;
import fr.polytech.g4.ecom23.service.PatientService;
import fr.polytech.g4.ecom23.service.SuividonneesService;
import fr.polytech.g4.ecom23.service.dto.PatientDTO;
import fr.polytech.g4.ecom23.service.mapper.PatientMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

/**
 * Service Implementation for managing {@link Patient}.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final SuividonneesRepository sdrepo;

    public PatientServiceImpl(PatientRepository patientRepository, PatientMapper patientMapper, SuividonneesRepository sdrepo) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.sdrepo = sdrepo;
    }

    public void importDataFromCSVForPatient(InputStream inputstream) {
       try {
            List<String[]> csvLines = CSVUtil.readCSV(inputstream);
            Etablissement eta = new Etablissement();
            for (String[] line : csvLines){
                Patient patient = new Patient();
                //patient.setId(Long.valueOf(line[0]));
                patient.setNom(line[0]);
                patient.setPrenom(line[0]);
                patient.setTaille(Float.valueOf(line[1]));
                patient.setAlbumine(Float.valueOf(line[2]));
                patient.setEtablissement(eta);

                patientRepository.save(patient);
            }
       }
       catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public PatientDTO save(PatientDTO patientDTO) {
        log.debug("Request to save Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public PatientDTO update(PatientDTO patientDTO) {
        log.debug("Request to update Patient : {}", patientDTO);
        Patient patient = patientMapper.toEntity(patientDTO);
        patient = patientRepository.save(patient);
        return patientMapper.toDto(patient);
    }

    @Override
    public Optional<PatientDTO> partialUpdate(PatientDTO patientDTO) {
        log.debug("Request to partially update Patient : {}", patientDTO);

        return patientRepository
            .findById(patientDTO.getId())
            .map(existingPatient -> {
                patientMapper.partialUpdate(existingPatient, patientDTO);

                return existingPatient;
            })
            .map(patientRepository::save)
            .map(patientMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PatientDTO> findAll() {
        log.debug("Request to get all Patients");
        return patientRepository.findAll().stream().map(patientMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDTO> findOne(Long id) {
        log.debug("Request to get Patient : {}", id);
        return patientRepository.findById(id).map(patientMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patient : {}", id);
        patientRepository.deleteById(id);
    }
}
    class CSVUtil {

        public static List<String[]> readCSV(InputStream inputStream) throws IOException {
            List<String[]> lines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                line = reader.readLine();
                while ((line = reader.readLine()) != null) {
                    // Remplacez les tabulations par des points-virgules pour normaliser le délimiteur
                    line = line.replace('\t', ';');
                    line = line.replace(',','.');
                    String[] values = line.split(";");
                    lines.add(values);
                }
            } catch (IOException e) {
                System.out.println("error");
                throw new RuntimeException(e);
            }
            return lines;
        }
    }
