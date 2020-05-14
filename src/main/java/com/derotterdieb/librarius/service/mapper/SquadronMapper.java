package com.derotterdieb.librarius.service.mapper;

import com.derotterdieb.librarius.domain.Squadron;
import com.derotterdieb.librarius.service.dto.SquadronDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SquadronMapper extends EntityMapper<SquadronDTO, Squadron> {
    Squadron toEntity(SquadronDTO dto);

    SquadronDTO toDto(Squadron entity);
}
