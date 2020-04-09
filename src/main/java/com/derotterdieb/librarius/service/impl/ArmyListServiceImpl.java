package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.service.ArmyListService;
import com.derotterdieb.librarius.domain.ArmyList;
import com.derotterdieb.librarius.domain.User;
import com.derotterdieb.librarius.repository.ArmyListRepository;
import com.derotterdieb.librarius.repository.UserRepository;
import com.derotterdieb.librarius.repository.search.ArmyListSearchRepository;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;
import com.derotterdieb.librarius.service.mapper.ArmyListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ArmyList}.
 */
@Service
public class ArmyListServiceImpl implements ArmyListService {

    private final Logger log = LoggerFactory.getLogger(ArmyListServiceImpl.class);

    private final ArmyListRepository armyListRepository;
    
    private final UserRepository userRepository;

    private final ArmyListMapper armyListMapper;

    private final ArmyListSearchRepository armyListSearchRepository;

    public ArmyListServiceImpl(ArmyListRepository armyListRepository, ArmyListMapper armyListMapper,
    		ArmyListSearchRepository armyListSearchRepository, UserRepository userRepository) {
        this.armyListRepository = armyListRepository;
        this.armyListMapper = armyListMapper;
        this.armyListSearchRepository = armyListSearchRepository;
        this.userRepository = userRepository;
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
        ArmyList armyList = armyListMapper.toEntity(armyListDTO);
        armyList = armyListRepository.save(armyList);
        ArmyListDTO result = armyListMapper.toDto(armyList);
        armyListSearchRepository.save(armyList);
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
        return armyListRepository.findOneWithEagerRelationships(id)
            .map(armyListMapper::toDto);
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
        armyListSearchRepository.deleteById(id);
    }

    /**
     * Search for the armyList corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ArmyListDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ArmyLists for query {}", query);
        return armyListSearchRepository.search(queryStringQuery(query), pageable)
            .map(armyListMapper::toDto);
    }

	@Override
	public Optional<List<ArmyListDTO>> findAllByUser(String id) {
		Optional<User> optionalUser = this.userRepository.findById(id);
		if (optionalUser.isPresent()) {
			return Optional.of(optionalUser.get().getArmyLists().stream().map(o -> armyListMapper.toDto(o)).collect(Collectors.toList()));
		}
		return null;
	}
}
