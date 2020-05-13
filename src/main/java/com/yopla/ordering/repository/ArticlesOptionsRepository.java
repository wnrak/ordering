package com.yopla.ordering.repository;

import com.yopla.ordering.domain.ArticlesOptions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticlesOptions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlesOptionsRepository extends JpaRepository<ArticlesOptions, Long> {
}
