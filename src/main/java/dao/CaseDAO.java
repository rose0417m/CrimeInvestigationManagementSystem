package dao;

import database.DatabaseConnection;
import model.Case;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CaseDAO {

    // Add a new case to the database
    public boolean addCase(Case caseData) {

        String sql = "INSERT INTO cases " +
                "(case_number, title, description, status, date_created, detective_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, caseData.getCaseNumber());
            statement.setString(2, caseData.getTitle());
            statement.setString(3, caseData.getDescription());
            statement.setString(4, caseData.getStatus());

            statement.setDate(
                    5,
                    java.sql.Date.valueOf(caseData.getDateCreated())
            );

            statement.setInt(6, caseData.getDetectiveId());

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }


    // Get all cases from the database
    public List<Case> getAllCases() {

        List<Case> cases = new ArrayList<>();

        String sql = "SELECT * FROM cases";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Case caseData = new Case();

                caseData.setCaseId(
                        resultSet.getInt("case_id")
                );

                caseData.setCaseNumber(
                        resultSet.getString("case_number")
                );

                caseData.setTitle(
                        resultSet.getString("title")
                );

                caseData.setDescription(
                        resultSet.getString("description")
                );

                caseData.setStatus(
                        resultSet.getString("status")
                );

                if (resultSet.getDate("date_created") != null) {

                    caseData.setDateCreated(
                            resultSet.getDate("date_created")
                                    .toLocalDate()
                    );
                }

                caseData.setDetectiveId(
                        resultSet.getInt("detective_id")
                );

                cases.add(caseData);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return cases;
    }
}