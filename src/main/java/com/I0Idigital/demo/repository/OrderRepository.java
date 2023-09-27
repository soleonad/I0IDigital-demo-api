package com.I0Idigital.demo.repository;

import com.I0Idigital.demo.domain.Order;
import com.I0Idigital.demo.domain.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByShopIdAndStatusAndQueueIdIsNull(Long shopId, OrderStatus status);
    List<Order> findByQueueIdAndQueueIndexGreaterThanEqual(Long queueId, Long queueIndex);
}
