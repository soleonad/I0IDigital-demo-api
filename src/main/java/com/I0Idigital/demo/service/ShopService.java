package com.I0Idigital.demo.service;

import com.I0Idigital.demo.domain.Shop;
import com.I0Idigital.demo.domain.ShopDish;
import com.I0Idigital.demo.domain.ShopQueue;
import com.I0Idigital.demo.handler.error.ShopNotFoundError;
import com.I0Idigital.demo.payload.request.Dish;
import com.I0Idigital.demo.payload.request.Queue;
import com.I0Idigital.demo.repository.ShopDishRepository;
import com.I0Idigital.demo.repository.ShopQueueRepository;
import com.I0Idigital.demo.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ShopService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopQueueRepository shopQueueRepository;

    @Autowired
    private ShopDishRepository shopDishRepository;

    public Shop getShopForOrder(Long id) {
        return shopRepository.findById(id).orElseThrow(ShopNotFoundError::new);
    }

    @PostAuthorize("@shopAdminChecker.correctAdmin(returnObject)")
    public Shop getShop(Long id) {
        return shopRepository.findById(id).orElseThrow(ShopNotFoundError::new);
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop updateShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop updateShopQueue(Shop shop, Queue queue) {
        shop.getQueues().stream().filter(q -> q.getId().equals(queue.getId())).findFirst()
                .ifPresent(shopQueue -> {
                    if (queue.getName() != null) {
                        shopQueue.setName(queue.getName());
                    }
                    if (queue.getMaxQuantity() != null) {
                        shopQueue.setMaxQuantity(queue.getMaxQuantity());
                    }
                    if (queue.getCurrentIndex() != null) {
                        shopQueue.setCurrentIndex(queue.getCurrentIndex());
                    }
                    shopQueueRepository.save(shopQueue);
                });
        return shop;
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop createShopQueue(Shop shop, Queue queue) {
        ShopQueue shopQueue = new ShopQueue();
        shopQueue.setName(queue.getName());
        shopQueue.setMaxQuantity(queue.getMaxQuantity());
        shopQueue.setCurrentIndex(queue.getCurrentIndex());
        shopQueue.setShop(shop);
        shopQueueRepository.save(shopQueue);
        return shopRepository.findById(shop.getId()).get();
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop removeShopQueue(Shop shop, Long shopQueueId) {
        shopQueueRepository.deleteById(shopQueueId);
        return shopRepository.findById(shop.getId()).get();
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop updateShopDish(Shop shop, Dish dish) {
        shop.getDishes().stream().filter(q -> q.getId().equals(dish.getId())).findFirst()
                .ifPresent(shopDish -> {
                    if (dish.getName() != null) {
                        shopDish.setName(dish.getName());
                    }
                    if (dish.getPrice() != null) {
                        shopDish.setPrice(dish.getPrice());
                    }
                    if (dish.getCurrency() != null) {
                        shopDish.setCurrency(dish.getCurrency());
                    }
                    shopDishRepository.save(shopDish);
                });
        return shop;
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop createShopDish(Shop shop, Dish dish) {
        ShopDish shopDish = new ShopDish();
        shopDish.setName(dish.getName());
        shopDish.setPrice(dish.getPrice());
        shopDish.setCurrency(dish.getCurrency());
        shopDish.setShop(shop);
        shopDishRepository.save(shopDish);
        return shopRepository.findById(shop.getId()).get();
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#shop)")
    public Shop removeShopDish(Shop shop, Long shopDishId) {
        shopDishRepository.deleteById(shopDishId);
        return shopRepository.findById(shop.getId()).get();
    }
}
