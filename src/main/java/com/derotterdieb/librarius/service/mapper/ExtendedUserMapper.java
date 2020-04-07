package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.ExtendedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExtendedUser} and its DTO {@link ExtendedUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExtendedUserMapper extends EntityMapper<ExtendedUserDTO, ExtendedUser> {


    @Mapping(target = "users", ignore = true)
    @Mapping(target = "removeUser", ignore = true)
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
