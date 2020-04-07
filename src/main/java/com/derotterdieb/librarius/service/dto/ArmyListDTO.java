package com.derotterdieb.librarius.service.dto;

import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.derotterdieb.librarius.domain.ArmyList} entity.
 */
public class ArmyListDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -8225946276351981931L;

    private String id;

	@NotNull
    private String listName;

    private Integer totalPoint;

    private Set<String> unitIds = new HashSet<>();

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

	public Set<String> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(Set<String> unitIds) {
		this.unitIds = unitIds;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
    
}
