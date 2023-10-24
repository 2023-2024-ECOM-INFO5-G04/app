package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Rappel;
import fr.polytech.g4.ecom23.repository.RappelRepository;
import fr.polytech.g4.ecom23.service.RappelService;
import fr.polytech.g4.ecom23.service.dto.RappelDTO;
import fr.polytech.g4.ecom23.service.mapper.RappelMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rappel}.
 */
@Service
@Transactional
public class RappelServiceImpl implements RappelService {

    private final Logger log = LoggerFactory.getLogger(RappelServiceImpl.class);

    private final RappelRepository rappelRepository;

    private final RappelMapper rappelMapper;

    public RappelServiceImpl(RappelRepository rappelRepository, RappelMapper rappelMapper) {
        this.rappelRepository = rappelRepository;
        this.rappelMapper = rappelMapper;
    }

    @Override
    public RappelDTO save(RappelDTO rappelDTO) {
        log.debug("Request to save Rappel : {}", rappelDTO);
        Rappel rappel = rappelMapper.toEntity(rappelDTO);
        rappel = rappelRepository.save(rappel);
        return rappelMapper.toDto(rappel);
    }

    @Override
    public RappelDTO update(RappelDTO rappelDTO) {
        log.debug("Request to update Rappel : {}", rappelDTO);
        Rappel rappel = rappelMapper.toEntity(rappelDTO);
        rappel = rappelRepository.save(rappel);
        return rappelMapper.toDto(rappel);
    }

    @Override
    public Optional<RappelDTO> partialUpdate(RappelDTO rappelDTO) {
        log.debug("Request to partially update Rappel : {}", rappelDTO);

        return rappelRepository
            .findById(rappelDTO.getId())
            .map(existingRappel -> {
                rappelMapper.partialUpdate(existingRappel, rappelDTO);

                return existingRappel;
            })
            .map(rappelRepository::save)
            .map(rappelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RappelDTO> findAll() {
        log.debug("Request to get all Rappels");
        return rappelRepository.findAll().stream().map(rappelMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RappelDTO> findOne(Long id) {
        log.debug("Request to get Rappel : {}", id);
        return rappelRepository.findById(id).map(rappelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rappel : {}", id);
        rappelRepository.deleteById(id);
    }
}
