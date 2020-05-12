package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.GearDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.derotterdieb.librarius.domain.Gear}.
 */
public interface GearService {

    /**
     * Save a gear.
     *
     * @param gearDTO the entity to save.
     * @return the persisted entity.
     */
    GearDTO save(GearDTO gearDTO);

    /**
     * Get all the gears.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GearDTO> findAll(Pageable pageable);

    /**
     * Get the "id" gear.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GearDTO> findOne(String id);

    /**
     * Delete the "id" gear.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the gear corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GearDTO> search(String query, Pageable pageable);

    List<GearDTO> findAllByName(String name);
}
