package com.derotterdieb.librarius.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class UnitMapDTO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8796925490656028024L;

    private String id;

    @NotNull
    private UnitDTO unit;

    @NotNull
    private Integer numberOfUnit;

    private String squadronId;

    private Set<GearDTO> gears = new HashSet<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UnitDTO getUnit() {
		return unit;
	}

	public void setUnit(UnitDTO unitId) {
		this.unit = unitId;
	}

	public Integer getNumberOfUnit() {
		return numberOfUnit;
	}

	public void setNumberOfUnit(Integer numberOfUnit) {
		this.numberOfUnit = numberOfUnit;
	}

    public Set<GearDTO> getGears() {
        return gears;
    }

    public void setGears(Set<GearDTO> gears) {
        this.gears = gears;
    }

    public String getSquadronId() {
        return squadronId;
    }

    public void setSquadronId(String squadronId) {
        this.squadronId = squadronId;
    }
}
