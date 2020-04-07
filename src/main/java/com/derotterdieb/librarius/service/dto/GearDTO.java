package com.derotterdieb.librarius.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.derotterdieb.librarius.domain.Gear} entity.
 */
public class GearDTO implements Serializable {
    
    private String id;

    @NotNull
    private String gearName;

    @NotNull
    private Integer pointValue;

    @NotNull
    private String type;

    @NotNull
    private String f;

    @NotNull
    private String range;

    @NotNull
    private String pa;

    @NotNull
    private String d;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGearName() {
        return gearName;
    }

    public void setGearName(String gearName) {
        this.gearName = gearName;
    }

    public Integer getPointValue() {
        return pointValue;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPa() {
        return pa;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GearDTO gearDTO = (GearDTO) o;
        if (gearDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gearDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GearDTO{" +
            "id=" + getId() +
            ", gearName='" + getGearName() + "'" +
            ", pointValue=" + getPointValue() +
            ", type='" + getType() + "'" +
            ", f='" + getF() + "'" +
            ", range='" + getRange() + "'" +
            ", pa='" + getPa() + "'" +
            ", d='" + getD() + "'" +
            "}";
    }
}
