package com.derotterdieb.librarius.service.dto;

import java.io.Serializable;
import java.util.List;

public class SquadronMapDTO implements Serializable {

    private String id;

    private SquadronDTO squadron;

    private List<UnitMapDTO> unitMaps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SquadronDTO getSquadron() {
        return squadron;
    }

    public void setSquadron(SquadronDTO squadron) {
        this.squadron = squadron;
    }

    public List<UnitMapDTO> getUnitMaps() {
        return unitMaps;
    }

    public void setUnitMaps(List<UnitMapDTO> unitMaps) {
        this.unitMaps = unitMaps;
    }

    public List<UnitMapDTO> addUnitMap(UnitMapDTO unitMap) {
        this.unitMaps.add(unitMap);
        return this.unitMaps;
    }

    public List<UnitMapDTO> removeUnitMap(UnitMapDTO unitmap) {
        this.unitMaps.remove(unitmap);
        return this.unitMaps;
    }
}
