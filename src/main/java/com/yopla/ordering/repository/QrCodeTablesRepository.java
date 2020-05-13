package com.yopla.ordering.repository;

import com.yopla.ordering.domain.QrCodeTables;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QrCodeTables entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QrCodeTablesRepository extends JpaRepository<QrCodeTables, Long> {
}
