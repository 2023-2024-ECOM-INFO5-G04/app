package fr.polytech.g4.ecom23.service.impl;

import fr.polytech.g4.ecom23.domain.Notes;
import fr.polytech.g4.ecom23.repository.NotesRepository;
import fr.polytech.g4.ecom23.service.NotesService;
import fr.polytech.g4.ecom23.service.dto.NotesDTO;
import fr.polytech.g4.ecom23.service.mapper.NotesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Notes}.
 */
@Service
@Transactional
public class NotesServiceImpl implements NotesService {

    private final Logger log = LoggerFactory.getLogger(NotesServiceImpl.class);

    private final NotesRepository notesRepository;

    private final NotesMapper notesMapper;

    public NotesServiceImpl(NotesRepository notesRepository, NotesMapper notesMapper) {
        this.notesRepository = notesRepository;
        this.notesMapper = notesMapper;
    }

    @Override
    public NotesDTO save(NotesDTO notesDTO) {
        log.debug("Request to save Notes : {}", notesDTO);
        Notes notes = notesMapper.toEntity(notesDTO);
        notes = notesRepository.save(notes);
        return notesMapper.toDto(notes);
    }

    @Override
    public NotesDTO update(NotesDTO notesDTO) {
        log.debug("Request to update Notes : {}", notesDTO);
        Notes notes = notesMapper.toEntity(notesDTO);
        notes = notesRepository.save(notes);
        return notesMapper.toDto(notes);
    }

    @Override
    public Optional<NotesDTO> partialUpdate(NotesDTO notesDTO) {
        log.debug("Request to partially update Notes : {}", notesDTO);

        return notesRepository
            .findById(notesDTO.getId())
            .map(existingNotes -> {
                notesMapper.partialUpdate(existingNotes, notesDTO);

                return existingNotes;
            })
            .map(notesRepository::save)
            .map(notesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NotesDTO> findAll() {
        log.debug("Request to get all Notes");
        return notesRepository.findAll().stream().map(notesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NotesDTO> findOne(Long id) {
        log.debug("Request to get Notes : {}", id);
        return notesRepository.findById(id).map(notesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Notes : {}", id);
        notesRepository.deleteById(id);
    }
}
