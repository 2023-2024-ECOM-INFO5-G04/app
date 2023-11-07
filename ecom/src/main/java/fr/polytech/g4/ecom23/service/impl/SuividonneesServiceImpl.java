package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Suividonnees;
import fr.polytech.g4.ecom23.repository.SuividonneesRepository;
import fr.polytech.g4.ecom23.service.SuividonneesService;
import fr.polytech.g4.ecom23.service.dto.SuividonneesDTO;
import fr.polytech.g4.ecom23.service.mapper.SuividonneesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Suividonnees}.
 */
@Service
@Transactional
public class SuividonneesServiceImpl implements SuividonneesService {

    private final Logger log = LoggerFactory.getLogger(SuividonneesServiceImpl.class);

    private final SuividonneesRepository suividonneesRepository;

    private final SuividonneesMapper suividonneesMapper;

    public SuividonneesServiceImpl(SuividonneesRepository suividonneesRepository, SuividonneesMapper suividonneesMapper) {
        this.suividonneesRepository = suividonneesRepository;
        this.suividonneesMapper = suividonneesMapper;
    }

    @Override
    public SuividonneesDTO save(SuividonneesDTO suividonneesDTO) {
        log.debug("Request to save Suividonnees : {}", suividonneesDTO);
        Suividonnees suividonnees = suividonneesMapper.toEntity(suividonneesDTO);
        suividonnees = suividonneesRepository.save(suividonnees);
        return suividonneesMapper.toDto(suividonnees);
    }

    @Override
    public SuividonneesDTO update(SuividonneesDTO suividonneesDTO) {
        log.debug("Request to update Suividonnees : {}", suividonneesDTO);
        Suividonnees suividonnees = suividonneesMapper.toEntity(suividonneesDTO);
        suividonnees = suividonneesRepository.save(suividonnees);
        return suividonneesMapper.toDto(suividonnees);
    }

    @Override
    public Optional<SuividonneesDTO> partialUpdate(SuividonneesDTO suividonneesDTO) {
        log.debug("Request to partially update Suividonnees : {}", suividonneesDTO);

        return suividonneesRepository
            .findById(suividonneesDTO.getId())
            .map(existingSuividonnees -> {
                suividonneesMapper.partialUpdate(existingSuividonnees, suividonneesDTO);

                return existingSuividonnees;
            })
            .map(suividonneesRepository::save)
            .map(suividonneesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuividonneesDTO> findAll() {
        log.debug("Request to get all Suividonnees");
        return suividonneesRepository.findAll().stream().map(suividonneesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SuividonneesDTO> findOne(Long id) {
        log.debug("Request to get Suividonnees : {}", id);
        return suividonneesRepository.findById(id).map(suividonneesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Suividonnees : {}", id);
        suividonneesRepository.deleteById(id);
    }
}
