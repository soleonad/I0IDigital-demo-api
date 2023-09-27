package com.I0Idigital.demo.repository;

import com.I0Idigital.demo.domain.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends CrudRepository<Shop, Long> {
}
