package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.domain.Squadron;
import com.derotterdieb.librarius.repository.SquadronRepository;
import com.derotterdieb.librarius.service.SquadronService;
import com.derotterdieb.librarius.service.dto.SquadronDTO;
import com.derotterdieb.librarius.service.mapper.SquadronMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SquadronServiceImpl implements SquadronService {
    private final Logger log = LoggerFactory.getLogger(SquadronServiceImpl.class);

    private final SquadronRepository squadronRepository;

    private final SquadronMapper squadronMapper;

    public SquadronServiceImpl(SquadronRepository squadronRepository, SquadronMapper squadronMapper) {
        this.squadronRepository = squadronRepository;
        this.squadronMapper = squadronMapper;
    }

    @Override
    public SquadronDTO save(SquadronDTO squadronDTO) {
        log.debug("Request to save Squadron : {}", squadronDTO);
        Squadron squadron = squadronMapper.toEntity(squadronDTO);
        squadron = squadronRepository.save(squadron);
        SquadronDTO result = squadronMapper.toDto(squadron);
        return result;
    }

    @Override
    public Page<SquadronDTO> findAll(Pageable pageable) {
        log.debug("Request to get all squadrons");
        return squadronRepository.findAll(pageable)
            .map(squadronMapper::toDto);
    }

    @Override
    public Page<SquadronDTO> findAllWithEagerRelationships(Pageable pageable) {
        return squadronRepository.findAllWithEagerRelationships(pageable).map(squadronMapper::toDto);
    }

    @Override
    public Optional<SquadronDTO> findOne(String id) {
        log.debug("Request to get squadron : {}", id);
        return squadronRepository.findOneWithEagerRelationships(id)
            .map(squadronMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete squadron : {}", id);
        squadronRepository.deleteById(id);
    }

    @Override
    public List<SquadronDTO> findByUserIdAndListId(String userId, String listId) {
        List<Squadron> allSquadrons = squadronRepository.findAll();
        List<Squadron> result = new ArrayList<>();
        for (Squadron squad : allSquadrons) {
            if (squad.getListId() != null && squad.getUserId() != null) {
                if (squad.getListId().equals(listId) && squad.getUserId().equals(userId)) {
                    result.add(squad);
                }
            }
        }
        return result.stream().map(squadronMapper::toDto).collect(Collectors.toList());
    }
}
