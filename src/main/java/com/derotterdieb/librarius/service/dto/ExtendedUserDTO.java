package com.derotterdieb.librarius.service.dto;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.derotterdieb.librarius.domain.ExtendedUser} entity.
 */
public class ExtendedUserDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 2757273087795684881L;

	private String id;

    @NotNull
    private String pseudo;
    
    private Set<String> armyListIds = new HashSet<>();
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Set<String> getArmyListIds() {
		return armyListIds;
	}

	public void setArmyListIds(Set<String> armyListIds) {
		this.armyListIds = armyListIds;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExtendedUserDTO extendedUserDTO = (ExtendedUserDTO) o;
        if (extendedUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendedUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ExtendedUserDTO{" +
            "id=" + getId() +
            ", pseudo='" + getPseudo() + "'" +
            "}";
    }
}
