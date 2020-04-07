package com.derotterdieb.librarius.repository.search;

import com.derotterdieb.librarius.domain.ArmyList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ArmyList} entity.
 */
public interface ArmyListSearchRepository extends ElasticsearchRepository<ArmyList, String> {
}
