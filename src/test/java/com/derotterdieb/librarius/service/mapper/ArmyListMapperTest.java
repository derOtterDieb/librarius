package com.derotterdieb.librarius.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArmyListMapperTest {

    private ArmyListMapper armyListMapper;

    @BeforeEach
    public void setUp() {
        armyListMapper = new ArmyListMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(armyListMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(armyListMapper.fromId(null)).isNull();
    }
}
