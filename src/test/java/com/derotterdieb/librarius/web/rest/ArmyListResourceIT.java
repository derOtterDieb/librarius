package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.LibrariusApp;
import com.derotterdieb.librarius.domain.ArmyList;
import com.derotterdieb.librarius.repository.ArmyListRepository;
import com.derotterdieb.librarius.repository.search.ArmyListSearchRepository;
import com.derotterdieb.librarius.service.ArmyListService;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;
import com.derotterdieb.librarius.service.mapper.ArmyListMapper;

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

import java.util.ArrayList;
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
 * Integration tests for the {@link ArmyListResource} REST controller.
 */
@SpringBootTest(classes = LibrariusApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArmyListResourceIT {

    private static final String DEFAULT_LIST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LIST_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_POINT = 1;
    private static final Integer UPDATED_TOTAL_POINT = 2;

    @Autowired
    private ArmyListRepository armyListRepository;

    @Mock
    private ArmyListRepository armyListRepositoryMock;

    @Autowired
    private ArmyListMapper armyListMapper;

    @Mock
    private ArmyListService armyListServiceMock;

    @Autowired
    private ArmyListService armyListService;

    /**
     * This repository is mocked in the com.derotterdieb.librarius.repository.search test package.
     *
     * @see com.derotterdieb.librarius.repository.search.ArmyListSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArmyListSearchRepository mockArmyListSearchRepository;

    @Autowired
    private MockMvc restArmyListMockMvc;

    private ArmyList armyList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmyList createEntity() {
        ArmyList armyList = new ArmyList()
            .listName(DEFAULT_LIST_NAME)
            .totalPoint(DEFAULT_TOTAL_POINT);
        return armyList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArmyList createUpdatedEntity() {
        ArmyList armyList = new ArmyList()
            .listName(UPDATED_LIST_NAME)
            .totalPoint(UPDATED_TOTAL_POINT);
        return armyList;
    }

    @BeforeEach
    public void initTest() {
        armyListRepository.deleteAll();
        armyList = createEntity();
    }

    @Test
    public void createArmyList() throws Exception {
        int databaseSizeBeforeCreate = armyListRepository.findAll().size();

        // Create the ArmyList
        ArmyListDTO armyListDTO = armyListMapper.toDto(armyList);
        restArmyListMockMvc.perform(post("/api/army-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armyListDTO)))
            .andExpect(status().isCreated());

        // Validate the ArmyList in the database
        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeCreate + 1);
        ArmyList testArmyList = armyListList.get(armyListList.size() - 1);
        assertThat(testArmyList.getListName()).isEqualTo(DEFAULT_LIST_NAME);
        assertThat(testArmyList.getTotalPoint()).isEqualTo(DEFAULT_TOTAL_POINT);

        // Validate the ArmyList in Elasticsearch
        verify(mockArmyListSearchRepository, times(1)).save(testArmyList);
    }

    @Test
    public void createArmyListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = armyListRepository.findAll().size();

        // Create the ArmyList with an existing ID
        armyList.setId("existing_id");
        ArmyListDTO armyListDTO = armyListMapper.toDto(armyList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArmyListMockMvc.perform(post("/api/army-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armyListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmyList in the database
        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArmyList in Elasticsearch
        verify(mockArmyListSearchRepository, times(0)).save(armyList);
    }


    @Test
    public void checkListNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = armyListRepository.findAll().size();
        // set the field null
        armyList.setListName(null);

        // Create the ArmyList, which fails.
        ArmyListDTO armyListDTO = armyListMapper.toDto(armyList);

        restArmyListMockMvc.perform(post("/api/army-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armyListDTO)))
            .andExpect(status().isBadRequest());

        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllArmyLists() throws Exception {
        // Initialize the database
        armyListRepository.save(armyList);

        // Get all the armyListList
        restArmyListMockMvc.perform(get("/api/army-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armyList.getId())))
            .andExpect(jsonPath("$.[*].listName").value(hasItem(DEFAULT_LIST_NAME)))
            .andExpect(jsonPath("$.[*].totalPoint").value(hasItem(DEFAULT_TOTAL_POINT)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArmyListsWithEagerRelationshipsIsEnabled() throws Exception {
        when(armyListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArmyListMockMvc.perform(get("/api/army-lists?eagerload=true"))
            .andExpect(status().isOk());

        verify(armyListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArmyListsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(armyListServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restArmyListMockMvc.perform(get("/api/army-lists?eagerload=true"))
            .andExpect(status().isOk());

        verify(armyListServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getArmyList() throws Exception {
        // Initialize the database
        armyListRepository.save(armyList);

        // Get the armyList
        restArmyListMockMvc.perform(get("/api/army-lists/{id}", armyList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(armyList.getId()))
            .andExpect(jsonPath("$.listName").value(DEFAULT_LIST_NAME))
            .andExpect(jsonPath("$.totalPoint").value(DEFAULT_TOTAL_POINT));
    }

    @Test
    public void getNonExistingArmyList() throws Exception {
        // Get the armyList
        restArmyListMockMvc.perform(get("/api/army-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateArmyList() throws Exception {
        // Initialize the database
        armyListRepository.save(armyList);

        int databaseSizeBeforeUpdate = armyListRepository.findAll().size();

        // Update the armyList
        ArmyList updatedArmyList = armyListRepository.findById(armyList.getId()).get();
        updatedArmyList
            .listName(UPDATED_LIST_NAME)
            .totalPoint(UPDATED_TOTAL_POINT);
        ArmyListDTO armyListDTO = armyListMapper.toDto(updatedArmyList);

        restArmyListMockMvc.perform(put("/api/army-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armyListDTO)))
            .andExpect(status().isOk());

        // Validate the ArmyList in the database
        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeUpdate);
        ArmyList testArmyList = armyListList.get(armyListList.size() - 1);
        assertThat(testArmyList.getListName()).isEqualTo(UPDATED_LIST_NAME);
        assertThat(testArmyList.getTotalPoint()).isEqualTo(UPDATED_TOTAL_POINT);

        // Validate the ArmyList in Elasticsearch
        verify(mockArmyListSearchRepository, times(1)).save(testArmyList);
    }

    @Test
    public void updateNonExistingArmyList() throws Exception {
        int databaseSizeBeforeUpdate = armyListRepository.findAll().size();

        // Create the ArmyList
        ArmyListDTO armyListDTO = armyListMapper.toDto(armyList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArmyListMockMvc.perform(put("/api/army-lists").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(armyListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArmyList in the database
        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArmyList in Elasticsearch
        verify(mockArmyListSearchRepository, times(0)).save(armyList);
    }

    @Test
    public void deleteArmyList() throws Exception {
        // Initialize the database
        armyListRepository.save(armyList);

        int databaseSizeBeforeDelete = armyListRepository.findAll().size();

        // Delete the armyList
        restArmyListMockMvc.perform(delete("/api/army-lists/{id}", armyList.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArmyList> armyListList = armyListRepository.findAll();
        assertThat(armyListList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArmyList in Elasticsearch
        verify(mockArmyListSearchRepository, times(1)).deleteById(armyList.getId());
    }

    @Test
    public void searchArmyList() throws Exception {
        // Initialize the database
        armyListRepository.save(armyList);
        when(mockArmyListSearchRepository.search(queryStringQuery("id:" + armyList.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(armyList), PageRequest.of(0, 1), 1));
        // Search the armyList
        restArmyListMockMvc.perform(get("/api/_search/army-lists?query=id:" + armyList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(armyList.getId())))
            .andExpect(jsonPath("$.[*].listName").value(hasItem(DEFAULT_LIST_NAME)))
            .andExpect(jsonPath("$.[*].totalPoint").value(hasItem(DEFAULT_TOTAL_POINT)));
    }
}
