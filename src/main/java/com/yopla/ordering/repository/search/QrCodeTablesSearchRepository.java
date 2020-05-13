package com.yopla.ordering.repository.search;

import com.yopla.ordering.domain.QrCodeTables;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link QrCodeTables} entity.
 */
public interface QrCodeTablesSearchRepository extends ElasticsearchRepository<QrCodeTables, Long> {
}
