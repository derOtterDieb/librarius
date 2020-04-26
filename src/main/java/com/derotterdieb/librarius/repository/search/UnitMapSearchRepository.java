package com.derotterdieb.librarius.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.derotterdieb.librarius.domain.UnitMap;

public interface UnitMapSearchRepository extends ElasticsearchRepository<UnitMap, String> {

}
