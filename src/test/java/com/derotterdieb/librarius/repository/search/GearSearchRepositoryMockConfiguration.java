package com.derotterdieb.librarius.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link GearSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class GearSearchRepositoryMockConfiguration {

    @MockBean
    private GearSearchRepository mockGearSearchRepository;

}
