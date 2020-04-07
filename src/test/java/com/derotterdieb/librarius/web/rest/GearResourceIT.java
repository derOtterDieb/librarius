package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.LibrariusApp;
import com.derotterdieb.librarius.domain.Gear;
import com.derotterdieb.librarius.repository.GearRepository;
import com.derotterdieb.librarius.repository.search.GearSearchRepository;
import com.derotterdieb.librarius.service.GearService;
import com.derotterdieb.librarius.service.dto.GearDTO;
import com.derotterdieb.librarius.service.mapper.GearMapper;

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
 * Integration tests for the {@link GearResource} REST controller.
 */
@SpringBootTest(classes = LibrariusApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class GearResourceIT {

    private static final String DEFAULT_GEAR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_GEAR_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT_VALUE = 1;
    private static final Integer UPDATED_POINT_VALUE = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_F = "AAAAAAAAAA";
    private static final String UPDATED_F = "BBBBBBBBBB";

    private static final String DEFAULT_RANGE = "AAAAAAAAAA";
    private static final String UPDATED_RANGE = "BBBBBBBBBB";

    private static final String DEFAULT_PA = "AAAAAAAAAA";
    private static final String UPDATED_PA = "BBBBBBBBBB";

    private static final String DEFAULT_D = "AAAAAAAAAA";
    private static final String UPDATED_D = "BBBBBBBBBB";

    @Autowired
    private GearRepository gearRepository;

    @Autowired
    private GearMapper gearMapper;

    @Autowired
    private GearService gearService;

    /**
     * This repository is mocked in the com.derotterdieb.librarius.repository.search test package.
     *
     * @see com.derotterdieb.librarius.repository.search.GearSearchRepositoryMockConfiguration
     */
    @Autowired
    private GearSearchRepository mockGearSearchRepository;

    @Autowired
    private MockMvc restGearMockMvc;

    private Gear gear;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gear createEntity() {
        Gear gear = new Gear()
            .gearName(DEFAULT_GEAR_NAME)
            .pointValue(DEFAULT_POINT_VALUE)
            .type(DEFAULT_TYPE)
            .f(DEFAULT_F)
            .range(DEFAULT_RANGE)
            .pa(DEFAULT_PA)
            .d(DEFAULT_D);
        return gear;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gear createUpdatedEntity() {
        Gear gear = new Gear()
            .gearName(UPDATED_GEAR_NAME)
            .pointValue(UPDATED_POINT_VALUE)
            .type(UPDATED_TYPE)
            .f(UPDATED_F)
            .range(UPDATED_RANGE)
            .pa(UPDATED_PA)
            .d(UPDATED_D);
        return gear;
    }

    @BeforeEach
    public void initTest() {
        gearRepository.deleteAll();
        gear = createEntity();
    }

    @Test
    public void createGear() throws Exception {
        int databaseSizeBeforeCreate = gearRepository.findAll().size();

        // Create the Gear
        GearDTO gearDTO = gearMapper.toDto(gear);
        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isCreated());

        // Validate the Gear in the database
        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeCreate + 1);
        Gear testGear = gearList.get(gearList.size() - 1);
        assertThat(testGear.getGearName()).isEqualTo(DEFAULT_GEAR_NAME);
        assertThat(testGear.getPointValue()).isEqualTo(DEFAULT_POINT_VALUE);
        assertThat(testGear.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGear.getF()).isEqualTo(DEFAULT_F);
        assertThat(testGear.getRange()).isEqualTo(DEFAULT_RANGE);
        assertThat(testGear.getPa()).isEqualTo(DEFAULT_PA);
        assertThat(testGear.getD()).isEqualTo(DEFAULT_D);

        // Validate the Gear in Elasticsearch
        verify(mockGearSearchRepository, times(1)).save(testGear);
    }

    @Test
    public void createGearWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gearRepository.findAll().size();

        // Create the Gear with an existing ID
        gear.setId("existing_id");
        GearDTO gearDTO = gearMapper.toDto(gear);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gear in the database
        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeCreate);

        // Validate the Gear in Elasticsearch
        verify(mockGearSearchRepository, times(0)).save(gear);
    }


    @Test
    public void checkGearNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setGearName(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPointValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setPointValue(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setType(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setF(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setRange(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPaIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setPa(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDIsRequired() throws Exception {
        int databaseSizeBeforeTest = gearRepository.findAll().size();
        // set the field null
        gear.setD(null);

        // Create the Gear, which fails.
        GearDTO gearDTO = gearMapper.toDto(gear);

        restGearMockMvc.perform(post("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGears() throws Exception {
        // Initialize the database
        gearRepository.save(gear);

        // Get all the gearList
        restGearMockMvc.perform(get("/api/gears?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gear.getId())))
            .andExpect(jsonPath("$.[*].gearName").value(hasItem(DEFAULT_GEAR_NAME)))
            .andExpect(jsonPath("$.[*].pointValue").value(hasItem(DEFAULT_POINT_VALUE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].f").value(hasItem(DEFAULT_F)))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE)))
            .andExpect(jsonPath("$.[*].pa").value(hasItem(DEFAULT_PA)))
            .andExpect(jsonPath("$.[*].d").value(hasItem(DEFAULT_D)));
    }
    
    @Test
    public void getGear() throws Exception {
        // Initialize the database
        gearRepository.save(gear);

        // Get the gear
        restGearMockMvc.perform(get("/api/gears/{id}", gear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gear.getId()))
            .andExpect(jsonPath("$.gearName").value(DEFAULT_GEAR_NAME))
            .andExpect(jsonPath("$.pointValue").value(DEFAULT_POINT_VALUE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.f").value(DEFAULT_F))
            .andExpect(jsonPath("$.range").value(DEFAULT_RANGE))
            .andExpect(jsonPath("$.pa").value(DEFAULT_PA))
            .andExpect(jsonPath("$.d").value(DEFAULT_D));
    }

    @Test
    public void getNonExistingGear() throws Exception {
        // Get the gear
        restGearMockMvc.perform(get("/api/gears/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGear() throws Exception {
        // Initialize the database
        gearRepository.save(gear);

        int databaseSizeBeforeUpdate = gearRepository.findAll().size();

        // Update the gear
        Gear updatedGear = gearRepository.findById(gear.getId()).get();
        updatedGear
            .gearName(UPDATED_GEAR_NAME)
            .pointValue(UPDATED_POINT_VALUE)
            .type(UPDATED_TYPE)
            .f(UPDATED_F)
            .range(UPDATED_RANGE)
            .pa(UPDATED_PA)
            .d(UPDATED_D);
        GearDTO gearDTO = gearMapper.toDto(updatedGear);

        restGearMockMvc.perform(put("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isOk());

        // Validate the Gear in the database
        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeUpdate);
        Gear testGear = gearList.get(gearList.size() - 1);
        assertThat(testGear.getGearName()).isEqualTo(UPDATED_GEAR_NAME);
        assertThat(testGear.getPointValue()).isEqualTo(UPDATED_POINT_VALUE);
        assertThat(testGear.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGear.getF()).isEqualTo(UPDATED_F);
        assertThat(testGear.getRange()).isEqualTo(UPDATED_RANGE);
        assertThat(testGear.getPa()).isEqualTo(UPDATED_PA);
        assertThat(testGear.getD()).isEqualTo(UPDATED_D);

        // Validate the Gear in Elasticsearch
        verify(mockGearSearchRepository, times(1)).save(testGear);
    }

    @Test
    public void updateNonExistingGear() throws Exception {
        int databaseSizeBeforeUpdate = gearRepository.findAll().size();

        // Create the Gear
        GearDTO gearDTO = gearMapper.toDto(gear);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGearMockMvc.perform(put("/api/gears").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gearDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Gear in the database
        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Gear in Elasticsearch
        verify(mockGearSearchRepository, times(0)).save(gear);
    }

    @Test
    public void deleteGear() throws Exception {
        // Initialize the database
        gearRepository.save(gear);

        int databaseSizeBeforeDelete = gearRepository.findAll().size();

        // Delete the gear
        restGearMockMvc.perform(delete("/api/gears/{id}", gear.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gear> gearList = gearRepository.findAll();
        assertThat(gearList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Gear in Elasticsearch
        verify(mockGearSearchRepository, times(1)).deleteById(gear.getId());
    }

    @Test
    public void searchGear() throws Exception {
        // Initialize the database
        gearRepository.save(gear);
        when(mockGearSearchRepository.search(queryStringQuery("id:" + gear.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gear), PageRequest.of(0, 1), 1));
        // Search the gear
        restGearMockMvc.perform(get("/api/_search/gears?query=id:" + gear.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gear.getId())))
            .andExpect(jsonPath("$.[*].gearName").value(hasItem(DEFAULT_GEAR_NAME)))
            .andExpect(jsonPath("$.[*].pointValue").value(hasItem(DEFAULT_POINT_VALUE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].f").value(hasItem(DEFAULT_F)))
            .andExpect(jsonPath("$.[*].range").value(hasItem(DEFAULT_RANGE)))
            .andExpect(jsonPath("$.[*].pa").value(hasItem(DEFAULT_PA)))
            .andExpect(jsonPath("$.[*].d").value(hasItem(DEFAULT_D)));
    }
}
