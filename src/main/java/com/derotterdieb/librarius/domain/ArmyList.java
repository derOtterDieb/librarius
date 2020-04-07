package com.derotterdieb.librarius.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @Field("armyLists")
    private Set<Unit> armyLists = new HashSet<>();

    @DBRef
    @Field("armyLists")
    @JsonIgnoreProperties("users")
    private ExtendedUser armyLists;

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

    public Set<Unit> getArmyLists() {
        return armyLists;
    }

    public ArmyList armyLists(Set<Unit> units) {
        this.armyLists = units;
        return this;
    }

    public ArmyList addArmyList(Unit unit) {
        this.armyLists.add(unit);
        unit.getUnits().add(this);
        return this;
    }

    public ArmyList removeArmyList(Unit unit) {
        this.armyLists.remove(unit);
        unit.getUnits().remove(this);
        return this;
    }

    public void setArmyLists(Set<Unit> units) {
        this.armyLists = units;
    }

    public ExtendedUser getArmyLists() {
        return armyLists;
    }

    public ArmyList armyLists(ExtendedUser extendedUser) {
        this.armyLists = extendedUser;
        return this;
    }

    public void setArmyLists(ExtendedUser extendedUser) {
        this.armyLists = extendedUser;
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
