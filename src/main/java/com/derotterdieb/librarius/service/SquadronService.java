package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.SquadronDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SquadronService {
    SquadronDTO save(SquadronDTO squadronDTO);

    Page<SquadronDTO> findAll(Pageable pageable);

    Page<SquadronDTO> findAllWithEagerRelationships(Pageable pageable);

    Optional<SquadronDTO> findOne(String id);

    void delete(String id);

    List<SquadronDTO> findByUserIdAndListId(String userId, String listId);
}
