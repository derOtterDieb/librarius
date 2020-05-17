package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.domain.SquadronMap;
import com.derotterdieb.librarius.repository.SquadronMapRepository;
import com.derotterdieb.librarius.service.SquadronMapService;
import com.derotterdieb.librarius.service.SquadronService;
import com.derotterdieb.librarius.service.UnitMapService;
import com.derotterdieb.librarius.service.dto.SquadronDTO;
import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;
import com.derotterdieb.librarius.service.mapper.SquadronMapMapper;
import com.derotterdieb.librarius.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SquadronMapServiceImpl implements SquadronMapService {

    private final Logger log = LoggerFactory.getLogger(SquadronMapServiceImpl.class);

    private final SquadronMapRepository squadronMapRepository;

    private final SquadronMapMapper squadronMapMapper;

    private final SquadronService squadronService;

    private final UnitMapService unitMapService;

    public SquadronMapServiceImpl(
        SquadronMapRepository squadronMapRepository,
        SquadronMapMapper squadronMapMapper,
        SquadronService squadronService,
        UnitMapService unitMapService
    ) {
        this.squadronMapRepository = squadronMapRepository;
        this.squadronMapMapper = squadronMapMapper;
        this.squadronService = squadronService;
        this.unitMapService = unitMapService;
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

    @Override
    @Transactional
    public void deleteCascade(String id) {
        Optional<SquadronMapDTO> squadronMapDTOOptional = this.findOne(id);
        if (squadronMapDTOOptional.isPresent()) {
            SquadronMapDTO squadMap = squadronMapDTOOptional.get();
            for (UnitMapDTO unitMapDTO : squadMap.getUnitMaps()) {
                unitMapDTO.setSquadronId(null);
                this.unitMapService.save(unitMapDTO);
            }
            this.squadronService.delete(squadMap.getSquadron().getId());
            this.delete(id);
        }
    }

    @Override
    @Transactional
    public SquadronMapDTO removeUnitFromSquad(String unitId, String squadronMapId) {
        Optional<SquadronMapDTO> squadronMapDTOOptional = this.findOne(squadronMapId);
        Optional<UnitMapDTO> unitMapDTOOptional = this.unitMapService.findOne(unitId);
        if (squadronMapDTOOptional.isPresent() && unitMapDTOOptional.isPresent()) {
            SquadronMapDTO squadMap = squadronMapDTOOptional.get();
            UnitMapDTO unitMapDTO = unitMapDTOOptional.get();
            unitMapDTO.setSquadronId(null);
            this.unitMapService.save(unitMapDTO);
            UnitMapDTO unitToRemove = new UnitMapDTO();
            for (UnitMapDTO unitMap : squadMap.getUnitMaps()) {
                if (unitMap.getId().equals(unitId)) {
                    unitToRemove = unitMap;
                }
            }
            squadMap = squadMap.removeUnitMap(unitToRemove);
            this.save(squadMap);
            return squadMap;
        } else {
            throw new BadRequestAlertException("Invalid id", "entity null", "idnull");
        }
    }

    @Override
    @Transactional
    public void createOrAddToSquadronMap(String unitMapId, String squadId, String userId, String listId) {
        Optional<UnitMapDTO> unitMapDTOOptional = this.unitMapService.findOne(unitMapId);
        if (unitMapDTOOptional.isPresent()) {
            UnitMapDTO unitMapDTO = unitMapDTOOptional.get();
            unitMapDTO.setSquadronId(squadId);
            unitMapDTO = this.unitMapService.save(unitMapDTO);
            List<SquadronMapDTO> squadronMapDTOS = this.findByUserIdAndListId(userId, listId);
            SquadronMapDTO squadMap = checkIfMapAlreadyExistsAndGetIt(squadronMapDTOS, squadId);
            if (squadMap != null) {
                squadMap.addUnitMap(unitMapDTO);
                this.save(squadMap);
            } else {
                Optional<SquadronDTO> squadOpt = this.squadronService.findOne(squadId);
                if (squadOpt.isPresent()) {
                    SquadronDTO squad = squadOpt.get();
                    SquadronMapDTO newSquadMap = new SquadronMapDTO();
                    newSquadMap.addUnitMap(unitMapDTO);
                    newSquadMap.setSquadron(squad);
                    this.save(newSquadMap);
                }
            }
        }
    }

    private SquadronMapDTO checkIfMapAlreadyExistsAndGetIt(List<SquadronMapDTO> squadMaps, String squadId) {
        for (SquadronMapDTO squadMap : squadMaps) {
            if (squadMap.getSquadron().getId().equals(squadId)) {
                return squadMap;
            }
        }
        return null;
    }
}
