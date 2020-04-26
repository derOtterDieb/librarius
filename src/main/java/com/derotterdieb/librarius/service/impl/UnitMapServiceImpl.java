package com.derotterdieb.librarius.service.impl;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.derotterdieb.librarius.domain.UnitMap;
import com.derotterdieb.librarius.repository.UnitMapRepository;
import com.derotterdieb.librarius.repository.search.UnitMapSearchRepository;
import com.derotterdieb.librarius.service.UnitMapService;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;
import com.derotterdieb.librarius.service.mapper.UnitMapMapper;

@Service
public class UnitMapServiceImpl implements UnitMapService {
	
	private final Logger log = LoggerFactory.getLogger(UnitServiceImpl.class);
	
	private final UnitMapRepository unitMapRepository;
	
	private final UnitMapMapper unitMapMapper;
	
	private final UnitMapSearchRepository unitMapSearchRepository;
	
	public UnitMapServiceImpl(
		UnitMapRepository unitMapRepository,
		UnitMapMapper unitMapMapper,
		UnitMapSearchRepository unitMapSearchRepository
	) {
		this.unitMapRepository = unitMapRepository;
		this.unitMapMapper = unitMapMapper;
		this.unitMapSearchRepository = unitMapSearchRepository;
	}

	@Override
	public UnitMapDTO save(UnitMapDTO UnitMapDTO) {
		log.debug("Request to save UnitMap : {}", UnitMapDTO);
        UnitMap unitMap = unitMapMapper.toEntity(UnitMapDTO);
        unitMap = unitMapRepository.save(unitMap);
        UnitMapDTO result = unitMapMapper.toDto(unitMap);
        unitMapSearchRepository.save(unitMap);
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
        unitMapSearchRepository.deleteById(id);
	}

	@Override
	public Page<UnitMapDTO> search(String query, Pageable pageable) {
		log.debug("Request to search for a page of Unitmapss for query {}", query);
        return unitMapSearchRepository.search(queryStringQuery(query), pageable)
            .map(unitMapMapper::toDto);
	}

}
