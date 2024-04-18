package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
public UserDaoJDBCImpl() {

}

@Override
public void createUsersTable() throws SQLException {
    String sql = "CREATE TABLE IF NOT EXISTS Users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, age INT NOT NULL)";

    try (Connection connection = Util.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)
    ) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public void dropUsersTable() throws SQLException {
    String sql = "DROP TABLE Users";

    try (Connection connection = Util.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)
    ) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public void saveUser(String name, String lastName, byte age) throws SQLException {
    String sql = "INSERT INTO USERS(name, lastName,age) VALUES (?,?,?)";

    try (Connection connection = Util.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql)
    ) {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lastName);
        preparedStatement.setByte(3, age);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public void removeUserById(long id) throws SQLException {
    String sql = "DELETE FROM Users WHERE id = ?";

    try (Connection connection = Util.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ) {
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

@Override
public List<User> getAllUsers() throws SQLException {
    List<User> list = new ArrayList<>();
    String sql = "SELECT * FROM Users";

    try (Connection connection = Util.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
    ) {
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String lastName = resultSet.getString("lastName");
            byte age = resultSet.getByte("age");

            list.add(new User(id, name, lastName, age));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

@Override
public void cleanUsersTable() throws SQLException {
    String sql = "DELETE FROM Users";      //или "TRUNCATE TABLE Users"

    try (Connection connection = Util.getConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(sql);
    ) {
        preparedStatement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }

}
}
