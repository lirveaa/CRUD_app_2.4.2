package org.java.web.dao;

import org.java.web.model.Role;
import org.java.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    @Autowired
    EntityManager em;

    @Override
    public void createNewUser(User user) {
        Set<Role> eSet = user.getRoles();
        if (eSet.size() == 0) {
            System.out.println("User don't have a roles. I added ROLE_USER");
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        user = em.merge(user);
        System.out.println(user + " - added to database");
    }

    @Override
    public User readUser(long id) {
        return (User) em.createQuery("from User where id=:id")
                .setParameter("id",id)
                .getSingleResult();
    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void deleteUser(long id) {
        //User user = readUser(id);
        //em.remove(user);
        em.createQuery("delete from User u where u.id =:id")
                .setParameter("id",id).executeUpdate();
    }

    @Override
    public User getUser(String login) {
        TypedQuery<User> query = em.createQuery(
                "select u from User u where u.login = :login",
                User.class);
        query.setParameter("login", login);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<User> usersList() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u",User.class);
        return query.getResultList();
    }

    @Override
    public List<Role> rolesList() {
        TypedQuery <Role> query =
                em.createQuery("SELECT u FROM Role u", Role.class);
        return query.getResultList();
    }

}
