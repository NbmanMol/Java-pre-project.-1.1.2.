package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Statement statement;


    public UserDaoJDBCImpl() {
        try {
            statement = Util.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS Users(id int auto_increment,name VARCHAR(45) null,last VARCHAR(45) null,age int null, constraint Users_pk primary key (id));");
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try {
            statement.execute("drop table if exists Users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            statement.executeUpdate("INSERT INTO Users (name, last, age) VALUES ('" + name + "', '" + lastName + "', " + age + ")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void removeUserById(long id) {
        try {
            statement.executeUpdate(" DELETE FROM Users where id = " + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * from Users;");
            while (resultSet.next()) {
                int age = resultSet.getInt("age");
                String name = resultSet.getString("name");
                String last = resultSet.getString("last");
                User user = new User(name, last, (byte) age);
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        try {
            statement.executeUpdate("DELETE from Users ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
