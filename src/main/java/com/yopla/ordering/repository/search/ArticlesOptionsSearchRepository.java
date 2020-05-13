package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.ArticlesOptions;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ArticlesOptions} entity.
 */
public interface ArticlesOptionsSearchRepository extends ElasticsearchRepository<ArticlesOptions, Long> {
}
