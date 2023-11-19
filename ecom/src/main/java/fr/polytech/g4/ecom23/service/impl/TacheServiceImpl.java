package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Tache;
import fr.polytech.g4.ecom23.repository.TacheRepository;
import fr.polytech.g4.ecom23.service.TacheService;
import fr.polytech.g4.ecom23.service.dto.TacheDTO;
import fr.polytech.g4.ecom23.service.mapper.TacheMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tache}.
 */
@Service
@Transactional
public class TacheServiceImpl implements TacheService {

    private final Logger log = LoggerFactory.getLogger(TacheServiceImpl.class);

    private final TacheRepository tacheRepository;

    private final TacheMapper tacheMapper;

    public TacheServiceImpl(TacheRepository tacheRepository, TacheMapper tacheMapper) {
        this.tacheRepository = tacheRepository;
        this.tacheMapper = tacheMapper;
    }

    @Override
    public TacheDTO save(TacheDTO tacheDTO) {
        log.debug("Request to save Tache : {}", tacheDTO);
        Tache tache = tacheMapper.toEntity(tacheDTO);
        tache = tacheRepository.save(tache);
        return tacheMapper.toDto(tache);
    }

    @Override
    public TacheDTO update(TacheDTO tacheDTO) {
        log.debug("Request to update Tache : {}", tacheDTO);
        Tache tache = tacheMapper.toEntity(tacheDTO);
        tache = tacheRepository.save(tache);
        return tacheMapper.toDto(tache);
    }

    @Override
    public Optional<TacheDTO> partialUpdate(TacheDTO tacheDTO) {
        log.debug("Request to partially update Tache : {}", tacheDTO);

        return tacheRepository
            .findById(tacheDTO.getId())
            .map(existingTache -> {
                tacheMapper.partialUpdate(existingTache, tacheDTO);

                return existingTache;
            })
            .map(tacheRepository::save)
            .map(tacheMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TacheDTO> findAll() {
        log.debug("Request to get all Taches");
        return tacheRepository.findAll().stream().map(tacheMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TacheDTO> findOne(Long id) {
        log.debug("Request to get Tache : {}", id);
        return tacheRepository.findById(id).map(tacheMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tache : {}", id);
        tacheRepository.deleteById(id);
    }
}
