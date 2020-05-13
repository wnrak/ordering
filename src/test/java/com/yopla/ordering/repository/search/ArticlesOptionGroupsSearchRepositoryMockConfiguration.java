package com.yopla.ordering.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ArticlesOptionGroupsSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ArticlesOptionGroupsSearchRepositoryMockConfiguration {

    @MockBean
    private ArticlesOptionGroupsSearchRepository mockArticlesOptionGroupsSearchRepository;

}
