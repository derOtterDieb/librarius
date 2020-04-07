package com.derotterdieb.librarius.service.dto;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.derotterdieb.librarius.domain.Unit} entity.
 */
public class UnitDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8294792833210006484L;

	private String id;

    @NotNull
    private String unitName;

    @NotNull
    private Integer basePoint;

    private Integer totalPoint;

    @NotNull
    private String m;

    @NotNull
    private String cc;

    @NotNull
    private String ct;

    @NotNull
    private String f;

    @NotNull
    private String e;

    @NotNull
    private String pv;

    @NotNull
    private String a;

    @NotNull
    private String cd;

    @NotNull
    private String sv;

    private Set<String> gearIds = new HashSet<>();
    
    public String getId() {
        return id;
    }

    public Set<String> getGearIds() {
		return gearIds;
	}

	public void setGearIds(Set<String> gearIds) {
		this.gearIds = gearIds;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getBasePoint() {
        return basePoint;
    }

    public void setBasePoint(Integer basePoint) {
        this.basePoint = basePoint;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getSv() {
        return sv;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UnitDTO unitDTO = (UnitDTO) o;
        if (unitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), unitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UnitDTO{" +
            "id=" + getId() +
            ", unitName='" + getUnitName() + "'" +
            ", basePoint=" + getBasePoint() +
            ", totalPoint=" + getTotalPoint() +
            ", m='" + getM() + "'" +
            ", cc='" + getCc() + "'" +
            ", ct='" + getCt() + "'" +
            ", f='" + getF() + "'" +
            ", e='" + getE() + "'" +
            ", pv='" + getPv() + "'" +
            ", a='" + getA() + "'" +
            ", cd='" + getCd() + "'" +
            ", sv='" + getSv() + "'" +
            "}";
    }
}
