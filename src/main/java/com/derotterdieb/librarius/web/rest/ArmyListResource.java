package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.service.ArmyListService;
import com.derotterdieb.librarius.service.UnitMapService;
import com.derotterdieb.librarius.service.UnitService;
import com.derotterdieb.librarius.web.rest.errors.BadRequestAlertException;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;
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
import java.util.Set;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.derotterdieb.librarius.domain.ArmyList}.
 */
@RestController
@RequestMapping("/api")
public class ArmyListResource {

    private final Logger log = LoggerFactory.getLogger(ArmyListResource.class);

    private static final String ENTITY_NAME = "armyList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArmyListService armyListService;
    
    private final UnitService unitService;
    
    private final UnitMapService unitMapService;

    public ArmyListResource(ArmyListService armyListService, UnitService unitService, UnitMapService unitMapService) {
        this.armyListService = armyListService;
        this.unitService = unitService;
        this.unitMapService = unitMapService;
    }

    /**
     * {@code POST  /army-lists} : Create a new armyList.
     *
     * @param armyListDTO the armyListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new armyListDTO, or with status {@code 400 (Bad Request)} if the armyList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/army-lists")
    public ResponseEntity<ArmyListDTO> createArmyList(@Valid @RequestBody ArmyListDTO armyListDTO) throws URISyntaxException {
        log.debug("REST request to save ArmyList : {}", armyListDTO);
        if (armyListDTO.getId() != null) {
            throw new BadRequestAlertException("A new armyList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArmyListDTO result = armyListService.save(armyListDTO);
        return ResponseEntity.created(new URI("/api/army-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /army-lists} : Updates an existing armyList.
     *
     * @param armyListDTO the armyListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated armyListDTO,
     * or with status {@code 400 (Bad Request)} if the armyListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the armyListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/army-lists")
    public ResponseEntity<ArmyListDTO> updateArmyList(@Valid @RequestBody ArmyListDTO armyListDTO) throws URISyntaxException {
        log.debug("REST request to update ArmyList : {}", armyListDTO);
        if (armyListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArmyListDTO result = armyListService.save(armyListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, armyListDTO.getId().toString()))
            .body(result);
    }
    
    @PutMapping("/army-lists/add-unit/{id}/{numberOfUnit}")
    public ResponseEntity<ArmyListDTO> addArmyList(@PathVariable String id, @PathVariable Integer numberOfUnit, @Valid @RequestBody UnitDTO unitDTO) {
    	log.debug("REST request to add unit to ArmyList : {}", id);
    	if (id == null) {
    		throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    	}
    	if (unitDTO.getId() == null) {
    		unitDTO = unitService.save(unitDTO);
    	}
    	UnitMapDTO unitMapDTO = new UnitMapDTO();
    	unitMapDTO.setNumberOfUnit(numberOfUnit);
    	unitMapDTO.setUnit(unitDTO);
    	unitMapDTO = unitMapService.save(unitMapDTO);
    	ArmyListDTO result = armyListService.addUnit(id, unitMapDTO);
    	return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME,id))
                .body(result);
    }

    /**
     * {@code GET  /army-lists} : get all the armyLists.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of armyLists in body.
     */
    @GetMapping("/army-lists")
    public ResponseEntity<List<ArmyListDTO>> getAllArmyLists(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ArmyLists");
        Page<ArmyListDTO> page;
        if (eagerload) {
            page = armyListService.findAllWithEagerRelationships(pageable);
        } else {
            page = armyListService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /army-lists/:id} : get the "id" armyList.
     *
     * @param id the id of the armyListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the armyListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/army-lists/{id}")
    public ResponseEntity<ArmyListDTO> getArmyList(@PathVariable String id) {
        log.debug("REST request to get ArmyList : {}", id);
        Optional<ArmyListDTO> armyListDTO = armyListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(armyListDTO);
    }

    /**
     * {@code DELETE  /army-lists/:id} : delete the "id" armyList.
     *
     * @param id the id of the armyListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/army-lists/{id}")
    public ResponseEntity<Void> deleteArmyList(@PathVariable String id) {
        log.debug("REST request to delete ArmyList : {}", id);
        armyListService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/army-lists?query=:query} : search for the armyList corresponding
     * to the query.
     *
     * @param query the query of the armyList search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/army-lists")
    public ResponseEntity<List<ArmyListDTO>> searchArmyLists(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ArmyLists for query {}", query);
        Page<ArmyListDTO> page = armyListService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    
    @GetMapping("/army-lists/user/{id}")
    public ResponseEntity<List<ArmyListDTO>> getFromUserId(@PathVariable String id) {
    	log.debug("REST request to search for armyLists of a user ", id);
    	Optional<List<ArmyListDTO>> result = this.armyListService.findAllByUser(id);
    	return ResponseUtil.wrapOrNotFound(result);
    }
}
