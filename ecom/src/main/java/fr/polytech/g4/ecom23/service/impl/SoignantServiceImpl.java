package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Soignant;
import fr.polytech.g4.ecom23.repository.SoignantRepository;
import fr.polytech.g4.ecom23.service.SoignantService;
import fr.polytech.g4.ecom23.service.dto.SoignantDTO;
import fr.polytech.g4.ecom23.service.mapper.SoignantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Soignant}.
 */
@Service
@Transactional
public class SoignantServiceImpl implements SoignantService {

    private final Logger log = LoggerFactory.getLogger(SoignantServiceImpl.class);

    private final SoignantRepository soignantRepository;

    private final SoignantMapper soignantMapper;

    public SoignantServiceImpl(SoignantRepository soignantRepository, SoignantMapper soignantMapper) {
        this.soignantRepository = soignantRepository;
        this.soignantMapper = soignantMapper;
    }

    @Override
    public SoignantDTO save(SoignantDTO soignantDTO) {
        log.debug("Request to save Soignant : {}", soignantDTO);
        Soignant soignant = soignantMapper.toEntity(soignantDTO);
        soignant = soignantRepository.save(soignant);
        return soignantMapper.toDto(soignant);
    }

    @Override
    public SoignantDTO update(SoignantDTO soignantDTO) {
        log.debug("Request to update Soignant : {}", soignantDTO);
        Soignant soignant = soignantMapper.toEntity(soignantDTO);
        soignant = soignantRepository.save(soignant);
        return soignantMapper.toDto(soignant);
    }

    @Override
    public Optional<SoignantDTO> partialUpdate(SoignantDTO soignantDTO) {
        log.debug("Request to partially update Soignant : {}", soignantDTO);

        return soignantRepository
            .findById(soignantDTO.getId())
            .map(existingSoignant -> {
                soignantMapper.partialUpdate(existingSoignant, soignantDTO);

                return existingSoignant;
            })
            .map(soignantRepository::save)
            .map(soignantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SoignantDTO> findAll() {
        log.debug("Request to get all Soignants");
        return soignantRepository.findAll().stream().map(soignantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<SoignantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return soignantRepository.findAllWithEagerRelationships(pageable).map(soignantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoignantDTO> findOne(Long id) {
        log.debug("Request to get Soignant : {}", id);
        return soignantRepository.findOneWithEagerRelationships(id).map(soignantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Soignant : {}", id);
        soignantRepository.deleteById(id);
    }
}
