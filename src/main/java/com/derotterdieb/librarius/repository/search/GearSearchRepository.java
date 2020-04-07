package com.derotterdieb.librarius.repository.search;

import com.derotterdieb.librarius.domain.Gear;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Gear} entity.
 */
public interface GearSearchRepository extends ElasticsearchRepository<Gear, String> {
}
