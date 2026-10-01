package dao;

import database.DatabaseConnection;
import model.Detective;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DetectiveDAO {

    // Add a new detective to the database
    public boolean addDetective(Detective detective) {

        String sql = "INSERT INTO detectives " +
                "(name, phone, email, specialization) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, detective.getName());
            statement.setString(2, detective.getPhone());
            statement.setString(3, detective.getEmail());
            statement.setString(4, detective.getSpecialization());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Get all detectives from the database
    public List<Detective> getAllDetectives() {

        List<Detective> detectives = new ArrayList<>();

        String sql = "SELECT * FROM detectives";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Detective detective = new Detective();

                detective.setPersonId(
                        resultSet.getInt("detective_id")
                );

                detective.setName(
                        resultSet.getString("name")
                );

                detective.setPhone(
                        resultSet.getString("phone")
                );

                detective.setEmail(
                        resultSet.getString("email")
                );

                detective.setSpecialization(
                        resultSet.getString("specialization")
                );

                detectives.add(detective);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return detectives;
    }
}