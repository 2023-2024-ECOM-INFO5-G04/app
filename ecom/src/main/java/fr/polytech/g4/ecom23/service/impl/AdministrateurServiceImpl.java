package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Administrateur;
import fr.polytech.g4.ecom23.repository.AdministrateurRepository;
import fr.polytech.g4.ecom23.service.AdministrateurService;
import fr.polytech.g4.ecom23.service.dto.AdministrateurDTO;
import fr.polytech.g4.ecom23.service.mapper.AdministrateurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Administrateur}.
 */
@Service
@Transactional
public class AdministrateurServiceImpl implements AdministrateurService {

    private final Logger log = LoggerFactory.getLogger(AdministrateurServiceImpl.class);

    private final AdministrateurRepository administrateurRepository;

    private final AdministrateurMapper administrateurMapper;

    public AdministrateurServiceImpl(AdministrateurRepository administrateurRepository, AdministrateurMapper administrateurMapper) {
        this.administrateurRepository = administrateurRepository;
        this.administrateurMapper = administrateurMapper;
    }

    @Override
    public AdministrateurDTO save(AdministrateurDTO administrateurDTO) {
        log.debug("Request to save Administrateur : {}", administrateurDTO);
        Administrateur administrateur = administrateurMapper.toEntity(administrateurDTO);
        administrateur = administrateurRepository.save(administrateur);
        return administrateurMapper.toDto(administrateur);
    }

    @Override
    public AdministrateurDTO update(AdministrateurDTO administrateurDTO) {
        log.debug("Request to update Administrateur : {}", administrateurDTO);
        Administrateur administrateur = administrateurMapper.toEntity(administrateurDTO);
        administrateur = administrateurRepository.save(administrateur);
        return administrateurMapper.toDto(administrateur);
    }

    @Override
    public Optional<AdministrateurDTO> partialUpdate(AdministrateurDTO administrateurDTO) {
        log.debug("Request to partially update Administrateur : {}", administrateurDTO);

        return administrateurRepository
            .findById(administrateurDTO.getId())
            .map(existingAdministrateur -> {
                administrateurMapper.partialUpdate(existingAdministrateur, administrateurDTO);

                return existingAdministrateur;
            })
            .map(administrateurRepository::save)
            .map(administrateurMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdministrateurDTO> findAll() {
        log.debug("Request to get all Administrateurs");
        return administrateurRepository
            .findAll()
            .stream()
            .map(administrateurMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdministrateurDTO> findOne(Long id) {
        log.debug("Request to get Administrateur : {}", id);
        return administrateurRepository.findById(id).map(administrateurMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Administrateur : {}", id);
        administrateurRepository.deleteById(id);
    }
}
