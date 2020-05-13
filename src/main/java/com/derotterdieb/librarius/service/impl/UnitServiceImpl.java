package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.service.UnitService;
import com.derotterdieb.librarius.domain.Unit;
import com.derotterdieb.librarius.repository.UnitRepository;
import com.derotterdieb.librarius.service.dto.UnitDTO;
import com.derotterdieb.librarius.service.mapper.UnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Unit}.
 */
@Service
public class UnitServiceImpl implements UnitService {

    private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);

    private final UnitRepository unitRepository;

    private final UnitMapper unitMapper;

//    private final UnitSearchRepository unitSearchRepository;

    public UnitServiceImpl(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
//        this.unitSearchRepository = unitSearchRepository;
    }

    /**
     * Save a unit.
     *
     * @param unitDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnitDTO save(UnitDTO unitDTO) {
        log.debug("Request to save Unit : {}", unitDTO);
        Unit unit = unitMapper.toEntity(unitDTO);
        unit = this.computeTotalUnitPoint(unit);
        unit = unitRepository.save(unit);
        UnitDTO result = unitMapper.toDto(unit);
//        unitSearchRepository.save(unit);
        return result;
    }

    private Unit computeTotalUnitPoint(Unit unit) {
    	unit.setTotalPoint(unit.getBasePoint());
    	return unit;
	}

	/**
     * Get all the units.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<UnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Units");
        return unitRepository.findAll(pageable)
            .map(unitMapper::toDto);
    }

    /**
     * Get all the units with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UnitDTO> findAllWithEagerRelationships(Pageable pageable) {
        return unitRepository.findAllWithEagerRelationships(pageable).map(unitMapper::toDto);
    }

    /**
     * Get one unit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<UnitDTO> findOne(String id) {
        log.debug("Request to get Unit : {}", id);
        return unitRepository.findOneWithEagerRelationships(id)
            .map(unitMapper::toDto);
    }

    /**
     * Delete the unit by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Unit : {}", id);
        unitRepository.deleteById(id);
//        unitSearchRepository.deleteById(id);
    }

    /**
     * Search for the unit corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
 /*   @Override
    public Page<UnitDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Units for query {}", query);
        return unitSearchRepository.search(queryStringQuery(query), pageable)
            .map(unitMapper::toDto);
    }*/
}
