package com.derotterdieb.librarius.service.impl;

import com.derotterdieb.librarius.service.ExtendedUserService;
import com.derotterdieb.librarius.domain.ExtendedUser;
import com.derotterdieb.librarius.repository.ExtendedUserRepository;
import com.derotterdieb.librarius.service.dto.ExtendedUserDTO;
import com.derotterdieb.librarius.service.mapper.ExtendedUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service Implementation for managing {@link ExtendedUser}.
 */
@Service
public class ExtendedUserServiceImpl implements ExtendedUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendedUserServiceImpl.class);

    private final ExtendedUserRepository extendedUserRepository;

    private final ExtendedUserMapper extendedUserMapper;

//    private final ExtendedUserSearchRepository extendedUserSearchRepository;

    public ExtendedUserServiceImpl(ExtendedUserRepository extendedUserRepository, ExtendedUserMapper extendedUserMapper) {
        this.extendedUserRepository = extendedUserRepository;
        this.extendedUserMapper = extendedUserMapper;
//        this.extendedUserSearchRepository = extendedUserSearchRepository;
    }

    /**
     * Save a extendedUser.
     *
     * @param extendedUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ExtendedUserDTO save(ExtendedUserDTO extendedUserDTO) {
        log.debug("Request to save ExtendedUser : {}", extendedUserDTO);
        ExtendedUser extendedUser = extendedUserMapper.toEntity(extendedUserDTO);
        extendedUser = extendedUserRepository.save(extendedUser);
        ExtendedUserDTO result = extendedUserMapper.toDto(extendedUser);
//        extendedUserSearchRepository.save(extendedUser);
        return result;
    }

    /**
     * Get all the extendedUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<ExtendedUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ExtendedUsers");
        return extendedUserRepository.findAll(pageable)
            .map(extendedUserMapper::toDto);
    }

    /**
     * Get one extendedUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<ExtendedUserDTO> findOne(String id) {
        log.debug("Request to get ExtendedUser : {}", id);
        return extendedUserRepository.findById(id)
            .map(extendedUserMapper::toDto);
    }

    /**
     * Delete the extendedUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete ExtendedUser : {}", id);
        extendedUserRepository.deleteById(id);
//        extendedUserSearchRepository.deleteById(id);
    }

    /*@Override
    public Page<ExtendedUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExtendedUsers for query {}", query);
        return extendedUserSearchRepository.search(queryStringQuery(query), pageable)
            .map(extendedUserMapper::toDto);
    }*/
}
