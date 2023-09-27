package com.I0Idigital.demo.repository;

import com.I0Idigital.demo.domain.ShopDish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDishRepository extends CrudRepository<ShopDish, Long> {
}
