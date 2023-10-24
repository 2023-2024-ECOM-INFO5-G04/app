package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Medecin;
import fr.polytech.g4.ecom23.repository.MedecinRepository;
import fr.polytech.g4.ecom23.service.MedecinService;
import fr.polytech.g4.ecom23.service.dto.MedecinDTO;
import fr.polytech.g4.ecom23.service.mapper.MedecinMapper;
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
 * Service Implementation for managing {@link Medecin}.
 */
@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

    private final Logger log = LoggerFactory.getLogger(MedecinServiceImpl.class);

    private final MedecinRepository medecinRepository;

    private final MedecinMapper medecinMapper;

    public MedecinServiceImpl(MedecinRepository medecinRepository, MedecinMapper medecinMapper) {
        this.medecinRepository = medecinRepository;
        this.medecinMapper = medecinMapper;
    }

    @Override
    public MedecinDTO save(MedecinDTO medecinDTO) {
        log.debug("Request to save Medecin : {}", medecinDTO);
        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        medecin = medecinRepository.save(medecin);
        return medecinMapper.toDto(medecin);
    }

    @Override
    public MedecinDTO update(MedecinDTO medecinDTO) {
        log.debug("Request to update Medecin : {}", medecinDTO);
        Medecin medecin = medecinMapper.toEntity(medecinDTO);
        medecin = medecinRepository.save(medecin);
        return medecinMapper.toDto(medecin);
    }

    @Override
    public Optional<MedecinDTO> partialUpdate(MedecinDTO medecinDTO) {
        log.debug("Request to partially update Medecin : {}", medecinDTO);

        return medecinRepository
            .findById(medecinDTO.getId())
            .map(existingMedecin -> {
                medecinMapper.partialUpdate(existingMedecin, medecinDTO);

                return existingMedecin;
            })
            .map(medecinRepository::save)
            .map(medecinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedecinDTO> findAll() {
        log.debug("Request to get all Medecins");
        return medecinRepository.findAll().stream().map(medecinMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<MedecinDTO> findAllWithEagerRelationships(Pageable pageable) {
        return medecinRepository.findAllWithEagerRelationships(pageable).map(medecinMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MedecinDTO> findOne(Long id) {
        log.debug("Request to get Medecin : {}", id);
        return medecinRepository.findOneWithEagerRelationships(id).map(medecinMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Medecin : {}", id);
        medecinRepository.deleteById(id);
    }
}
