package com.yopla.ordering.repository;

import com.yopla.ordering.domain.Restaurant;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
