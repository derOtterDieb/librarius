package com.derotterdieb.librarius.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ArmyList.
 */
@Document(collection = "army_list")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "armylist")
public class ArmyList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("list_name")
    private String listName;

    @Field("total_point")
    private Integer totalPoint;

    @DBRef
    @Field("units")
    private Set<Unit> units = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public ArmyList listName(String listName) {
        this.listName = listName;
        return this;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public ArmyList totalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
        return this;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public ArmyList units(Set<Unit> units) {
        this.units = units;
        return this;
    }

    public ArmyList addUnit(Unit unit) {
        this.units.add(unit);
        return this;
    }

    public ArmyList removeUnit(Unit unit) {
        this.units.remove(unit);
        return this;
    }

    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArmyList)) {
            return false;
        }
        return id != null && id.equals(((ArmyList) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ArmyList{" +
            "id=" + getId() +
            ", listName='" + getListName() + "'" +
            ", totalPoint=" + getTotalPoint() +
            "}";
    }
}
