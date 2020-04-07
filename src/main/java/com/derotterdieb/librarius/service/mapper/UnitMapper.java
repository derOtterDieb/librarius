package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.UnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {GearMapper.class})
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {


    @Mapping(target = "removeUnit", ignore = true)
    @Mapping(target = "units", ignore = true)
    @Mapping(target = "removeUnit", ignore = true)
    Unit toEntity(UnitDTO unitDTO);

    default Unit fromId(String id) {
        if (id == null) {
            return null;
        }
        Unit unit = new Unit();
        unit.setId(id);
        return unit;
    }
}
