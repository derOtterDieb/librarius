package com.derotterdieb.librarius.service.mapper;


import com.derotterdieb.librarius.domain.*;
import com.derotterdieb.librarius.service.dto.UnitDTO;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Unit} and its DTO {@link UnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UnitMapper extends EntityMapper<UnitDTO, Unit> {

	UnitDTO toDto(Unit unit);

    Unit toEntity(UnitDTO unitDTO);
}
