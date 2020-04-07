package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.GearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gear} and its DTO {@link GearDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GearMapper extends EntityMapper<GearDTO, Gear> {

	GearDTO toDto(Gear gear);

    Gear toEntity(GearDTO gearDTO);

    default Gear fromId(String id) {
        if (id == null) {
            return null;
        }
        Gear gear = new Gear();
        gear.setId(id);
        return gear;
    }
    
    default String toId(Gear gear) {
    	if (gear == null || gear.getId() == null) {
    		return null;
    	}
    	return gear.getId();
    }
}
