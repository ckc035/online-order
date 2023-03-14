package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.Cart;
import com.laioffer.onlineorder.entity.OrderItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// remove a/all items in cart
@Repository
public class CartDao {

    @Autowired
    private SessionFactory sessionFactory;


    public void removeCartItem(int orderItemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            OrderItem cartItem = session.get(OrderItem.class, orderItemId);
            // orderItem to find cart -> delete this item from the orderItem list
            Cart cart = cartItem.getCart();
            cart.getOrderItemList().remove(cartItem);

            // should use transaction to ensure unity of deletion
            session.beginTransaction();
            session.delete(cartItem);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }

    public void removeAllCartItems(Cart cart) {
        for (OrderItem item : cart.getOrderItemList()) {
            removeCartItem(item.getId());
        }
    }
}

