package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Servicesoignant;
import fr.polytech.g4.ecom23.repository.ServicesoignantRepository;
import fr.polytech.g4.ecom23.service.ServicesoignantService;
import fr.polytech.g4.ecom23.service.dto.ServicesoignantDTO;
import fr.polytech.g4.ecom23.service.mapper.ServicesoignantMapper;
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
 * Service Implementation for managing {@link Servicesoignant}.
 */
@Service
@Transactional
public class ServicesoignantServiceImpl implements ServicesoignantService {

    private final Logger log = LoggerFactory.getLogger(ServicesoignantServiceImpl.class);

    private final ServicesoignantRepository servicesoignantRepository;

    private final ServicesoignantMapper servicesoignantMapper;

    public ServicesoignantServiceImpl(ServicesoignantRepository servicesoignantRepository, ServicesoignantMapper servicesoignantMapper) {
        this.servicesoignantRepository = servicesoignantRepository;
        this.servicesoignantMapper = servicesoignantMapper;
    }

    @Override
    public ServicesoignantDTO save(ServicesoignantDTO servicesoignantDTO) {
        log.debug("Request to save Servicesoignant : {}", servicesoignantDTO);
        Servicesoignant servicesoignant = servicesoignantMapper.toEntity(servicesoignantDTO);
        servicesoignant = servicesoignantRepository.save(servicesoignant);
        return servicesoignantMapper.toDto(servicesoignant);
    }

    @Override
    public ServicesoignantDTO update(ServicesoignantDTO servicesoignantDTO) {
        log.debug("Request to update Servicesoignant : {}", servicesoignantDTO);
        Servicesoignant servicesoignant = servicesoignantMapper.toEntity(servicesoignantDTO);
        servicesoignant = servicesoignantRepository.save(servicesoignant);
        return servicesoignantMapper.toDto(servicesoignant);
    }

    @Override
    public Optional<ServicesoignantDTO> partialUpdate(ServicesoignantDTO servicesoignantDTO) {
        log.debug("Request to partially update Servicesoignant : {}", servicesoignantDTO);

        return servicesoignantRepository
            .findById(servicesoignantDTO.getId())
            .map(existingServicesoignant -> {
                servicesoignantMapper.partialUpdate(existingServicesoignant, servicesoignantDTO);

                return existingServicesoignant;
            })
            .map(servicesoignantRepository::save)
            .map(servicesoignantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicesoignantDTO> findAll() {
        log.debug("Request to get all Servicesoignants");
        return servicesoignantRepository
            .findAll()
            .stream()
            .map(servicesoignantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<ServicesoignantDTO> findAllWithEagerRelationships(Pageable pageable) {
        return servicesoignantRepository.findAllWithEagerRelationships(pageable).map(servicesoignantMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ServicesoignantDTO> findOne(Long id) {
        log.debug("Request to get Servicesoignant : {}", id);
        return servicesoignantRepository.findOneWithEagerRelationships(id).map(servicesoignantMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Servicesoignant : {}", id);
        servicesoignantRepository.deleteById(id);
    }
}
