package com.I0Idigital.demo.repository;

import com.I0Idigital.demo.domain.ShopQueue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopQueueRepository extends CrudRepository<ShopQueue, Long> {
}
