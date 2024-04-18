package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Елизавета", "Симонова", (byte) 32);
        userService.saveUser("Кирилл", "Зюзьников", (byte) 23);
        userService.saveUser("Наруто", "Удзумаки", (byte) 16);
        userService.saveUser("Дмитрий", "Бородач", (byte) 28);

        userService.getAllUsers();

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
