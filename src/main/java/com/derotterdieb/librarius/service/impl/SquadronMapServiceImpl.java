package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.domain.SquadronMap;
import com.derotterdieb.librarius.repository.SquadronMapRepository;
import com.derotterdieb.librarius.service.SquadronMapService;
import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import com.derotterdieb.librarius.service.mapper.SquadronMapMapper;
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
public class SquadronMapServiceImpl implements SquadronMapService {

    private final Logger log = LoggerFactory.getLogger(SquadronMapServiceImpl.class);

    private final SquadronMapRepository squadronMapRepository;

    private final SquadronMapMapper squadronMapMapper;

    public SquadronMapServiceImpl(SquadronMapRepository squadronMapRepository, SquadronMapMapper squadronMapMapper) {
        this.squadronMapRepository = squadronMapRepository;
        this.squadronMapMapper = squadronMapMapper;
    }

    @Override
    public SquadronMapDTO save(SquadronMapDTO squadronMapDTO) {
        SquadronMap saved = this.squadronMapRepository.save(this.squadronMapMapper.toEntity(squadronMapDTO));
        return this.squadronMapMapper.toDto(saved);
    }

    @Override
    public Page<SquadronMapDTO> findAll(Pageable pageable) {
        return this.squadronMapRepository.findAll(pageable).map(squadronMapMapper::toDto);
    }

    @Override
    public Page<SquadronMapDTO> findAllWithEagerRelationships(Pageable pageable) {
        return this.squadronMapRepository.findAllWithEagerRelationships(pageable).map(squadronMapMapper::toDto);
    }

    @Override
    public Optional<SquadronMapDTO> findOne(String id) {
        Optional<SquadronMap> resultOpt = this.squadronMapRepository.findById(id);
        if (resultOpt.isPresent()) {
            return Optional.of(squadronMapMapper.toDto(resultOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public void delete(String id) {
        this.squadronMapRepository.deleteById(id);
    }

    @Override
    public List<SquadronMapDTO> findByUserIdAndListId(String userId, String listId) {
        List<SquadronMapDTO> allSquadrons = this.squadronMapRepository.findAll().stream().map(o -> squadronMapMapper.toDto(o)).collect(Collectors.toList());
        List<SquadronMapDTO> result = new ArrayList<>();

        for (SquadronMapDTO squad : allSquadrons) {
            if (squad.getSquadron().getUserId().equals(userId) && squad.getSquadron().getListId().equals(listId)) {
                result.add(squad);
            }
        }

        return result;
    }
}
