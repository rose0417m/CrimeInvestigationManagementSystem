package dao;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CaseLinkDAO {

    // Link an existing suspect to a case
    public boolean linkSuspectToCase(int caseId, int suspectId) {

        String sql =
                "INSERT IGNORE INTO case_suspects " +
                        "(case_id, suspect_id) VALUES (?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, caseId);
            statement.setInt(2, suspectId);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Link an existing witness to a case
    public boolean linkWitnessToCase(int caseId, int witnessId) {

        String sql =
                "INSERT IGNORE INTO case_witnesses " +
                        "(case_id, witness_id) VALUES (?, ?)";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, caseId);
            statement.setInt(2, witnessId);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all suspects linked to a particular case
    public List<String> getSuspectsForCase(int caseId) {

        List<String> suspects = new ArrayList<>();

        String sql =
                "SELECT s.suspect_id, s.name, s.age, " +
                        "s.gender, s.address, s.phone " +
                        "FROM suspects s " +
                        "INNER JOIN case_suspects cs " +
                        "ON s.suspect_id = cs.suspect_id " +
                        "WHERE cs.case_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, caseId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    String details =
                            "Suspect ID: " + rs.getInt("suspect_id") +
                                    "\nName: " + rs.getString("name") +
                                    "\nAge: " + rs.getInt("age") +
                                    "\nGender: " + rs.getString("gender") +
                                    "\nAddress: " + rs.getString("address") +
                                    "\nPhone: " + rs.getString("phone");

                    suspects.add(details);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return suspects;
    }

    // Get all witnesses linked to a particular case
    public List<String> getWitnessesForCase(int caseId) {

        List<String> witnesses = new ArrayList<>();

        String sql =
                "SELECT w.witness_id, w.name, w.phone, w.statement " +
                        "FROM witnesses w " +
                        "INNER JOIN case_witnesses cw " +
                        "ON w.witness_id = cw.witness_id " +
                        "WHERE cw.case_id = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, caseId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {

                    String details =
                            "Witness ID: " + rs.getInt("witness_id") +
                                    "\nName: " + rs.getString("name") +
                                    "\nPhone: " + rs.getString("phone") +
                                    "\nStatement: " + rs.getString("statement");

                    witnesses.add(details);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return witnesses;
    }
}
