package com.derotterdieb.librarius.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.derotterdieb.librarius.domain.ArmyList} entity.
 */
public class ArmyListDTO implements Serializable {
    
    private String id;

    @NotNull
    private String listName;

    private Integer totalPoint;

    private Set<UnitDTO> armyLists = new HashSet<>();

    private String armyListsId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Set<UnitDTO> getArmyLists() {
        return armyLists;
    }

    public void setArmyLists(Set<UnitDTO> units) {
        this.armyLists = units;
    }

    public String getArmyListsId() {
        return armyListsId;
    }

    public void setArmyListsId(String extendedUserId) {
        this.armyListsId = extendedUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArmyListDTO armyListDTO = (ArmyListDTO) o;
        if (armyListDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), armyListDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ArmyListDTO{" +
            "id=" + getId() +
            ", listName='" + getListName() + "'" +
            ", totalPoint=" + getTotalPoint() +
            ", armyLists='" + getArmyLists() + "'" +
            ", armyListsId=" + getArmyListsId() +
            "}";
    }
}
