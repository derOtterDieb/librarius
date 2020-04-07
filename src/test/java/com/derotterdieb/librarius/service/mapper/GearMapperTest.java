package com.derotterdieb.librarius.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GearMapperTest {

    private GearMapper gearMapper;

    @BeforeEach
    public void setUp() {
        gearMapper = new GearMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(gearMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gearMapper.fromId(null)).isNull();
    }
}
