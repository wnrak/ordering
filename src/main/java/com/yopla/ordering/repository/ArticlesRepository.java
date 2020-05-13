package com.yopla.ordering.repository;

import com.yopla.ordering.domain.Articles;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Articles entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
}
