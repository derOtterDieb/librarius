package com.derotterdieb.librarius.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.derotterdieb.librarius.service.SquadronMapService;
import com.derotterdieb.librarius.service.SquadronService;
import com.derotterdieb.librarius.service.dto.SquadronDTO;
import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.derotterdieb.librarius.domain.UnitMap;
import com.derotterdieb.librarius.repository.UnitMapRepository;
import com.derotterdieb.librarius.service.UnitMapService;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;
import com.derotterdieb.librarius.service.mapper.UnitMapMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

@Service
public class UnitMapServiceImpl implements UnitMapService {

	private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

	private final UnitMapRepository unitMapRepository;

	private final UnitMapMapper unitMapMapper;

	private final SquadronMapService squadronMapService;

	private final SquadronService squadronService;

	public UnitMapServiceImpl(
		UnitMapRepository unitMapRepository,
		UnitMapMapper unitMapMapper,
        SquadronMapService squadronMapService,
        SquadronService squadronService
	) {
		this.unitMapRepository = unitMapRepository;
		this.unitMapMapper = unitMapMapper;
		this.squadronMapService = squadronMapService;
		this.squadronService = squadronService;
	}

	@Override
	public UnitMapDTO save(UnitMapDTO UnitMapDTO) {
		log.debug("Request to save UnitMap : {}", UnitMapDTO);
        UnitMap unitMap = unitMapMapper.toEntity(UnitMapDTO);
        unitMap = unitMapRepository.save(unitMap);
        UnitMapDTO result = unitMapMapper.toDto(unitMap);
        return result;
	}

	@Override
	public Page<UnitMapDTO> findAll(Pageable pageable) {
		log.debug("Request to get all UnitMaps");
        return unitMapRepository.findAll(pageable)
            .map(unitMapMapper::toDto);
	}

	@Override
	public Page<UnitMapDTO> findAllWithEagerRelationships(Pageable pageable) {
		return unitMapRepository.findAllWithEagerRelationships(pageable).map(unitMapMapper::toDto);
	}

	@Override
	public Optional<UnitMapDTO> findOne(String id) {
		log.debug("Request to get UnitMap : {}", id);
        return unitMapRepository.findOneWithEagerRelationships(id)
            .map(unitMapMapper::toDto);
	}

	@Override
	public void delete(String id) {
		log.debug("Request to delete UnitMap : {}", id);
        unitMapRepository.deleteById(id);
	}

    @Override
    public List<UnitMapDTO> getUnitMapWithoutSquadronByListId(String listId) {
        List<UnitMapDTO> allUnitMaps = this.unitMapRepository.findAll().stream().map(o -> unitMapMapper.toDto(o)).collect(Collectors.toList());
        List<UnitMapDTO> result = new ArrayList<>();
        for (UnitMapDTO unitMap : allUnitMaps) {
            if (unitMap.getSquadronId() == null || unitMap.getSquadronId() == "" || unitMap.getSquadronId().isEmpty()) {
                result.add(unitMap);
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void createOrAddToSquadronMap(String unitMapId, String squadId, String userId, String listId) {
        Optional<UnitMapDTO> unitMapDTOOptional = this.findOne(unitMapId);
        if (unitMapDTOOptional.isPresent()) {
            UnitMapDTO unitMapDTO = unitMapDTOOptional.get();
            unitMapDTO.setSquadronId(squadId);
            unitMapDTO = this.save(unitMapDTO);
            List<SquadronMapDTO> squadronMapDTOS = this.squadronMapService.findByUserIdAndListId(userId, listId);
            SquadronMapDTO squadMap = checkIfMapAlreadyExistsAndGetIt(squadronMapDTOS, squadId);
            if (squadMap != null) {
                squadMap.addUnitMap(unitMapDTO);
                this.squadronMapService.save(squadMap);
            } else {
                Optional<SquadronDTO> squadOpt = this.squadronService.findOne(squadId);
                if (squadOpt.isPresent()) {
                    SquadronDTO squad = squadOpt.get();
                    SquadronMapDTO newSquadMap = new SquadronMapDTO();
                    newSquadMap.addUnitMap(unitMapDTO);
                    newSquadMap.setSquadron(squad);
                    this.squadronMapService.save(newSquadMap);
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
