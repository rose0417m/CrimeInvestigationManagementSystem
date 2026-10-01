package dao;

import database.DatabaseConnection;
import model.Suspect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SuspectDAO {

    // Add a new suspect to the database
    public boolean addSuspect(Suspect suspect) {

        String sql = "INSERT INTO suspects " +
                "(name, age, gender, address, phone) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, suspect.getName());
            statement.setInt(2, suspect.getAge());
            statement.setString(3, suspect.getGender());
            statement.setString(4, suspect.getAddress());
            statement.setString(5, suspect.getPhone());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Get all suspects from the database
    public List<Suspect> getAllSuspects() {

        List<Suspect> suspects = new ArrayList<>();

        String sql = "SELECT * FROM suspects";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Suspect suspect = new Suspect();

                suspect.setPersonId(
                        resultSet.getInt("suspect_id")
                );

                suspect.setName(
                        resultSet.getString("name")
                );

                suspect.setAge(
                        resultSet.getInt("age")
                );

                suspect.setGender(
                        resultSet.getString("gender")
                );

                suspect.setAddress(
                        resultSet.getString("address")
                );

                suspect.setPhone(
                        resultSet.getString("phone")
                );

                suspects.add(suspect);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return suspects;
    }
}