package com.derotterdieb.librarius.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class GearTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gear.class);
        Gear gear1 = new Gear();
        gear1.setId("id1");
        Gear gear2 = new Gear();
        gear2.setId(gear1.getId());
        assertThat(gear1).isEqualTo(gear2);
        gear2.setId("id2");
        assertThat(gear1).isNotEqualTo(gear2);
        gear1.setId(null);
        assertThat(gear1).isNotEqualTo(gear2);
    }
}
