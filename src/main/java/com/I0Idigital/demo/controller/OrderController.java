package com.I0Idigital.demo.controller;

import com.I0Idigital.demo.domain.Order;
import com.I0Idigital.demo.domain.Shop;
import com.I0Idigital.demo.payload.request.OrderRequest;
import com.I0Idigital.demo.payload.request.Queue;
import com.I0Idigital.demo.service.OrderService;
import com.I0Idigital.demo.service.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private ShopService shopService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/menu/{shopId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Shop> getShopMenu(@PathVariable Long shopId) {
        Shop shop = shopService.getShopForOrder(shopId);
        return ResponseEntity.ok(shop);
    }

    @PutMapping("/menu/{shopId}/order")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Order> order(@PathVariable Long shopId, @RequestBody OrderRequest request) {
        Order order = orderService.order(shopId, request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Order> viewOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/order/{orderId}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        order = orderService.cancelOrder(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/shopProcess/{shopId}/orders")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<List<Queue>> getQueuedOrders(@PathVariable Long shopId) {
        Shop shop = shopService.getShopForOrder(shopId);
        List<Queue> queues = orderService.getQueuesWithOrders(shop).stream().map(q -> {
            Queue queue = new Queue();
            queue.setId(q.getId());
            queue.setName(q.getName());
            queue.setCurrentIndex(q.getCurrentIndex());
            queue.setMaxQuantity(q.getMaxQuantity());
            queue.setOrders(q.getOrdersInQueue());
            return queue;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(queues);
    }

    @PostMapping("/shopProcess/order/{orderId}/queuePush")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Order> queueOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        order = orderService.queueOrder(order);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/shopProcess/{shopId}/queueNext")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Order> queueNextOrder(@PathVariable Long shopId) {
        Shop shop = shopService.getShop(shopId);
        Optional<Order> order = orderService.queueNextOrder(shop);
        return ResponseEntity.of(order);
    }

    @PostMapping("/shopProcess/order/{orderId}/process")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Order> processOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        order = orderService.processOrder(order);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/shopProcess/{shopId}/queuePop")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Order> processNextOrder(@PathVariable Long shopId) {
        Shop shop = shopService.getShop(shopId);
        Optional<Order> order = orderService.processNextOrder(shop);
        return ResponseEntity.of(order);
    }

    @PostMapping("/shopProcess/order/{orderId}/complete")
    @PreAuthorize("hasAuthority('SHOP_ADMIN')")
    public ResponseEntity<Order> completeOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrder(orderId);
        order = orderService.completeOrder(order);
        return ResponseEntity.ok(order);
    }
}