package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmyList} and its DTO {@link ArmyListDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class})
public interface ArmyListMapper extends EntityMapper<ArmyListDTO, ArmyList> {

	@Mapping(source = "units", target = "unitIds")
    ArmyListDTO toDto(ArmyList armyList);

	@Mapping(source = "unitIds", target = "units")
    ArmyList toEntity(ArmyListDTO armyListDTO);

    default ArmyList fromId(String id) {
        if (id == null) {
            return null;
        }
        ArmyList armyList = new ArmyList();
        armyList.setId(id);
        return armyList;
    }
    
    default String toId(ArmyList entity) {
    	if (entity == null || entity.getId() == null) {
    		return null;
    	}
    	return entity.getId();
    }
}
