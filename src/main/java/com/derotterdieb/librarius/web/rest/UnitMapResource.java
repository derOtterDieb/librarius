package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.service.UnitMapService;
import com.derotterdieb.librarius.service.UnitService;
import com.derotterdieb.librarius.web.rest.errors.BadRequestAlertException;
import com.derotterdieb.librarius.service.dto.UnitDTO;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.derotterdieb.librarius.domain.Unit}.
 */
@RestController
@RequestMapping("/api")
public class UnitMapResource {

    private final Logger log = LoggerFactory.getLogger(UnitMapResource.class);

    private static final String ENTITY_NAME = "unit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnitMapService unitMapService;

    public UnitMapResource(UnitMapService unitMapService) {
        this.unitMapService = unitMapService;
    }

    /**
     * {@code POST  /units} : Create a new unit.
     *
     * @param unitMapDTO the unitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unitDTO, or with status {@code 400 (Bad Request)} if the unit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unit-maps")
    public ResponseEntity<UnitMapDTO> createUnitMap(@Valid @RequestBody UnitMapDTO unitMapDTO) throws URISyntaxException {
        log.debug("REST request to save Unit : {}", unitMapDTO);
        if (unitMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitMapDTO result = unitMapService.save(unitMapDTO);
        return ResponseEntity.created(new URI("/api/unit-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /units} : Updates an existing unit.
     *
     * @param unitMapDTO the unitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unitDTO,
     * or with status {@code 400 (Bad Request)} if the unitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unit-maps")
    public ResponseEntity<UnitMapDTO> updateUnitMap(@Valid @RequestBody UnitMapDTO unitMapDTO) throws URISyntaxException {
        log.debug("REST request to update UnitMap : {}", unitMapDTO);
        if (unitMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitMapDTO result = unitMapService.save(unitMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unitMapDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /units} : get all the units.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of units in body.
     */
    @GetMapping("/unit-maps")
    public ResponseEntity<List<UnitMapDTO>> getAllUnitMaps(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of UnitMaps");
        Page<UnitMapDTO> page;
        if (eagerload) {
            page = unitMapService.findAllWithEagerRelationships(pageable);
        } else {
            page = unitMapService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /units/:id} : get the "id" unit.
     *
     * @param id the id of the unitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unit-maps/{id}")
    public ResponseEntity<UnitMapDTO> getUnitMap(@PathVariable String id) {
        log.debug("REST request to get UnitMap : {}", id);
        Optional<UnitMapDTO> unitMapDTO = unitMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitMapDTO);
    }

    /**
     * {@code DELETE  /units/:id} : delete the "id" unit.
     *
     * @param id the id of the unitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unit-maps/{id}")
    public ResponseEntity<Void> deleteUnitMap(@PathVariable String id) {
        log.debug("REST request to delete UnitMap : {}", id);
        unitMapService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/units?query=:query} : search for the unit corresponding
     * to the query.
     *
     * @param query the query of the unit search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/unit-maps")
    public ResponseEntity<List<UnitMapDTO>> searchUnits(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of UnitMaps for query {}", query);
        Page<UnitMapDTO> page = unitMapService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
