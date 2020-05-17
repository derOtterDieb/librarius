package com.derotterdieb.librarius.service;

import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SquadronMapService {
    SquadronMapDTO save(SquadronMapDTO squadronMapDTO);

    Page<SquadronMapDTO> findAll(Pageable pageable);

    Page<SquadronMapDTO> findAllWithEagerRelationships(Pageable pageable);

    Optional<SquadronMapDTO> findOne(String id);

    void delete(String id);

    List<SquadronMapDTO> findByUserIdAndListId(String userId, String listId);
}
