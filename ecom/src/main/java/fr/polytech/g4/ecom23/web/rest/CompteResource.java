package fr.polytech.g4.ecom23.web.rest;

import fr.polytech.g4.ecom23.repository.CompteRepository;
import fr.polytech.g4.ecom23.service.CompteService;
import fr.polytech.g4.ecom23.service.dto.CompteDTO;
import fr.polytech.g4.ecom23.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.polytech.g4.ecom23.domain.Compte}.
 */
@RestController
@RequestMapping("/api")
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteService compteService;

    private final CompteRepository compteRepository;

    public CompteResource(CompteService compteService, CompteRepository compteRepository) {
        this.compteService = compteService;
        this.compteRepository = compteRepository;
    }

    /**
     * {@code POST  /comptes} : Create a new compte.
     *
     * @param compteDTO the compteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compteDTO, or with status {@code 400 (Bad Request)} if the compte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comptes")
    public ResponseEntity<CompteDTO> createCompte(@RequestBody CompteDTO compteDTO) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compteDTO);
        if (compteDTO.getId() != null) {
            throw new BadRequestAlertException("A new compte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompteDTO result = compteService.save(compteDTO);
        return ResponseEntity
            .created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comptes/:id} : Updates an existing compte.
     *
     * @param id the id of the compteDTO to save.
     * @param compteDTO the compteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteDTO,
     * or with status {@code 400 (Bad Request)} if the compteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comptes/{id}")
    public ResponseEntity<CompteDTO> updateCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompteDTO compteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Compte : {}, {}", id, compteDTO);
        if (compteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompteDTO result = compteService.update(compteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /comptes/:id} : Partial updates given fields of an existing compte, field will ignore if it is null
     *
     * @param id the id of the compteDTO to save.
     * @param compteDTO the compteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteDTO,
     * or with status {@code 400 (Bad Request)} if the compteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/comptes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompteDTO> partialUpdateCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompteDTO compteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Compte partially : {}, {}", id, compteDTO);
        if (compteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, compteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompteDTO> result = compteService.partialUpdate(compteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /comptes} : get all the comptes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of comptes in body.
     */
    @GetMapping("/comptes")
    public List<CompteDTO> getAllComptes(@RequestParam(required = false) String filter) {
        if ("soignant-is-null".equals(filter)) {
            log.debug("REST request to get all Comptes where soignant is null");
            return compteService.findAllWhereSoignantIsNull();
        }

        if ("medecin-is-null".equals(filter)) {
            log.debug("REST request to get all Comptes where medecin is null");
            return compteService.findAllWhereMedecinIsNull();
        }

        if ("admin-is-null".equals(filter)) {
            log.debug("REST request to get all Comptes where admin is null");
            return compteService.findAllWhereAdminIsNull();
        }
        log.debug("REST request to get all Comptes");
        return compteService.findAll();
    }

    /**
     * {@code GET  /comptes/:id} : get the "id" compte.
     *
     * @param id the id of the compteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comptes/{id}")
    public ResponseEntity<CompteDTO> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Optional<CompteDTO> compteDTO = compteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteDTO);
    }

    /**
     * {@code DELETE  /comptes/:id} : delete the "id" compte.
     *
     * @param id the id of the compteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comptes/{id}")
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
