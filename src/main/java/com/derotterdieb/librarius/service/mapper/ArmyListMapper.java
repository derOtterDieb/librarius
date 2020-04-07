package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.ArmyListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArmyList} and its DTO {@link ArmyListDTO}.
 */
@Mapper(componentModel = "spring", uses = {UnitMapper.class, ExtendedUserMapper.class})
public interface ArmyListMapper extends EntityMapper<ArmyListDTO, ArmyList> {

    @Mapping(source = "armyLists.id", target = "armyListsId")
    ArmyListDTO toDto(ArmyList armyList);

    @Mapping(target = "removeArmyList", ignore = true)
    @Mapping(source = "armyListsId", target = "armyLists")
    ArmyList toEntity(ArmyListDTO armyListDTO);

    default ArmyList fromId(String id) {
        if (id == null) {
            return null;
        }
        ArmyList armyList = new ArmyList();
        armyList.setId(id);
        return armyList;
    }
}
