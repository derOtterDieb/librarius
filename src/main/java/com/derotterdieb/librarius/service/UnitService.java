package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.UnitDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.derotterdieb.librarius.domain.Unit}.
 */
public interface UnitService {

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    UnitDTO save(UnitDTO unitDTO);

    /**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitDTO> findAll(Pageable pageable);

    /**
     * Get all the units with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UnitDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" unit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitDTO> findOne(String id);

    /**
     * Delete the "id" unit.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the unit corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitDTO> search(String query, Pageable pageable);
}
