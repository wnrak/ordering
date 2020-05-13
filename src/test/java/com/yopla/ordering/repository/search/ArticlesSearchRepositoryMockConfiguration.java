package com.yopla.ordering.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ArticlesSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ArticlesSearchRepositoryMockConfiguration {

    @MockBean
    private ArticlesSearchRepository mockArticlesSearchRepository;

}
