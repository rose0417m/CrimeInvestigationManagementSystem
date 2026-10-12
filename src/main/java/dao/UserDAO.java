
package dao;

import database.DatabaseConnection;
import model.User;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {

    // Login using a username and password
    public User login(String username, String password) {

        String sql =
                "SELECT * FROM users WHERE username = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                if (!resultSet.next()) {
                    return null;
                }

                String storedPassword =
                        resultSet.getString("password");

                boolean passwordMatches;

                // Support existing accounts that still have
                // plain-text passwords during migration.
                if (PasswordUtil.isHashed(storedPassword)) {

                    passwordMatches =
                            PasswordUtil.verifyPassword(
                                    password,
                                    storedPassword
                            );

                } else {

                    passwordMatches =
                            password.equals(storedPassword);

                    // Upgrade the password after a successful
                    // login with a legacy plain-text password.
                    if (passwordMatches) {

                        String hashedPassword =
                                PasswordUtil.hashPassword(password);

                        String updateSql =
                                "UPDATE users SET password = ? "
                                        + "WHERE user_id = ?";

                        try (PreparedStatement updateStatement =
                                     connection.prepareStatement(updateSql)) {

                            updateStatement.setString(
                                    1, hashedPassword
                            );

                            updateStatement.setInt(
                                    2,
                                    resultSet.getInt("user_id")
                            );

                            updateStatement.executeUpdate();
                        }
                    }
                }

                if (!passwordMatches) {
                    return null;
                }

                User user = new User();

                user.setUserId(
                        resultSet.getInt("user_id")
                );

                user.setUsername(
                        resultSet.getString("username")
                );

                user.setPassword(
                        storedPassword
                );

                user.setRole(
                        resultSet.getString("role")
                );

                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // Create an account with the restricted Investigator role.
    // This method never creates an Admin account.
    public boolean createInvestigatorAccount(
            String username,
            String password
    ) {

        String sql =
                "INSERT INTO users (username, password, role) "
                        + "VALUES (?, ?, ?)";

        try {
            String hashedPassword =
                    PasswordUtil.hashPassword(password);

            try (Connection connection =
                         DatabaseConnection.getConnection();
                 PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                statement.setString(1, username);
                statement.setString(2, hashedPassword);
                statement.setString(3, "Investigator");

                return statement.executeUpdate() == 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Retrieve all users without exposing their passwords
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        String sql =
                "SELECT user_id, username, role "
                        + "FROM users ORDER BY user_id";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setUserId(resultSet.getInt("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(resultSet.getString("role"));

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
