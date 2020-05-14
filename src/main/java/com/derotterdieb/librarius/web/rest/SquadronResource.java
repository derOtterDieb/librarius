package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.service.SquadronService;
import com.derotterdieb.librarius.service.dto.SquadronDTO;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;
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
public class SquadronResource {
    private final Logger log = LoggerFactory.getLogger(UnitMapResource.class);

    private static final String ENTITY_NAME = "squadron";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SquadronService squadronService;

    public SquadronResource(SquadronService squadronService) {
        this.squadronService = squadronService;
    }

    @PostMapping("/squadrons")
    public ResponseEntity<SquadronDTO> createSquadron(@Valid @RequestBody SquadronDTO squadronDTO) throws URISyntaxException {
        log.debug("REST request to save squadron : {}", squadronDTO);
        if (squadronDTO.getId() != null) {
            throw new BadRequestAlertException("A new unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SquadronDTO result = squadronService.save(squadronDTO);
        return ResponseEntity.created(new URI("/api/squadrons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/squadrons")
    public ResponseEntity<SquadronDTO> updateSquadron(@Valid @RequestBody SquadronDTO squadronDTO) throws URISyntaxException {
        log.debug("REST request to update squadron : {}", squadronDTO);
        if (squadronDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SquadronDTO result = squadronService.save(squadronDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, squadronDTO.getId().toString()))
            .body(result);
    }

    @GetMapping("/squadrons")
    public ResponseEntity<List<SquadronDTO>> getAllSquadrons(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of squadrons");
        Page<SquadronDTO> page;
        if (eagerload) {
            page = squadronService.findAllWithEagerRelationships(pageable);
        } else {
            page = squadronService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/squadrons/userId/{userId}/listId/{listId}")
    public ResponseEntity<List<SquadronDTO>> findByUserIdAndListId(@PathVariable String userId, @PathVariable String listId) {
        log.debug("REST request to get squadrons");
        List<SquadronDTO> result = squadronService.findByUserIdAndListId(userId, listId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "list"))
            .body(result);
    }

    @GetMapping("/squadrons/{id}")
    public ResponseEntity<SquadronDTO> getSquadron(@PathVariable String id) {
        log.debug("REST request to get UnitMap : {}", id);
        Optional<SquadronDTO> squadronDTO = squadronService.findOne(id);
        return ResponseUtil.wrapOrNotFound(squadronDTO);
    }

    @DeleteMapping("/squadrons/{id}")
    public ResponseEntity<Void> deleteSquadron(@PathVariable String id) {
        log.debug("REST request to delete squardon : {}", id);
        squadronService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
