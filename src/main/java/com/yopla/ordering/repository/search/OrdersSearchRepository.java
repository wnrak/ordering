package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.Orders;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Orders} entity.
 */
public interface OrdersSearchRepository extends ElasticsearchRepository<Orders, Long> {
}
