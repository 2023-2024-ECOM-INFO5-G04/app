package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Compte;
import fr.polytech.g4.ecom23.repository.CompteRepository;
import fr.polytech.g4.ecom23.service.CompteService;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import fr.polytech.g4.ecom23.service.mapper.CompteMapper;
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
 * Service Implementation for managing {@link Compte}.
 */
@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final Logger log = LoggerFactory.getLogger(CompteServiceImpl.class);

    private final CompteRepository compteRepository;

    private final CompteMapper compteMapper;

    public CompteServiceImpl(CompteRepository compteRepository, CompteMapper compteMapper) {
        this.compteRepository = compteRepository;
        this.compteMapper = compteMapper;
    }

    @Override
    public CompteDTO save(CompteDTO compteDTO) {
        log.debug("Request to save Compte : {}", compteDTO);
        Compte compte = compteMapper.toEntity(compteDTO);
        compte = compteRepository.save(compte);
        return compteMapper.toDto(compte);
    }

    @Override
    public CompteDTO update(CompteDTO compteDTO) {
        log.debug("Request to update Compte : {}", compteDTO);
        Compte compte = compteMapper.toEntity(compteDTO);
        compte = compteRepository.save(compte);
        return compteMapper.toDto(compte);
    }

    @Override
    public Optional<CompteDTO> partialUpdate(CompteDTO compteDTO) {
        log.debug("Request to partially update Compte : {}", compteDTO);

        return compteRepository
            .findById(compteDTO.getId())
            .map(existingCompte -> {
                compteMapper.partialUpdate(existingCompte, compteDTO);

                return existingCompte;
            })
            .map(compteRepository::save)
            .map(compteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompteDTO> findAll() {
        log.debug("Request to get all Comptes");
        return compteRepository.findAll().stream().map(compteMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the comptes where Soignant is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompteDTO> findAllWhereSoignantIsNull() {
        log.debug("Request to get all comptes where Soignant is null");
        return StreamSupport
            .stream(compteRepository.findAll().spliterator(), false)
            .filter(compte -> compte.getSoignant() == null)
            .map(compteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the comptes where Medecin is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompteDTO> findAllWhereMedecinIsNull() {
        log.debug("Request to get all comptes where Medecin is null");
        return StreamSupport
            .stream(compteRepository.findAll().spliterator(), false)
            .filter(compte -> compte.getMedecin() == null)
            .map(compteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the comptes where Admin is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CompteDTO> findAllWhereAdminIsNull() {
        log.debug("Request to get all comptes where Admin is null");
        return StreamSupport
            .stream(compteRepository.findAll().spliterator(), false)
            .filter(compte -> compte.getAdmin() == null)
            .map(compteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompteDTO> findOne(Long id) {
        log.debug("Request to get Compte : {}", id);
        return compteRepository.findById(id).map(compteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compte : {}", id);
        compteRepository.deleteById(id);
    }
}
