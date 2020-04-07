package com.derotterdieb.librarius.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ArmyListSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ArmyListSearchRepositoryMockConfiguration {

    @MockBean
    private ArmyListSearchRepository mockArmyListSearchRepository;

}
