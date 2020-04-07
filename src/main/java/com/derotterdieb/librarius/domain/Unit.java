package com.derotterdieb.librarius.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Unit.
 */
@Document(collection = "unit")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "unit")
public class Unit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("unit_name")
    private String unitName;

    @NotNull
    @Field("base_point")
    private Integer basePoint;

    @Field("total_point")
    private Integer totalPoint;

    @NotNull
    @Field("m")
    private String m;

    @NotNull
    @Field("cc")
    private String cc;

    @NotNull
    @Field("ct")
    private String ct;

    @NotNull
    @Field("f")
    private String f;

    @NotNull
    @Field("e")
    private String e;

    @NotNull
    @Field("pv")
    private String pv;

    @NotNull
    @Field("a")
    private String a;

    @NotNull
    @Field("cd")
    private String cd;

    @NotNull
    @Field("sv")
    private String sv;

    @DBRef
    @Field("gears")
    private Set<Gear> gears = new HashSet<>();

    public Set<Gear> getGears() {
		return gears;
	}

	public void setGears(Set<Gear> gear) {
		this.gears = gear;
	}
	
	public Unit addGear(Gear gear) {
		this.gears.add(gear);
		return this;
	}
	
	public Unit removeGear(Gear gear) {
		this.gears.remove(gear);
		return this;
	}
	
	public Unit gears(Set<Gear> gear) {
		this.gears = gear;
		return this;
	}

	// jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public Unit unitName(String unitName) {
        this.unitName = unitName;
        return this;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getBasePoint() {
        return basePoint;
    }

    public Unit basePoint(Integer basePoint) {
        this.basePoint = basePoint;
        return this;
    }

    public void setBasePoint(Integer basePoint) {
        this.basePoint = basePoint;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public Unit totalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
        return this;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public String getM() {
        return m;
    }

    public Unit m(String m) {
        this.m = m;
        return this;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getCc() {
        return cc;
    }

    public Unit cc(String cc) {
        this.cc = cc;
        return this;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCt() {
        return ct;
    }

    public Unit ct(String ct) {
        this.ct = ct;
        return this;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getF() {
        return f;
    }

    public Unit f(String f) {
        this.f = f;
        return this;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getE() {
        return e;
    }

    public Unit e(String e) {
        this.e = e;
        return this;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getPv() {
        return pv;
    }

    public Unit pv(String pv) {
        this.pv = pv;
        return this;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getA() {
        return a;
    }

    public Unit a(String a) {
        this.a = a;
        return this;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getCd() {
        return cd;
    }

    public Unit cd(String cd) {
        this.cd = cd;
        return this;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getSv() {
        return sv;
    }

    public Unit sv(String sv) {
        this.sv = sv;
        return this;
    }

    public void setSv(String sv) {
        this.sv = sv;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Unit)) {
            return false;
        }
        return id != null && id.equals(((Unit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Unit{" +
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
