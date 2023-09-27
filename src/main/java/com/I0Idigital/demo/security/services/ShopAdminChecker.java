package com.I0Idigital.demo.security.services;

import com.I0Idigital.demo.domain.Order;
import com.I0Idigital.demo.domain.Shop;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("shopAdminChecker")
public class ShopAdminChecker {
    public boolean correctAdmin(Shop shop) {
        if (shop == null) {
            return true;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Long currentUserId = ((UserDetailsImpl) principal).getId();
            return shop.getAdmins().stream().anyMatch(admin -> currentUserId.equals(admin.getId()));
        }
        return false;
    }

    public boolean correctAdmin(Order order) {
        if (order == null) {
            return true;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            Long currentUserId = ((UserDetailsImpl) principal).getId();
            return order.getShop().getAdmins().stream().anyMatch(admin -> currentUserId.equals(admin.getId()));
        }
        return false;
    }
}
