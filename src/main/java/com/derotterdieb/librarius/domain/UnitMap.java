package com.derotterdieb.librarius.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "unit_map")
public class UnitMap implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @DBRef
    @Field("unit")
    private Unit unit;

    @NotNull
    @Field("number_of_unit")
    private Integer numberOfUnit;

    @DBRef
    @Field("gears")
    private Set<Gear> gears = new HashSet<>();

    @Field("squadron")
    private String squadronId;

    public Set<Gear> getGears() {
        return gears;
    }

    public void setGears(Set<Gear> gear) {
        this.gears = gear;
    }

    public UnitMap addGear(Gear gear) {
        this.gears.add(gear);
        return this;
    }

    public UnitMap removeGear(Gear gear) {
        this.gears.remove(gear);
        return this;
    }

    public UnitMap gears(Set<Gear> gear) {
        this.gears = gear;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Integer getNumberOfUnit() {
		return numberOfUnit;
	}

	public void setNumberOfUnit(Integer numberOfUnit) {
		this.numberOfUnit = numberOfUnit;
	}

    public String getSquadronId() {
        return squadronId;
    }

    public void setSquadronId(String squadronId) {
        this.squadronId = squadronId;
    }

    @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof UnitMap)) {
	            return false;
	        }
	        return id != null && id.equals(((UnitMap) o).id);
	    }

	    @Override
	    public int hashCode() {
	        return 87;
	    }

	    @Override
	    public String toString() {
	        return "UnitMap{" +
	            "id=" + getId() +
	            "}";
	    }

}
