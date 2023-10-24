package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Alerte;
import fr.polytech.g4.ecom23.repository.AlerteRepository;
import fr.polytech.g4.ecom23.service.AlerteService;
import fr.polytech.g4.ecom23.service.dto.AlerteDTO;
import fr.polytech.g4.ecom23.service.mapper.AlerteMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Alerte}.
 */
@Service
@Transactional
public class AlerteServiceImpl implements AlerteService {

    private final Logger log = LoggerFactory.getLogger(AlerteServiceImpl.class);

    private final AlerteRepository alerteRepository;

    private final AlerteMapper alerteMapper;

    public AlerteServiceImpl(AlerteRepository alerteRepository, AlerteMapper alerteMapper) {
        this.alerteRepository = alerteRepository;
        this.alerteMapper = alerteMapper;
    }

    @Override
    public AlerteDTO save(AlerteDTO alerteDTO) {
        log.debug("Request to save Alerte : {}", alerteDTO);
        Alerte alerte = alerteMapper.toEntity(alerteDTO);
        alerte = alerteRepository.save(alerte);
        return alerteMapper.toDto(alerte);
    }

    @Override
    public AlerteDTO update(AlerteDTO alerteDTO) {
        log.debug("Request to update Alerte : {}", alerteDTO);
        Alerte alerte = alerteMapper.toEntity(alerteDTO);
        alerte = alerteRepository.save(alerte);
        return alerteMapper.toDto(alerte);
    }

    @Override
    public Optional<AlerteDTO> partialUpdate(AlerteDTO alerteDTO) {
        log.debug("Request to partially update Alerte : {}", alerteDTO);

        return alerteRepository
            .findById(alerteDTO.getId())
            .map(existingAlerte -> {
                alerteMapper.partialUpdate(existingAlerte, alerteDTO);

                return existingAlerte;
            })
            .map(alerteRepository::save)
            .map(alerteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlerteDTO> findAll() {
        log.debug("Request to get all Alertes");
        return alerteRepository.findAll().stream().map(alerteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the alertes where Patient is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AlerteDTO> findAllWherePatientIsNull() {
        log.debug("Request to get all alertes where Patient is null");
        return StreamSupport
            .stream(alerteRepository.findAll().spliterator(), false)
            .filter(alerte -> alerte.getPatient() == null)
            .map(alerteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlerteDTO> findOne(Long id) {
        log.debug("Request to get Alerte : {}", id);
        return alerteRepository.findById(id).map(alerteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Alerte : {}", id);
        alerteRepository.deleteById(id);
    }
}
