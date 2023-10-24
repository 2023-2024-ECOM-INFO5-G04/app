package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Etablissement;
import fr.polytech.g4.ecom23.repository.EtablissementRepository;
import fr.polytech.g4.ecom23.service.EtablissementService;
import fr.polytech.g4.ecom23.service.dto.EtablissementDTO;
import fr.polytech.g4.ecom23.service.mapper.EtablissementMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Etablissement}.
 */
@Service
@Transactional
public class EtablissementServiceImpl implements EtablissementService {

    private final Logger log = LoggerFactory.getLogger(EtablissementServiceImpl.class);

    private final EtablissementRepository etablissementRepository;

    private final EtablissementMapper etablissementMapper;

    public EtablissementServiceImpl(EtablissementRepository etablissementRepository, EtablissementMapper etablissementMapper) {
        this.etablissementRepository = etablissementRepository;
        this.etablissementMapper = etablissementMapper;
    }

    @Override
    public EtablissementDTO save(EtablissementDTO etablissementDTO) {
        log.debug("Request to save Etablissement : {}", etablissementDTO);
        Etablissement etablissement = etablissementMapper.toEntity(etablissementDTO);
        etablissement = etablissementRepository.save(etablissement);
        return etablissementMapper.toDto(etablissement);
    }

    @Override
    public EtablissementDTO update(EtablissementDTO etablissementDTO) {
        log.debug("Request to update Etablissement : {}", etablissementDTO);
        Etablissement etablissement = etablissementMapper.toEntity(etablissementDTO);
        etablissement = etablissementRepository.save(etablissement);
        return etablissementMapper.toDto(etablissement);
    }

    @Override
    public Optional<EtablissementDTO> partialUpdate(EtablissementDTO etablissementDTO) {
        log.debug("Request to partially update Etablissement : {}", etablissementDTO);

        return etablissementRepository
            .findById(etablissementDTO.getId())
            .map(existingEtablissement -> {
                etablissementMapper.partialUpdate(existingEtablissement, etablissementDTO);

                return existingEtablissement;
            })
            .map(etablissementRepository::save)
            .map(etablissementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EtablissementDTO> findAll() {
        log.debug("Request to get all Etablissements");
        return etablissementRepository.findAll().stream().map(etablissementMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EtablissementDTO> findOne(Long id) {
        log.debug("Request to get Etablissement : {}", id);
        return etablissementRepository.findById(id).map(etablissementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etablissement : {}", id);
        etablissementRepository.deleteById(id);
    }
}
