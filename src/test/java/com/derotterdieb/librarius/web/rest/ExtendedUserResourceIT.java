package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.LibrariusApp;
import com.derotterdieb.librarius.domain.ExtendedUser;
import com.derotterdieb.librarius.repository.ExtendedUserRepository;
import com.derotterdieb.librarius.repository.search.ExtendedUserSearchRepository;
import com.derotterdieb.librarius.service.ExtendedUserService;
import com.derotterdieb.librarius.service.dto.ExtendedUserDTO;
import com.derotterdieb.librarius.service.mapper.ExtendedUserMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ExtendedUserResource} REST controller.
 */
@SpringBootTest(classes = LibrariusApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ExtendedUserResourceIT {

    private static final String DEFAULT_PSEUDO = "AAAAAAAAAA";
    private static final String UPDATED_PSEUDO = "BBBBBBBBBB";

    @Autowired
    private ExtendedUserRepository extendedUserRepository;

    @Autowired
    private ExtendedUserMapper extendedUserMapper;

    @Autowired
    private ExtendedUserService extendedUserService;

    /**
     * This repository is mocked in the com.derotterdieb.librarius.repository.search test package.
     *
     * @see com.derotterdieb.librarius.repository.search.ExtendedUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private ExtendedUserSearchRepository mockExtendedUserSearchRepository;

    @Autowired
    private MockMvc restExtendedUserMockMvc;

    private ExtendedUser extendedUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendedUser createEntity() {
        ExtendedUser extendedUser = new ExtendedUser()
            .pseudo(DEFAULT_PSEUDO);
        return extendedUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExtendedUser createUpdatedEntity() {
        ExtendedUser extendedUser = new ExtendedUser()
            .pseudo(UPDATED_PSEUDO);
        return extendedUser;
    }

    @BeforeEach
    public void initTest() {
        extendedUserRepository.deleteAll();
        extendedUser = createEntity();
    }

    @Test
    public void createExtendedUser() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);
        restExtendedUserMockMvc.perform(post("/api/extended-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isCreated());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate + 1);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getPseudo()).isEqualTo(DEFAULT_PSEUDO);

        // Validate the ExtendedUser in Elasticsearch
        verify(mockExtendedUserSearchRepository, times(1)).save(testExtendedUser);
    }

    @Test
    public void createExtendedUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser with an existing ID
        extendedUser.setId("existing_id");
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExtendedUserMockMvc.perform(post("/api/extended-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the ExtendedUser in Elasticsearch
        verify(mockExtendedUserSearchRepository, times(0)).save(extendedUser);
    }


    @Test
    public void checkPseudoIsRequired() throws Exception {
        int databaseSizeBeforeTest = extendedUserRepository.findAll().size();
        // set the field null
        extendedUser.setPseudo(null);

        // Create the ExtendedUser, which fails.
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);

        restExtendedUserMockMvc.perform(post("/api/extended-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllExtendedUsers() throws Exception {
        // Initialize the database
        extendedUserRepository.save(extendedUser);

        // Get all the extendedUserList
        restExtendedUserMockMvc.perform(get("/api/extended-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId())))
            .andExpect(jsonPath("$.[*].pseudo").value(hasItem(DEFAULT_PSEUDO)));
    }
    
    @Test
    public void getExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.save(extendedUser);

        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", extendedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(extendedUser.getId()))
            .andExpect(jsonPath("$.pseudo").value(DEFAULT_PSEUDO));
    }

    @Test
    public void getNonExistingExtendedUser() throws Exception {
        // Get the extendedUser
        restExtendedUserMockMvc.perform(get("/api/extended-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.save(extendedUser);

        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Update the extendedUser
        ExtendedUser updatedExtendedUser = extendedUserRepository.findById(extendedUser.getId()).get();
        updatedExtendedUser
            .pseudo(UPDATED_PSEUDO);
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(updatedExtendedUser);

        restExtendedUserMockMvc.perform(put("/api/extended-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isOk());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);
        ExtendedUser testExtendedUser = extendedUserList.get(extendedUserList.size() - 1);
        assertThat(testExtendedUser.getPseudo()).isEqualTo(UPDATED_PSEUDO);

        // Validate the ExtendedUser in Elasticsearch
        verify(mockExtendedUserSearchRepository, times(1)).save(testExtendedUser);
    }

    @Test
    public void updateNonExistingExtendedUser() throws Exception {
        int databaseSizeBeforeUpdate = extendedUserRepository.findAll().size();

        // Create the ExtendedUser
        ExtendedUserDTO extendedUserDTO = extendedUserMapper.toDto(extendedUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExtendedUserMockMvc.perform(put("/api/extended-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(extendedUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExtendedUser in the database
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ExtendedUser in Elasticsearch
        verify(mockExtendedUserSearchRepository, times(0)).save(extendedUser);
    }

    @Test
    public void deleteExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.save(extendedUser);

        int databaseSizeBeforeDelete = extendedUserRepository.findAll().size();

        // Delete the extendedUser
        restExtendedUserMockMvc.perform(delete("/api/extended-users/{id}", extendedUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExtendedUser> extendedUserList = extendedUserRepository.findAll();
        assertThat(extendedUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ExtendedUser in Elasticsearch
        verify(mockExtendedUserSearchRepository, times(1)).deleteById(extendedUser.getId());
    }

    @Test
    public void searchExtendedUser() throws Exception {
        // Initialize the database
        extendedUserRepository.save(extendedUser);
        when(mockExtendedUserSearchRepository.search(queryStringQuery("id:" + extendedUser.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(extendedUser), PageRequest.of(0, 1), 1));
        // Search the extendedUser
        restExtendedUserMockMvc.perform(get("/api/_search/extended-users?query=id:" + extendedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(extendedUser.getId())))
            .andExpect(jsonPath("$.[*].pseudo").value(hasItem(DEFAULT_PSEUDO)));
    }
}
