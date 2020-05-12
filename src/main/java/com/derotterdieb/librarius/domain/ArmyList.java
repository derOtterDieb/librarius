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
    private Set<UnitMap> unitMap = new HashSet<>();

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

    public Set<UnitMap> getUnitMap() {
        return unitMap;
    }

    public ArmyList units(Set<UnitMap> units) {
        this.unitMap = units;
        return this;
    }

    public ArmyList addUnitMap(UnitMap unit) {
        this.unitMap.add(unit);
        return this;
    }

    public ArmyList removeUnitMap(UnitMap unit) {
        UnitMap unitMapToRemove = new UnitMap();
        for (UnitMap unitMap : this.getUnitMap()) {
            if (unitMap.getId().equals(unit.getId())) {
                unitMapToRemove = unitMap;
            }
        }
        this.unitMap.remove(unitMapToRemove);
        return this;
    }

    public void setUnitMap(Set<UnitMap> units) {
        this.unitMap = units;
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
