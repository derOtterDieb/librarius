package com.derotterdieb.librarius.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection = "squadron_map")
public class SquadronMap implements Serializable {

    @Id
    private String id;

    @DBRef
    @Field("squadron")
    private Squadron squadron;

    @DBRef
    @Field("unit_maps")
    private List<UnitMap> unitMaps;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Squadron getSquadron() {
        return squadron;
    }

    public void setSquadron(Squadron squadron) {
        this.squadron = squadron;
    }

    public List<UnitMap> getUnitMaps() {
        return unitMaps;
    }

    public void setUnitMaps(List<UnitMap> unitMaps) {
        this.unitMaps = unitMaps;
    }

    public List<UnitMap> addUnitMap(UnitMap unitMap) {
        this.unitMaps.add(unitMap);
        return this.unitMaps;
    }

    public List<UnitMap> removeUnitMap(UnitMap unitmap) {
        this.unitMaps.remove(unitmap);
        return this.unitMaps;
    }

    @Override
    public int hashCode() {
        return 69;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SquadronMap)) {
            return false;
        }
        return id != null && id.equals(((SquadronMap) obj).id);
    }

    @Override
    public String toString() {
        return getId();
    }
}
