package com.yopla.ordering.repository;

import com.yopla.ordering.domain.ArticlesOptionGroups;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ArticlesOptionGroups entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticlesOptionGroupsRepository extends JpaRepository<ArticlesOptionGroups, Long> {
}
