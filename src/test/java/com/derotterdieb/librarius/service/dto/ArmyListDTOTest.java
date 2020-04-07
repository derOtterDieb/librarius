package com.derotterdieb.librarius.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class ArmyListDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmyListDTO.class);
        ArmyListDTO armyListDTO1 = new ArmyListDTO();
        armyListDTO1.setId("id1");
        ArmyListDTO armyListDTO2 = new ArmyListDTO();
        assertThat(armyListDTO1).isNotEqualTo(armyListDTO2);
        armyListDTO2.setId(armyListDTO1.getId());
        assertThat(armyListDTO1).isEqualTo(armyListDTO2);
        armyListDTO2.setId("id2");
        assertThat(armyListDTO1).isNotEqualTo(armyListDTO2);
        armyListDTO1.setId(null);
        assertThat(armyListDTO1).isNotEqualTo(armyListDTO2);
    }
}
