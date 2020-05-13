package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.Categories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Categories} entity.
 */
public interface CategoriesSearchRepository extends ElasticsearchRepository<Categories, Long> {
}
