package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.service.SquadronMapService;
import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import com.derotterdieb.librarius.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SquadronMapResource {

    private final Logger log = LoggerFactory.getLogger(UnitMapResource.class);

    private static final String ENTITY_NAME = "squadronMap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SquadronMapService squadronMapService;

    public SquadronMapResource(SquadronMapService squadronMapService) {
        this.squadronMapService = squadronMapService;
    }

    @PostMapping("/squadron-maps")
    public ResponseEntity<SquadronMapDTO> createSquadronMap(@Valid @RequestBody SquadronMapDTO squadronMapDTO) throws URISyntaxException {
        log.debug("REST request to save squadron map : {}", squadronMapDTO);
        if (squadronMapDTO.getId() != null) {
            throw new BadRequestAlertException("A new squadron map cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SquadronMapDTO result = squadronMapService.save(squadronMapDTO);
        return ResponseEntity.created(new URI("/api/squadron-maps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/squadron-maps")
    public ResponseEntity<SquadronMapDTO> updateSquadronMap(@Valid @RequestBody SquadronMapDTO squadronMapDTO) throws URISyntaxException {
        log.debug("REST request to update squadron map : {}", squadronMapDTO);
        if (squadronMapDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SquadronMapDTO result = squadronMapService.save(squadronMapDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, squadronMapDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/squadron-maps/userId/{userId}/listId/{listId}")
    public ResponseEntity<List<SquadronMapDTO>> findByUserIdAndListId(@PathVariable String userId, @PathVariable String listId) {
        log.debug("REST request to get a list of squadron maps");
        List<SquadronMapDTO> result = squadronMapService.findByUserIdAndListId(userId, listId);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "list"))
            .body(result);
    }

    @GetMapping("/squadron-maps")
    public ResponseEntity<List<SquadronMapDTO>> getAllSquadronMaps(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of squadron maps");
        Page<SquadronMapDTO> page;
        if (eagerload) {
            page = squadronMapService.findAllWithEagerRelationships(pageable);
        } else {
            page = squadronMapService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/squadron-maps/{id}")
    public ResponseEntity<SquadronMapDTO> getSquadronMap(@PathVariable String id) {
        log.debug("REST request to get squadron map : {}", id);
        Optional<SquadronMapDTO> unitMapDTO = squadronMapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unitMapDTO);
    }

    @DeleteMapping("/squadron-maps/{id}")
    public ResponseEntity<Void> deleteSquadronMap(@PathVariable String id) {
        log.debug("REST request to delete squadron map : {}", id);
        squadronMapService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
