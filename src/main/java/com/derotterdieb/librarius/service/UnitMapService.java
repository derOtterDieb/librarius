package com.derotterdieb.librarius.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.derotterdieb.librarius.service.dto.UnitMapDTO;

public interface UnitMapService {
    /**
     * Save a UnitMap.
     *
     * @param UnitMapDTO the entity to save.
     * @return the persisted entity.
     */
    UnitMapDTO save(UnitMapDTO UnitMapDTO);

    /**
     * Get all the UnitMaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitMapDTO> findAll(Pageable pageable);

    /**
     * Get all the UnitMaps with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<UnitMapDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" UnitMap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnitMapDTO> findOne(String id);

    /**
     * Delete the "id" UnitMap.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the UnitMap corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UnitMapDTO> search(String query, Pageable pageable);
}
