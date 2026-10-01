package dao;

import database.DatabaseConnection;
import model.Witness;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WitnessDAO {

    // Add a new witness to the database
    public boolean addWitness(Witness witness) {

        String sql = "INSERT INTO witnesses " +
                "(name, phone, statement) " +
                "VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, witness.getName());
            statement.setString(2, witness.getPhone());
            statement.setString(3, witness.getStatement());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Get all witnesses from the database
    public List<Witness> getAllWitnesses() {

        List<Witness> witnesses = new ArrayList<>();

        String sql = "SELECT * FROM witnesses";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Witness witness = new Witness();

                witness.setPersonId(
                        resultSet.getInt("witness_id")
                );

                witness.setName(
                        resultSet.getString("name")
                );

                witness.setPhone(
                        resultSet.getString("phone")
                );

                witness.setStatement(
                        resultSet.getString("statement")
                );

                witnesses.add(witness);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return witnesses;
    }
}