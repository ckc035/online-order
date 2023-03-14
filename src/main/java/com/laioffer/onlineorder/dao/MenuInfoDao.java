package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.MenuItem;
import com.laioffer.onlineorder.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MenuInfoDao {

    @Autowired
    private SessionFactory sessionFactory;
    // get restaurant info
    public List<Restaurant> getRestaurants() {
        try (Session session = sessionFactory.openSession()) {
            //CriteriaBuilder provides apis for sql query of the DB
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Restaurant> criteria = builder.createQuery(Restaurant.class);
            criteria.from(Restaurant.class);
            // use a session to find info in db
            return session.createQuery(criteria).getResultList();
            // createQuery(criteria) == sql: select * from Restaurant where name = ''
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }


    // client select restaurant menu -> sends restaurantId -> backend returns menuItems
    public List<MenuItem> getAllMenuItem(int restaurantId) {
        try (Session session = sessionFactory.openSession()) {
            // just need to find the restaurant object -> eager fetching -> already has list<MenuItem>
            Restaurant restaurant = session.get(Restaurant.class, restaurantId);
            if (restaurant != null) {
                return restaurant.getMenuItemList();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }

    // return menu item info
    public MenuItem getMenuItem(int menuItemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(MenuItem.class, menuItemId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
