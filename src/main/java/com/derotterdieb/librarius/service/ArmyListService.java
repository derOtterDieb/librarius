package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.ArmyListDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.derotterdieb.librarius.domain.ArmyList}.
 */
public interface ArmyListService {

    /**
     * Save a armyList.
     *
     * @param armyListDTO the entity to save.
     * @return the persisted entity.
     */
    ArmyListDTO save(ArmyListDTO armyListDTO);

    /**
     * Get all the armyLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArmyListDTO> findAll(Pageable pageable);

    /**
     * Get all the armyLists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<ArmyListDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" armyList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArmyListDTO> findOne(String id);

    /**
     * Delete the "id" armyList.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the armyList corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArmyListDTO> search(String query, Pageable pageable);

	List<ArmyListDTO> findAllByUser(String id);
}
