package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.domain.Gear;
import com.derotterdieb.librarius.service.ArmyListService;
import com.derotterdieb.librarius.domain.ArmyList;
import com.derotterdieb.librarius.domain.UnitMap;
import com.derotterdieb.librarius.domain.User;
import com.derotterdieb.librarius.repository.ArmyListRepository;
import com.derotterdieb.librarius.repository.UnitMapRepository;
import com.derotterdieb.librarius.repository.UserRepository;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;
import com.derotterdieb.librarius.service.dto.GearDTO;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;
import com.derotterdieb.librarius.service.mapper.ArmyListMapper;
import com.derotterdieb.librarius.service.mapper.UnitMapMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;


/**
 * Service Implementation for managing {@link ArmyList}.
 */
@Service
public class ArmyListServiceImpl implements ArmyListService {

    private final Logger log = LoggerFactory.getLogger(ArmyListServiceImpl.class);

    private final ArmyListRepository armyListRepository;

    private final UserRepository userRepository;

    private final ArmyListMapper armyListMapper;

//    private final ArmyListSearchRepository armyListSearchRepository;

    private final UnitMapRepository unitMapRepository;

    private final UnitMapMapper unitMapMapper;

    public ArmyListServiceImpl(ArmyListRepository armyListRepository, ArmyListMapper armyListMapper,
    		UserRepository userRepository,
    		UnitMapRepository unitRepository, UnitMapMapper unitMapper) {
        this.armyListRepository = armyListRepository;
        this.armyListMapper = armyListMapper;
//        this.armyListSearchRepository = armyListSearchRepository;
        this.userRepository = userRepository;
        this.unitMapRepository = unitRepository;
        this.unitMapMapper = unitMapper;
    }

    private int computeDTOPoints(ArmyListDTO armyListDTO) {
        int result = 0;
        if (armyListDTO.getUnitMaps() != null) {
            for (UnitMapDTO unitMap : armyListDTO.getUnitMaps()) {
                int gearPoints = 0;
                if (unitMap.getNumberOfUnit() != null && unitMap.getUnit().getTotalPoint() != null) {
                    for (GearDTO gearDTO : unitMap.getGears()) {
                        gearPoints += gearDTO.getPointValue();
                    }
                    result += (unitMap.getUnit().getTotalPoint() + gearPoints) * unitMap.getNumberOfUnit();
                }
            }
        }
        return result;
    }

    private int computeEntityPoints(ArmyList armyList) {
        int result = 0;
        if (armyList.getUnitMap() != null) {
            for (UnitMap unitMap : armyList.getUnitMap()) {
                int gearPoints = 0;
                if (unitMap.getNumberOfUnit() != null && unitMap.getUnit().getTotalPoint() != null) {
                    for (Gear gear : unitMap.getGears()) {
                        gearPoints += gear.getPointValue();
                    }
                    result += (unitMap.getUnit().getTotalPoint() + gearPoints) * unitMap.getNumberOfUnit();
                }
            }
        }
        return result;
    }

    /**
     * Save a armyList.
     *
     * @param armyListDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ArmyListDTO save(ArmyListDTO armyListDTO) {
        log.debug("Request to save ArmyList : {}", armyListDTO);
        armyListDTO.setTotalPoint(this.computeDTOPoints(armyListDTO));
        ArmyList armyList = armyListMapper.toEntity(armyListDTO);
        armyList = armyListRepository.save(armyList);
        ArmyListDTO result = armyListMapper.toDto(armyList);
//        armyListSearchRepository.save(armyList);
        return result;
    }

    /**
     * Get all the armyLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ArmyListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArmyLists");
        return armyListRepository.findAll(pageable)
            .map(armyListMapper::toDto);
    }

    /**
     * Get all the armyLists with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ArmyListDTO> findAllWithEagerRelationships(Pageable pageable) {
        return armyListRepository.findAllWithEagerRelationships(pageable).map(armyListMapper::toDto);
    }

    /**
     * Get one armyList by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ArmyListDTO> findOne(String id) {
        log.debug("Request to get ArmyList : {}", id);
        Optional<ArmyList> resultEntity = armyListRepository.findOneWithEagerRelationships(id);
        if (resultEntity.isPresent()) {
            ArmyListDTO result = armyListMapper.toDto(resultEntity.get());
            result.setTotalPoint(this.computeDTOPoints(result));
            return Optional.of(result);
        }
        return resultEntity.map(armyListMapper::toDto);
    }

    /**
     * Delete the armyList by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ArmyList : {}", id);
        armyListRepository.deleteById(id);
//        armyListSearchRepository.deleteById(id);
    }

  /*  @Override
    public Page<ArmyListDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArmyLists for query {}", query);
        return armyListSearchRepository.search(queryStringQuery(query), pageable)
            .map(armyListMapper::toDto);
    }*/

	@Override
	public Optional<List<ArmyListDTO>> findAllByUser(String id) {
		Optional<User> optionalUser = this.userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return Optional.of(optionalUser.get().getArmyLists().stream().map(o -> armyListMapper.toDto(o)).collect(Collectors.toList()));
		}
		return null;
	}

	@Override
    @Transactional
	public ArmyListDTO addUnit(String id, @Valid UnitMapDTO unitDTO) {
		Optional<ArmyList> armyList = this.armyListRepository.findById(id);
		UnitMap unit = this.unitMapRepository.save(this.unitMapMapper.toEntity(unitDTO));
		if (armyList.isPresent()) {
		    ArmyList properArmyList = armyList.get();
            properArmyList = properArmyList.addUnitMap(unit);
            properArmyList.setTotalPoint(this.computeEntityPoints(armyList.get()));
			ArmyList result = this.armyListRepository.save(properArmyList);
			return this.armyListMapper.toDto(result);
		}
		return null;
	}

	@Override
    @Transactional
    public ArmyListDTO removeUnit(String id, @Valid UnitMapDTO unitDTO) {
        Optional<ArmyList> armyList = this.armyListRepository.findById(id);
        UnitMap unit = this.unitMapMapper.toEntity(unitDTO);
        if (armyList.isPresent()) {
            ArmyList properArmyList = armyList.get();
            properArmyList = properArmyList.removeUnitMap(unit);
            this.unitMapRepository.delete(unit);
            properArmyList.setTotalPoint(this.computeEntityPoints(properArmyList));
            ArmyList result = this.armyListRepository.save(properArmyList);
            return this.armyListMapper.toDto(result);
        }
        return null;
    }
}
