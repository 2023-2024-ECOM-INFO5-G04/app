package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Admin;
import fr.polytech.g4.ecom23.repository.AdminRepository;
import fr.polytech.g4.ecom23.service.AdminService;
import fr.polytech.g4.ecom23.service.dto.AdminDTO;
import fr.polytech.g4.ecom23.service.mapper.AdminMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public AdminDTO save(AdminDTO adminDTO) {
        log.debug("Request to save Admin : {}", adminDTO);
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    public AdminDTO update(AdminDTO adminDTO) {
        log.debug("Request to update Admin : {}", adminDTO);
        Admin admin = adminMapper.toEntity(adminDTO);
        admin = adminRepository.save(admin);
        return adminMapper.toDto(admin);
    }

    @Override
    public Optional<AdminDTO> partialUpdate(AdminDTO adminDTO) {
        log.debug("Request to partially update Admin : {}", adminDTO);

        return adminRepository
            .findById(adminDTO.getId())
            .map(existingAdmin -> {
                adminMapper.partialUpdate(existingAdmin, adminDTO);

                return existingAdmin;
            })
            .map(adminRepository::save)
            .map(adminMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminDTO> findAll() {
        log.debug("Request to get all Admins");
        return adminRepository.findAll().stream().map(adminMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdminDTO> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id).map(adminMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
