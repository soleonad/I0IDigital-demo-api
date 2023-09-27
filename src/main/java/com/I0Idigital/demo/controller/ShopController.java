package com.I0Idigital.demo.controller;

import com.I0Idigital.demo.domain.Shop;
import com.I0Idigital.demo.payload.request.Dish;
import com.I0Idigital.demo.payload.request.Queue;
import com.I0Idigital.demo.payload.request.ShopUpdateRequest;
import com.I0Idigital.demo.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shop")
@PreAuthorize("hasAuthority('SHOP_ADMIN')")
public class ShopController {

    private final Logger log = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopService shopService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Shop> getShop(@PathVariable Long id) {
        Shop shop = shopService.getShop(id);
        return ResponseEntity.ok(shop);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Shop> updateShop(@PathVariable Long id, @RequestBody ShopUpdateRequest req) {
        Shop shop = shopService.getShop(id);
        shop.setAddress(req.getAddress());
        shop.setOpeningTime(req.getOpeningTime());
        shop.setClosingTime(req.getClosingTime());
        shopService.updateShop(shop);
        return ResponseEntity.ok(shop);
    }

    @PostMapping("/{shopId}/queue")
    public ResponseEntity<Shop> updateShopQueue(@PathVariable Long shopId, @RequestBody Queue req) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.updateShopQueue(shop, req);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/{shopId}/queue")
    public ResponseEntity<Shop> createShopQueue(@PathVariable Long shopId, @RequestBody Queue req) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.createShopQueue(shop, req);
        return ResponseEntity.ok(shop);
    }

    @DeleteMapping("/{shopId}/queue/{queueId}")
    public ResponseEntity<Shop> removeShopQueue(@PathVariable Long shopId, @PathVariable Long queueId) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.removeShopQueue(shop, queueId);
        return ResponseEntity.ok(shop);
    }

    @PostMapping("/{shopId}/dish")
    public ResponseEntity<Shop> updateShopDish(@PathVariable Long shopId, @RequestBody Dish req) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.updateShopDish(shop, req);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/{shopId}/dish")
    public ResponseEntity<Shop> createShopDish(@PathVariable Long shopId, @RequestBody Dish req) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.createShopDish(shop, req);
        return ResponseEntity.ok(shop);
    }

    @DeleteMapping("/{shopId}/dish/{dishId}")
    public ResponseEntity<Shop> removeShopDish(@PathVariable Long shopId, @PathVariable Long dishId) {
        Shop shop = shopService.getShop(shopId);
        shop = shopService.removeShopDish(shop, dishId);
        return ResponseEntity.ok(shop);
    }
}