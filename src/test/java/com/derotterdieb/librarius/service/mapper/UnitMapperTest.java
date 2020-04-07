package com.derotterdieb.librarius.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitMapperTest {

    private UnitMapper unitMapper;

    @BeforeEach
    public void setUp() {
        unitMapper = new UnitMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(unitMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(unitMapper.fromId(null)).isNull();
    }
}
