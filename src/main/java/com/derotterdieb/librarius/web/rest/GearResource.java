package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.service.GearService;
import com.derotterdieb.librarius.web.rest.errors.BadRequestAlertException;
import com.derotterdieb.librarius.service.dto.GearDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.derotterdieb.librarius.domain.Gear}.
 */
@RestController
@RequestMapping("/api")
public class GearResource {

    private final Logger log = LoggerFactory.getLogger(GearResource.class);

    private static final String ENTITY_NAME = "gear";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GearService gearService;

    public GearResource(GearService gearService) {
        this.gearService = gearService;
    }

    /**
     * {@code POST  /gears} : Create a new gear.
     *
     * @param gearDTO the gearDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gearDTO, or with status {@code 400 (Bad Request)} if the gear has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gears")
    public ResponseEntity<GearDTO> createGear(@Valid @RequestBody GearDTO gearDTO) throws URISyntaxException {
        log.debug("REST request to save Gear : {}", gearDTO);
        if (gearDTO.getId() != null) {
            throw new BadRequestAlertException("A new gear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GearDTO result = gearService.save(gearDTO);
        return ResponseEntity.created(new URI("/api/gears/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gears} : Updates an existing gear.
     *
     * @param gearDTO the gearDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gearDTO,
     * or with status {@code 400 (Bad Request)} if the gearDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gearDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gears")
    public ResponseEntity<GearDTO> updateGear(@Valid @RequestBody GearDTO gearDTO) throws URISyntaxException {
        log.debug("REST request to update Gear : {}", gearDTO);
        if (gearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GearDTO result = gearService.save(gearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gearDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gears} : get all the gears.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gears in body.
     */
    @GetMapping("/gears")
    public ResponseEntity<List<GearDTO>> getAllGears(Pageable pageable) {
        log.debug("REST request to get a page of Gears");
        Page<GearDTO> page = gearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gears/:id} : get the "id" gear.
     *
     * @param id the id of the gearDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gearDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gears/{id}")
    public ResponseEntity<GearDTO> getGear(@PathVariable String id) {
        log.debug("REST request to get Gear : {}", id);
        Optional<GearDTO> gearDTO = gearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gearDTO);
    }

    @GetMapping("/gears/search/{name}")
    public ResponseEntity<List<GearDTO>> getAllByName(@PathVariable String name) {
        log.debug("REST request to get Gears : {}", name);
        List<GearDTO> list = gearService.findAllByName(name);
        HttpHeaders headers = HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "list from name");
        return ResponseEntity.ok().headers(headers).body(list);
    }

    /**
     * {@code DELETE  /gears/:id} : delete the "id" gear.
     *
     * @param id the id of the gearDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gears/{id}")
    public ResponseEntity<Void> deleteGear(@PathVariable String id) {
        log.debug("REST request to delete Gear : {}", id);
        gearService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

  /*  @GetMapping("/_search/gears")
    public ResponseEntity<List<GearDTO>> searchGears(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Gears for query {}", query);
        Page<GearDTO> page = gearService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }*/
}
