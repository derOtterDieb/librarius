package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.service.GearService;
import com.derotterdieb.librarius.domain.Gear;
import com.derotterdieb.librarius.repository.GearRepository;
import com.derotterdieb.librarius.service.dto.GearDTO;
import com.derotterdieb.librarius.service.mapper.GearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing {@link Gear}.
 */
@Service
public class GearServiceImpl implements GearService {

    private final Logger log = LoggerFactory.getLogger(GearServiceImpl.class);

    private final GearRepository gearRepository;

    private final GearMapper gearMapper;

//    private final GearSearchRepository gearSearchRepository;

    public GearServiceImpl(GearRepository gearRepository, GearMapper gearMapper) {
        this.gearRepository = gearRepository;
        this.gearMapper = gearMapper;
//        this.gearSearchRepository = gearSearchRepository;
    }

    /**
     * Save a gear.
     *
     * @param gearDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GearDTO save(GearDTO gearDTO) {
        log.debug("Request to save Gear : {}", gearDTO);
        Gear gear = gearMapper.toEntity(gearDTO);
        gear = gearRepository.save(gear);
        GearDTO result = gearMapper.toDto(gear);
//        gearSearchRepository.save(gear);
        return result;
    }

    /**
     * Get all the gears.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<GearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Gears");
        return gearRepository.findAll(pageable)
            .map(gearMapper::toDto);
    }

    /**
     * Get one gear by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<GearDTO> findOne(String id) {
        log.debug("Request to get Gear : {}", id);
        return gearRepository.findById(id)
            .map(gearMapper::toDto);
    }

    /**
     * Delete the gear by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Gear : {}", id);
        gearRepository.deleteById(id);
//        gearSearchRepository.deleteById(id);
    }

/*    @Override
    public Page<GearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Gears for query {}", query);
        return gearSearchRepository.search(queryStringQuery(query), pageable)
            .map(gearMapper::toDto);
    }*/

    @Override
    public List<GearDTO> findAllByName(String name) {
        log.debug("Request to search for a page of Gears for query {}", name);
        List<GearDTO> result = new ArrayList<>();
        List<Gear> allGear = gearRepository.findAll();
        for (Gear gear : allGear) {
            if (gear.getGearName().toLowerCase().contains(name.toLowerCase())) {
                result.add(gearMapper.toDto(gear));
            }
        }
        return result;
    }
}
