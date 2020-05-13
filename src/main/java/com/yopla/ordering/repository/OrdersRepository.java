package com.yopla.ordering.repository;

import com.yopla.ordering.domain.Orders;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Orders entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select orders from Orders orders where orders.user.login = ?#{principal.username}")
    List<Orders> findByUserIsCurrentUser();
}
