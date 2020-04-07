package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.UnitDTO;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {GearMapper.class})
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {

	@Mapping(source = "gears", target = "gearIds")
	UnitDTO toDto(Unit unit);

	@Mapping(source = "gearIds", target = "gears")
    Unit toEntity(UnitDTO unitDTO);
    
    default Unit fromId(String id) {
        if (id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(id);
        return unit;
    }
    
    default String toId(Unit unit) {
    	if (unit == null || unit.getId() == null) {
    		return null;
    	}
    	return unit.getId();
    }
}
