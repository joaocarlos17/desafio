package com.salsatechnology.repository;

import com.salsatechnology.dto.ProductOrderDTO;
import com.salsatechnology.model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salsatechnology.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    @Query("SELECT po FROM ProductOrder po " +
            "WHERE (:#{#filter.productType} IS NULL OR po.productType = :#{#filter.productType}) " +
            "AND (:#{#filter.userName} IS NULL OR UPPER(po.userName) LIKE UPPER(CONCAT('%', :#{#filter.userName}, '%'))) " +
            "AND (:#{#filter.timeHour} IS NULL OR po.timeHour = :#{#filter.timeHour}) " +
            "ORDER BY po.id ASC")
    List<ProductOrder> filter(@Param("filter") ProductOrderDTO filter);



}
