package com.derotterdieb.librarius.repository.search;

import com.derotterdieb.librarius.domain.ExtendedUser;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ExtendedUser} entity.
 */
public interface ExtendedUserSearchRepository extends ElasticsearchRepository<ExtendedUser, String> {
}
