package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.ArticlesOptionGroups;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ArticlesOptionGroups} entity.
 */
public interface ArticlesOptionGroupsSearchRepository extends ElasticsearchRepository<ArticlesOptionGroups, Long> {
}
