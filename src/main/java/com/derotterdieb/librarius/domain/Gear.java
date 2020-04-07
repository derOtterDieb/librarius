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
 * A Gear.
 */
@Document(collection = "gear")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gear")
public class Gear implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("gear_name")
    private String gearName;

    @NotNull
    @Field("point_value")
    private Integer pointValue;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("f")
    private String f;

    @NotNull
    @Field("range")
    private String range;

    @NotNull
    @Field("pa")
    private String pa;

    @NotNull
    @Field("d")
    private String d;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGearName() {
        return gearName;
    }

    public Gear gearName(String gearName) {
        this.gearName = gearName;
        return this;
    }

    public void setGearName(String gearName) {
        this.gearName = gearName;
    }

    public Integer getPointValue() {
        return pointValue;
    }

    public Gear pointValue(Integer pointValue) {
        this.pointValue = pointValue;
        return this;
    }

    public void setPointValue(Integer pointValue) {
        this.pointValue = pointValue;
    }

    public String getType() {
        return type;
    }

    public Gear type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getF() {
        return f;
    }

    public Gear f(String f) {
        this.f = f;
        return this;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getRange() {
        return range;
    }

    public Gear range(String range) {
        this.range = range;
        return this;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getPa() {
        return pa;
    }

    public Gear pa(String pa) {
        this.pa = pa;
        return this;
    }

    public void setPa(String pa) {
        this.pa = pa;
    }

    public String getD() {
        return d;
    }

    public Gear d(String d) {
        this.d = d;
        return this;
    }

    public void setD(String d) {
        this.d = d;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gear)) {
            return false;
        }
        return id != null && id.equals(((Gear) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gear{" +
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
