package com.derotterdieb.librarius.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class UnitDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitDTO.class);
        UnitDTO unitDTO1 = new UnitDTO();
        unitDTO1.setId("id1");
        UnitDTO unitDTO2 = new UnitDTO();
        assertThat(unitDTO1).isNotEqualTo(unitDTO2);
        unitDTO2.setId(unitDTO1.getId());
        assertThat(unitDTO1).isEqualTo(unitDTO2);
        unitDTO2.setId("id2");
        assertThat(unitDTO1).isNotEqualTo(unitDTO2);
        unitDTO1.setId(null);
        assertThat(unitDTO1).isNotEqualTo(unitDTO2);
    }
}
