package com.laioffer.onlineorder.dao;

import com.laioffer.onlineorder.entity.Customer;
import org.springframework.stereotype.Repository;
import com.laioffer.onlineorder.entity.Authorities;
import com.laioffer.onlineorder.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//Repository includes @component + spring knows this class communicates with db
@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;
    // write
    public void signUp(Customer customer) {
        Authorities authorities = new Authorities();
        // related to login security: if not a Role_USER -> redirect you to sign up
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmail(customer.getEmail());

        Session session = null;
        try {
            // openSession (from SessionFactory in AppConfig)
            session = sessionFactory.openSession();
            // We change multiple tables, need to ensure all tables to have records.
            // transaction as a unit of work (all or nothing)
            session.beginTransaction();
            session.save(authorities);
            session.save(customer); // cascade in customer -> will create cart also
            session.getTransaction().commit();

        } catch (Exception ex) { // if any steps above have exception
            ex.printStackTrace();
            // if session is null, exception in openSession(), no need to roll back
            if (session != null) session.getTransaction().rollback();
        } finally {
            // if session == null, it is not even opened, no need to close
            if (session != null) {
                session.close();
            }
        }
    }

    // read
    public Customer getCustomer(String email) {
        Customer customer = null;
        // try with resource (JAVA 8) both way works
//        try (Session session = sessionFactory.openSession()) {
//            customer = session.get(Customer.class, email);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
        Session session = null;

        try {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, email);
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

}

