package com.derotterdieb.librarius.repository.search;

import com.derotterdieb.librarius.domain.Unit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Unit} entity.
 */
public interface UnitSearchRepository extends ElasticsearchRepository<Unit, String> {
}
