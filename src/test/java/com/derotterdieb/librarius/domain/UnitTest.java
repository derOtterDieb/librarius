package com.derotterdieb.librarius.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class UnitTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unit.class);
        Unit unit1 = new Unit();
        unit1.setId("id1");
        Unit unit2 = new Unit();
        unit2.setId(unit1.getId());
        assertThat(unit1).isEqualTo(unit2);
        unit2.setId("id2");
        assertThat(unit1).isNotEqualTo(unit2);
        unit1.setId(null);
        assertThat(unit1).isNotEqualTo(unit2);
    }
}
