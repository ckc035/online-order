package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.OrderItemDao;
import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service // --> must have, else dep injection cannot find this bean
public class OrderItemService {

    @Autowired
    private MenuInfoService menuInfoService;

    // from customerService to find customerID（email） -> find cardID
    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderItemDao orderItemDao;

    public void saveOrderItem(int menuId) {
        final OrderItem orderItem = new OrderItem();
        final MenuItem menuItem = menuInfoService.getMenuItem(menuId);

        // need to use SecurityContextHolder to retrieve logged user email（name）
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        Customer customer = customerService.getCustomer(username);

        // save orderItem info
        orderItem.setMenuItem(menuItem);
        orderItem.setCart(customer.getCart());
        orderItem.setQuantity(1);
        orderItem.setPrice(menuItem.getPrice());

        orderItemDao.save(orderItem);
    }
}

