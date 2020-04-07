package com.derotterdieb.librarius.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class GearDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GearDTO.class);
        GearDTO gearDTO1 = new GearDTO();
        gearDTO1.setId("id1");
        GearDTO gearDTO2 = new GearDTO();
        assertThat(gearDTO1).isNotEqualTo(gearDTO2);
        gearDTO2.setId(gearDTO1.getId());
        assertThat(gearDTO1).isEqualTo(gearDTO2);
        gearDTO2.setId("id2");
        assertThat(gearDTO1).isNotEqualTo(gearDTO2);
        gearDTO1.setId(null);
        assertThat(gearDTO1).isNotEqualTo(gearDTO2);
    }
}
