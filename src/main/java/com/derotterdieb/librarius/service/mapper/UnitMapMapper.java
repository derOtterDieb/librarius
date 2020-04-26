package com.derotterdieb.librarius.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.derotterdieb.librarius.domain.UnitMap;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;

@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface UnitMapMapper extends EntityMapper<UnitMapDTO, UnitMap> {
	@Mapping(source = "unit", target = "unit")
	UnitMapDTO toDto(UnitMap unit);

	@Mapping(source = "unit", target = "unit")
    UnitMap toEntity(UnitMapDTO unitMapDTO);
    
    default UnitMap fromId(String id) {
        if (id == null) {
            return null;
        }
        UnitMap unit = new UnitMap();
        unit.setId(id);
        return unit;
    }
    
    default String toId(UnitMap unit) {
    	if (unit == null || unit.getId() == null) {
    		return null;
    	}
    	return unit.getId();
    }
}
