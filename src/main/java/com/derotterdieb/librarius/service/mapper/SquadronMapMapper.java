package com.derotterdieb.librarius.service.mapper;

import com.derotterdieb.librarius.domain.SquadronMap;
import com.derotterdieb.librarius.service.dto.SquadronMapDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UnitMapMapper.class, SquadronMapper.class})
public interface SquadronMapMapper extends EntityMapper<SquadronMapDTO, SquadronMap> {

    @Mapping(source = "squadron", target = "squadron")
    @Mapping(source = "unitMaps", target = "unitMaps")
    SquadronMapDTO toDto(SquadronMap entity);

    @Mapping(source = "squadron", target = "squadron")
    @Mapping(source = "unitMaps", target = "unitMaps")
    SquadronMap toEntity(SquadronMapDTO dto);
}
