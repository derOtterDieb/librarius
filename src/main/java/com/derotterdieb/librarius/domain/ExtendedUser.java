package com.derotterdieb.librarius.domain;

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
 * A ExtendedUser.
 */
@Document(collection = "extended_user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "extendeduser")
public class ExtendedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("pseudo")
    private String pseudo;

    @DBRef
    @Field("user")
    private Set<ArmyList> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public ExtendedUser pseudo(String pseudo) {
        this.pseudo = pseudo;
        return this;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Set<ArmyList> getUsers() {
        return users;
    }

    public ExtendedUser users(Set<ArmyList> armyLists) {
        this.users = armyLists;
        return this;
    }

    public ExtendedUser addUser(ArmyList armyList) {
        this.users.add(armyList);
        armyList.setArmyLists(this);
        return this;
    }

    public ExtendedUser removeUser(ArmyList armyList) {
        this.users.remove(armyList);
        armyList.setArmyLists(null);
        return this;
    }

    public void setUsers(Set<ArmyList> armyLists) {
        this.users = armyLists;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendedUser)) {
            return false;
        }
        return id != null && id.equals(((ExtendedUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExtendedUser{" +
            "id=" + getId() +
            ", pseudo='" + getPseudo() + "'" +
            "}";
    }
}
