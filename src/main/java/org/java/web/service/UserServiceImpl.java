package org.java.web.service;

import org.java.web.dao.UserDao;
import org.java.web.dao.UserDaoImpl;
import org.java.web.model.Role;
import org.java.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void createNewUser(User user) {
        if(user.getRoles().size()==0){
            System.out.println("\nChecking roles in the createNewUser(User user).method in the UserServiceImpl.class");
            System.out.println("adding default ROLE_USER role because null roles received = ");
            user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        }
        userDao.createNewUser(user);
    }

    @Transactional
    @Override
    public User readUser(long id) {
        return userDao.readUser(id);
    }

    @Transactional
    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    public User getUser(String login) {
        return userDao.getUser(login);
    }

    @Transactional
    @Override
    public List<User> usersList() {
        return userDao.usersList();
    }

    @Transactional
    @Override
    public List<Role> rolesList() {
        return userDao.rolesList();
    }


}
