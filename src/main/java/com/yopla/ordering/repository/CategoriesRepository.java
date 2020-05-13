package com.yopla.ordering.repository;

import com.yopla.ordering.domain.Categories;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Categories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
