package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.ExtendedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtendedUser} and its DTO {@link ExtendedUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArmyListMapper.class})
public interface ExtendedUserMapper extends EntityMapper<ExtendedUserDTO, ExtendedUser> {

	@Mapping(source = "armyLists", target = "armyListIds")
	ExtendedUserDTO toDto(ExtendedUser extendedUser);

	@Mapping(source = "armyListIds", target = "armyLists")
    ExtendedUser toEntity(ExtendedUserDTO extendedUserDTO);

    default ExtendedUser fromId(String id) {
        if (id == null) {
            return null;
        }
        ExtendedUser extendedUser = new ExtendedUser();
        extendedUser.setId(id);
        return extendedUser;
    }
}
