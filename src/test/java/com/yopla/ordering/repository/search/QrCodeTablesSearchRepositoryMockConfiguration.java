package com.yopla.ordering.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link QrCodeTablesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class QrCodeTablesSearchRepositoryMockConfiguration {

    @MockBean
    private QrCodeTablesSearchRepository mockQrCodeTablesSearchRepository;

}
