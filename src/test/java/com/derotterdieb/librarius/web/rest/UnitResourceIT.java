package com.derotterdieb.librarius.web.rest;

import com.derotterdieb.librarius.LibrariusApp;
import com.derotterdieb.librarius.domain.Unit;
import com.derotterdieb.librarius.repository.UnitRepository;
import com.derotterdieb.librarius.repository.search.UnitSearchRepository;
import com.derotterdieb.librarius.service.UnitService;
import com.derotterdieb.librarius.service.dto.UnitDTO;
import com.derotterdieb.librarius.service.mapper.UnitMapper;

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
 * Integration tests for the {@link UnitResource} REST controller.
 */
@SpringBootTest(classes = LibrariusApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UnitResourceIT {

    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_BASE_POINT = 1;
    private static final Integer UPDATED_BASE_POINT = 2;

    private static final Integer DEFAULT_TOTAL_POINT = 1;
    private static final Integer UPDATED_TOTAL_POINT = 2;

    private static final String DEFAULT_M = "AAAAAAAAAA";
    private static final String UPDATED_M = "BBBBBBBBBB";

    private static final String DEFAULT_CC = "AAAAAAAAAA";
    private static final String UPDATED_CC = "BBBBBBBBBB";

    private static final String DEFAULT_CT = "AAAAAAAAAA";
    private static final String UPDATED_CT = "BBBBBBBBBB";

    private static final String DEFAULT_F = "AAAAAAAAAA";
    private static final String UPDATED_F = "BBBBBBBBBB";

    private static final String DEFAULT_E = "AAAAAAAAAA";
    private static final String UPDATED_E = "BBBBBBBBBB";

    private static final String DEFAULT_PV = "AAAAAAAAAA";
    private static final String UPDATED_PV = "BBBBBBBBBB";

    private static final String DEFAULT_A = "AAAAAAAAAA";
    private static final String UPDATED_A = "BBBBBBBBBB";

    private static final String DEFAULT_CD = "AAAAAAAAAA";
    private static final String UPDATED_CD = "BBBBBBBBBB";

    private static final String DEFAULT_SV = "AAAAAAAAAA";
    private static final String UPDATED_SV = "BBBBBBBBBB";

    @Autowired
    private UnitRepository unitRepository;

    @Mock
    private UnitRepository unitRepositoryMock;

    @Autowired
    private UnitMapper unitMapper;

    @Mock
    private UnitService unitServiceMock;

    @Autowired
    private UnitService unitService;

    /**
     * This repository is mocked in the com.derotterdieb.librarius.repository.search test package.
     *
     * @see com.derotterdieb.librarius.repository.search.UnitSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnitSearchRepository mockUnitSearchRepository;

    @Autowired
    private MockMvc restUnitMockMvc;

    private Unit unit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createEntity() {
        Unit unit = new Unit()
            .unitName(DEFAULT_UNIT_NAME)
            .basePoint(DEFAULT_BASE_POINT)
            .totalPoint(DEFAULT_TOTAL_POINT)
            .m(DEFAULT_M)
            .cc(DEFAULT_CC)
            .ct(DEFAULT_CT)
            .f(DEFAULT_F)
            .e(DEFAULT_E)
            .pv(DEFAULT_PV)
            .a(DEFAULT_A)
            .cd(DEFAULT_CD)
            .sv(DEFAULT_SV);
        return unit;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Unit createUpdatedEntity() {
        Unit unit = new Unit()
            .unitName(UPDATED_UNIT_NAME)
            .basePoint(UPDATED_BASE_POINT)
            .totalPoint(UPDATED_TOTAL_POINT)
            .m(UPDATED_M)
            .cc(UPDATED_CC)
            .ct(UPDATED_CT)
            .f(UPDATED_F)
            .e(UPDATED_E)
            .pv(UPDATED_PV)
            .a(UPDATED_A)
            .cd(UPDATED_CD)
            .sv(UPDATED_SV);
        return unit;
    }

    @BeforeEach
    public void initTest() {
        unitRepository.deleteAll();
        unit = createEntity();
    }

    @Test
    public void createUnit() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);
        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isCreated());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate + 1);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
        assertThat(testUnit.getBasePoint()).isEqualTo(DEFAULT_BASE_POINT);
        assertThat(testUnit.getTotalPoint()).isEqualTo(DEFAULT_TOTAL_POINT);
        assertThat(testUnit.getM()).isEqualTo(DEFAULT_M);
        assertThat(testUnit.getCc()).isEqualTo(DEFAULT_CC);
        assertThat(testUnit.getCt()).isEqualTo(DEFAULT_CT);
        assertThat(testUnit.getF()).isEqualTo(DEFAULT_F);
        assertThat(testUnit.getE()).isEqualTo(DEFAULT_E);
        assertThat(testUnit.getPv()).isEqualTo(DEFAULT_PV);
        assertThat(testUnit.getA()).isEqualTo(DEFAULT_A);
        assertThat(testUnit.getCd()).isEqualTo(DEFAULT_CD);
        assertThat(testUnit.getSv()).isEqualTo(DEFAULT_SV);

        // Validate the Unit in Elasticsearch
        verify(mockUnitSearchRepository, times(1)).save(testUnit);
    }

    @Test
    public void createUnitWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitRepository.findAll().size();

        // Create the Unit with an existing ID
        unit.setId("existing_id");
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeCreate);

        // Validate the Unit in Elasticsearch
        verify(mockUnitSearchRepository, times(0)).save(unit);
    }


    @Test
    public void checkUnitNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setUnitName(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkBasePointIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setBasePoint(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkMIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setM(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCcIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setCc(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCtIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setCt(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkFIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setF(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkEIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setE(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPvIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setPv(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkAIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setA(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkCdIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setCd(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSvIsRequired() throws Exception {
        int databaseSizeBeforeTest = unitRepository.findAll().size();
        // set the field null
        unit.setSv(null);

        // Create the Unit, which fails.
        UnitDTO unitDTO = unitMapper.toDto(unit);

        restUnitMockMvc.perform(post("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllUnits() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        // Get all the unitList
        restUnitMockMvc.perform(get("/api/units?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId())))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].basePoint").value(hasItem(DEFAULT_BASE_POINT)))
            .andExpect(jsonPath("$.[*].totalPoint").value(hasItem(DEFAULT_TOTAL_POINT)))
            .andExpect(jsonPath("$.[*].m").value(hasItem(DEFAULT_M)))
            .andExpect(jsonPath("$.[*].cc").value(hasItem(DEFAULT_CC)))
            .andExpect(jsonPath("$.[*].ct").value(hasItem(DEFAULT_CT)))
            .andExpect(jsonPath("$.[*].f").value(hasItem(DEFAULT_F)))
            .andExpect(jsonPath("$.[*].e").value(hasItem(DEFAULT_E)))
            .andExpect(jsonPath("$.[*].pv").value(hasItem(DEFAULT_PV)))
            .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A)))
            .andExpect(jsonPath("$.[*].cd").value(hasItem(DEFAULT_CD)))
            .andExpect(jsonPath("$.[*].sv").value(hasItem(DEFAULT_SV)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUnitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(unitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUnitMockMvc.perform(get("/api/units?eagerload=true"))
            .andExpect(status().isOk());

        verify(unitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUnitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(unitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restUnitMockMvc.perform(get("/api/units?eagerload=true"))
            .andExpect(status().isOk());

        verify(unitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getUnit() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(unit.getId()))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME))
            .andExpect(jsonPath("$.basePoint").value(DEFAULT_BASE_POINT))
            .andExpect(jsonPath("$.totalPoint").value(DEFAULT_TOTAL_POINT))
            .andExpect(jsonPath("$.m").value(DEFAULT_M))
            .andExpect(jsonPath("$.cc").value(DEFAULT_CC))
            .andExpect(jsonPath("$.ct").value(DEFAULT_CT))
            .andExpect(jsonPath("$.f").value(DEFAULT_F))
            .andExpect(jsonPath("$.e").value(DEFAULT_E))
            .andExpect(jsonPath("$.pv").value(DEFAULT_PV))
            .andExpect(jsonPath("$.a").value(DEFAULT_A))
            .andExpect(jsonPath("$.cd").value(DEFAULT_CD))
            .andExpect(jsonPath("$.sv").value(DEFAULT_SV));
    }

    @Test
    public void getNonExistingUnit() throws Exception {
        // Get the unit
        restUnitMockMvc.perform(get("/api/units/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateUnit() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Update the unit
        Unit updatedUnit = unitRepository.findById(unit.getId()).get();
        updatedUnit
            .unitName(UPDATED_UNIT_NAME)
            .basePoint(UPDATED_BASE_POINT)
            .totalPoint(UPDATED_TOTAL_POINT)
            .m(UPDATED_M)
            .cc(UPDATED_CC)
            .ct(UPDATED_CT)
            .f(UPDATED_F)
            .e(UPDATED_E)
            .pv(UPDATED_PV)
            .a(UPDATED_A)
            .cd(UPDATED_CD)
            .sv(UPDATED_SV);
        UnitDTO unitDTO = unitMapper.toDto(updatedUnit);

        restUnitMockMvc.perform(put("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isOk());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);
        Unit testUnit = unitList.get(unitList.size() - 1);
        assertThat(testUnit.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
        assertThat(testUnit.getBasePoint()).isEqualTo(UPDATED_BASE_POINT);
        assertThat(testUnit.getTotalPoint()).isEqualTo(UPDATED_TOTAL_POINT);
        assertThat(testUnit.getM()).isEqualTo(UPDATED_M);
        assertThat(testUnit.getCc()).isEqualTo(UPDATED_CC);
        assertThat(testUnit.getCt()).isEqualTo(UPDATED_CT);
        assertThat(testUnit.getF()).isEqualTo(UPDATED_F);
        assertThat(testUnit.getE()).isEqualTo(UPDATED_E);
        assertThat(testUnit.getPv()).isEqualTo(UPDATED_PV);
        assertThat(testUnit.getA()).isEqualTo(UPDATED_A);
        assertThat(testUnit.getCd()).isEqualTo(UPDATED_CD);
        assertThat(testUnit.getSv()).isEqualTo(UPDATED_SV);

        // Validate the Unit in Elasticsearch
        verify(mockUnitSearchRepository, times(1)).save(testUnit);
    }

    @Test
    public void updateNonExistingUnit() throws Exception {
        int databaseSizeBeforeUpdate = unitRepository.findAll().size();

        // Create the Unit
        UnitDTO unitDTO = unitMapper.toDto(unit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitMockMvc.perform(put("/api/units").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(unitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Unit in the database
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Unit in Elasticsearch
        verify(mockUnitSearchRepository, times(0)).save(unit);
    }

    @Test
    public void deleteUnit() throws Exception {
        // Initialize the database
        unitRepository.save(unit);

        int databaseSizeBeforeDelete = unitRepository.findAll().size();

        // Delete the unit
        restUnitMockMvc.perform(delete("/api/units/{id}", unit.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Unit> unitList = unitRepository.findAll();
        assertThat(unitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Unit in Elasticsearch
        verify(mockUnitSearchRepository, times(1)).deleteById(unit.getId());
    }

    @Test
    public void searchUnit() throws Exception {
        // Initialize the database
        unitRepository.save(unit);
        when(mockUnitSearchRepository.search(queryStringQuery("id:" + unit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(unit), PageRequest.of(0, 1), 1));
        // Search the unit
        restUnitMockMvc.perform(get("/api/_search/units?query=id:" + unit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unit.getId())))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)))
            .andExpect(jsonPath("$.[*].basePoint").value(hasItem(DEFAULT_BASE_POINT)))
            .andExpect(jsonPath("$.[*].totalPoint").value(hasItem(DEFAULT_TOTAL_POINT)))
            .andExpect(jsonPath("$.[*].m").value(hasItem(DEFAULT_M)))
            .andExpect(jsonPath("$.[*].cc").value(hasItem(DEFAULT_CC)))
            .andExpect(jsonPath("$.[*].ct").value(hasItem(DEFAULT_CT)))
            .andExpect(jsonPath("$.[*].f").value(hasItem(DEFAULT_F)))
            .andExpect(jsonPath("$.[*].e").value(hasItem(DEFAULT_E)))
            .andExpect(jsonPath("$.[*].pv").value(hasItem(DEFAULT_PV)))
            .andExpect(jsonPath("$.[*].a").value(hasItem(DEFAULT_A)))
            .andExpect(jsonPath("$.[*].cd").value(hasItem(DEFAULT_CD)))
            .andExpect(jsonPath("$.[*].sv").value(hasItem(DEFAULT_SV)));
    }
}
