package com.psilonsoft.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.psilonsoft.entities.User;

@Repository
public class UserDao {

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    /**
     * Returns user by specified email or null if not found
     */
    @Transactional
    public User getUserByEmail(final String email) {
        try {
            return (User) em.createQuery("from User where email = :email")
                    .setParameter("email", email).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Transactional
    public List<User> getAllUsers() {
        return em.createQuery("from User").getResultList();
    }

    /**
     * For some reasons, someone left plain JDBC implementation - which is very very bad.
     * 
     * @param lastName
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> filterUsersByEmail(final String lastName) {
        return em.createQuery("from User where email like :lastName")
                .setParameter("lastName", "%" + lastName + "%").getResultList();
    }

    @Transactional
    public void save(final User user) {
        em.persist(user);
    }
}
