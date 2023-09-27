package com.I0Idigital.demo.service;

import com.I0Idigital.demo.domain.*;
import com.I0Idigital.demo.payload.request.OrderRequest;
import com.I0Idigital.demo.repository.OrderRepository;
import com.I0Idigital.demo.repository.ShopQueueRepository;
import com.I0Idigital.demo.repository.ShopRepository;
import com.I0Idigital.demo.security.services.UserDetailsImpl;
import com.I0Idigital.demo.handler.error.DishNotFoundError;
import com.I0Idigital.demo.handler.error.NoAvailableQueueError;
import com.I0Idigital.demo.handler.error.OrderNotFoundError;
import com.I0Idigital.demo.handler.error.ShopNotFoundError;
import com.I0Idigital.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class OrderService {
    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopQueueRepository shopQueueRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundError::new);
    }

    public List<ShopQueue> getQueuesWithOrders(Shop shop) {
        return shop.getQueues().stream().peek(q -> {
            List<Order> inQueueOrders = orderRepository.findByQueueIdAndQueueIndexGreaterThanEqual(q.getId(), q.getCurrentIndex());
            inQueueOrders.sort(Comparator.comparingLong(Order::getQueueIndex));
            q.setOrdersInQueue(inQueueOrders);
        }).collect(Collectors.toList());
    }

    public Order order(Long shopId, OrderRequest request) {
        Optional<Shop> shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
            Shop s = shop.get();
            Order order = new Order();
            order.setShop(s);
            List<OrderDetail> orderDetails = request.getItems().stream().map(item -> {
                Optional<ShopDish> shopDish = s.getDishes().stream().filter(dish -> dish.getId().equals(item.getShopDishId())).findFirst();
                if (shopDish.isPresent()) {
                    OrderDetail orderDetail = new OrderDetail(shopDish.get(), item.getQuantity());
                    orderDetail.setOrder(order);
                    return orderDetail;
                }
                throw DishNotFoundError.create();
            }).collect(Collectors.toList());
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                Long currentUserId = ((UserDetailsImpl) principal).getId();
                Optional<Customer> optCustomer = customerRepository.findByUserId(currentUserId);
                if (optCustomer.isPresent()) {
                    Customer customer = optCustomer.get();
                    order.setCustomer(customer);
                    order.setItems(orderDetails);
                    order.setStatus(OrderStatus.ORDERED);
                    order.setOrderedTime(LocalDateTime.now());
                    orderRepository.save(order);
                    return order;
                }
            }
        }
        throw ShopNotFoundError.create();
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Order cancelOrder(Order order) {
        if (order.getStatus() == OrderStatus.QUEUED) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setQueue(null);
            orderRepository.save(order);
        }
        return order;
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Optional<Order> queueNextOrder(Shop shop) {
        return orderRepository.findByShopIdAndStatusAndQueueIdIsNull(shop.getId(), OrderStatus.ORDERED)
                .stream().min(Comparator.comparing(Order::getOrderedTime))
                .map(this::queueOrder);
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Order queueOrder(Order order) {
        if (order.getStatus() == OrderStatus.ORDERED) {
            ShopQueue availQueue = order.getShop().getQueues().stream().filter(q -> {
                        List<Order> inQueueOrders = orderRepository.findByQueueIdAndQueueIndexGreaterThanEqual(q.getId(), q.getCurrentIndex());
                        q.setOrdersInQueue(inQueueOrders);
                        return inQueueOrders.size() < q.getMaxQuantity();
                    }).min(Comparator.comparingInt(q -> q.getOrdersInQueue().size()))
                    .orElseThrow(NoAvailableQueueError::new);
            order.setQueue(availQueue);
            orderRepository.findByQueueIdAndQueueIndexGreaterThanEqual(availQueue.getId(), availQueue.getCurrentIndex());
            order.setQueueIndex(
                    availQueue.getOrdersInQueue().stream()
                            .max(Comparator.comparingLong(Order::getQueueIndex))
                            .map(Order::getQueueIndex).orElse(availQueue.getCurrentIndex()) + 1
            );
            order.setStatus(OrderStatus.QUEUED);
            order.setUpdatedTime(LocalDateTime.now());
            orderRepository.save(order);
        }
        return order;
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Optional<Order> processNextOrder(Shop shop) {
        ShopQueue busiestQueue = shop.getQueues().stream().peek(q -> {
                    List<Order> inQueueOrders = orderRepository.findByQueueIdAndQueueIndexGreaterThanEqual(q.getId(), q.getCurrentIndex());
                    q.setOrdersInQueue(inQueueOrders);
                }).max(Comparator.comparingInt(q -> q.getOrdersInQueue().size()))
                .orElseThrow(NoAvailableQueueError::new);
        Optional<Order> optOrder = busiestQueue.getOrdersInQueue().stream().min(Comparator.comparing(Order::getOrderedTime));
        return optOrder.map(this::processOrder);
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Order processOrder(Order order) {
        if (order.getStatus() == OrderStatus.QUEUED) {
            ShopQueue queue = order.getQueue();
            order.setStatus(OrderStatus.PROCESSING);
            order.setQueue(null);
            order.setUpdatedTime(LocalDateTime.now());
            orderRepository.save(order);
            queue.setCurrentIndex(order.getQueueIndex());
            shopQueueRepository.save(queue);
        }
        return order;
    }

    @PreAuthorize("@shopAdminChecker.correctAdmin(#order)")
    public Order completeOrder(Order order) {
        if (order.getStatus() == OrderStatus.PROCESSING) {
            order.setStatus(OrderStatus.SERVED);
            order.setUpdatedTime(LocalDateTime.now());
            orderRepository.save(order);
        }
        return order;
    }
}
