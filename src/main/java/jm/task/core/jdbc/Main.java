package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;



public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        User user1 = new User("Naruto","Uzumaki",(byte) 16);
        User user2 = new User("Sasuke","Uthiha",(byte) 17);
        User user3 = new User("Sakura","Haruno",(byte) 16);
        User user4 = new User("Hatake","Kakashi",(byte) 33);
        userService.createUsersTable();

        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());

        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
