package com.derotterdieb.librarius.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.derotterdieb.librarius.web.rest.TestUtil;

public class ArmyListTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArmyList.class);
        ArmyList armyList1 = new ArmyList();
        armyList1.setId("id1");
        ArmyList armyList2 = new ArmyList();
        armyList2.setId(armyList1.getId());
        assertThat(armyList1).isEqualTo(armyList2);
        armyList2.setId("id2");
        assertThat(armyList1).isNotEqualTo(armyList2);
        armyList1.setId(null);
        assertThat(armyList1).isNotEqualTo(armyList2);
    }
}
