package dao;

import database.DatabaseConnection;
import model.Evidence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EvidenceDAO {

    // Add new evidence to the database
    public boolean addEvidence(Evidence evidence) {

        String sql = "INSERT INTO evidence " +
                "(case_id, evidence_type, description, location_found, date_collected) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, evidence.getCaseId());
            statement.setString(2, evidence.getType());
            statement.setString(3, evidence.getDescription());
            statement.setString(4, evidence.getLocationFound());

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(
                            evidence.getDateCollected()
                    )
            );

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }

    // Get all evidence from the database
    public List<Evidence> getAllEvidence() {

        List<Evidence> evidenceList = new ArrayList<>();

        String sql = "SELECT * FROM evidence";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Evidence evidence = new Evidence();

                evidence.setEvidenceId(
                        resultSet.getInt("evidence_id")
                );

                evidence.setCaseId(
                        resultSet.getInt("case_id")
                );

                evidence.setType(
                        resultSet.getString("evidence_type")
                );

                evidence.setDescription(
                        resultSet.getString("description")
                );

                evidence.setLocationFound(
                        resultSet.getString("location_found")
                );

                if (resultSet.getDate("date_collected") != null) {

                    evidence.setDateCollected(
                            resultSet.getDate("date_collected")
                                    .toLocalDate()
                    );
                }

                evidenceList.add(evidence);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return evidenceList;
    }
}