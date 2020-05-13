package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.Articles;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Articles} entity.
 */
public interface ArticlesSearchRepository extends ElasticsearchRepository<Articles, Long> {
}
