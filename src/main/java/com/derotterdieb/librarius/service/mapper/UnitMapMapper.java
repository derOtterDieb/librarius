package com.derotterdieb.librarius.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.derotterdieb.librarius.domain.UnitMap;
import com.derotterdieb.librarius.service.dto.UnitMapDTO;

@Mapper(componentModel = "spring", uses = {UnitMapper.class, GearMapper.class})
public interface UnitMapMapper extends EntityMapper<UnitMapDTO, UnitMap> {
	@Mapping(source = "unit", target = "unit")
    @Mapping(source = "gears", target = "gears")
	UnitMapDTO toDto(UnitMap unit);

	@Mapping(source = "unit", target = "unit")
    @Mapping(source = "gears", target = "gears")
    UnitMap toEntity(UnitMapDTO unitMapDTO);
}
