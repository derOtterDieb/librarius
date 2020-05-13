package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.ExtendedUserDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.derotterdieb.librarius.domain.ExtendedUser}.
 */
public interface ExtendedUserService {

    /**
     * Save a extendedUser.
     *
     * @param extendedUserDTO the entity to save.
     * @return the persisted entity.
     */
    ExtendedUserDTO save(ExtendedUserDTO extendedUserDTO);

    /**
     * Get all the extendedUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExtendedUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" extendedUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExtendedUserDTO> findOne(String id);

    /**
     * Delete the "id" extendedUser.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the extendedUser corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
//    Page<ExtendedUserDTO> search(String query, Pageable pageable);
}
