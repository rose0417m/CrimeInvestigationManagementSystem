//package gui;
//
//import database.DatabaseConnection;
//
//import javax.swing.*;
//import java.awt.*;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class ReportFrame extends JFrame {
//
//    private JLabel casesLabel;
//    private JLabel detectivesLabel;
//    private JLabel suspectsLabel;
//    private JLabel witnessesLabel;
//    private JLabel evidenceLabel;
//
//    private JTextArea reportArea;
//
//    public ReportFrame() {
//
//        setTitle("Crime Investigation Reports");
//        setSize(700, 600);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JLabel headingLabel = new JLabel(
//                "Crime Investigation Reports",
//                SwingConstants.CENTER
//        );
//
//        headingLabel.setFont(
//                new Font("Arial", Font.BOLD, 22)
//        );
//
//        JPanel summaryPanel = new JPanel(
//                new GridLayout(5, 1, 10, 10)
//        );
//
//        summaryPanel.setBorder(
//                BorderFactory.createEmptyBorder(20, 50, 20, 50)
//        );
//
//        casesLabel = new JLabel();
//        detectivesLabel = new JLabel();
//        suspectsLabel = new JLabel();
//        witnessesLabel = new JLabel();
//        evidenceLabel = new JLabel();
//
//        summaryPanel.add(casesLabel);
//        summaryPanel.add(detectivesLabel);
//        summaryPanel.add(suspectsLabel);
//        summaryPanel.add(witnessesLabel);
//        summaryPanel.add(evidenceLabel);
//
//        reportArea = new JTextArea();
//        reportArea.setEditable(false);
//
//        JScrollPane scrollPane = new JScrollPane(reportArea);
//
//        JButton refreshButton = new JButton("Refresh Report");
//
//        refreshButton.addActionListener(e -> generateReport());
//
//        JPanel bottomPanel = new JPanel();
//        bottomPanel.add(refreshButton);
//
//        setLayout(new BorderLayout());
//
//        add(headingLabel, BorderLayout.NORTH);
//        add(summaryPanel, BorderLayout.CENTER);
//        add(scrollPane, BorderLayout.SOUTH);
//        add(bottomPanel, BorderLayout.PAGE_END);
//
//        generateReport();
//    }
//
//    private void generateReport() {
//
//        try (Connection connection =
//                     DatabaseConnection.getConnection()) {
//
//            int totalCases = getCount(
//                    connection,
//                    "SELECT COUNT(*) FROM cases"
//            );
//
//            int totalDetectives = getCount(
//                    connection,
//                    "SELECT COUNT(*) FROM detectives"
//            );
//
//            int totalSuspects = getCount(
//                    connection,
//                    "SELECT COUNT(*) FROM suspects"
//            );
//
//            int totalWitnesses = getCount(
//                    connection,
//                    "SELECT COUNT(*) FROM witnesses"
//            );
//
//            int totalEvidence = getCount(
//                    connection,
//                    "SELECT COUNT(*) FROM evidence"
//            );
//
//            casesLabel.setText(
//                    "Total Cases: " + totalCases
//            );
//
//            detectivesLabel.setText(
//                    "Total Detectives: " + totalDetectives
//            );
//
//            suspectsLabel.setText(
//                    "Total Suspects: " + totalSuspects
//            );
//
//            witnessesLabel.setText(
//                    "Total Witnesses: " + totalWitnesses
//            );
//
//            evidenceLabel.setText(
//                    "Total Evidence: " + totalEvidence
//            );
//
//            generateCaseDetails(connection);
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Failed to generate report!",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//
//    private int getCount(
//            Connection connection,
//            String sql) throws Exception {
//
//        try (PreparedStatement statement =
//                     connection.prepareStatement(sql);
//             ResultSet resultSet =
//                     statement.executeQuery()) {
//
//            if (resultSet.next()) {
//                return resultSet.getInt(1);
//            }
//        }
//
//        return 0;
//    }
//
//    private void generateCaseDetails(
//            Connection connection) throws Exception {
//
//        String sql =
//                "SELECT c.case_number, c.title, c.status, " +
//                        "d.name AS detective_name " +
//                        "FROM cases c " +
//                        "LEFT JOIN detectives d " +
//                        "ON c.detective_id = d.detective_id";
//
//        StringBuilder details = new StringBuilder();
//
//        details.append("CASE DETAILS\n");
//        details.append("==============================\n\n");
//
//        try (PreparedStatement statement =
//                     connection.prepareStatement(sql);
//             ResultSet resultSet =
//                     statement.executeQuery()) {
//
//            while (resultSet.next()) {
//
//                details.append("Case Number: ")
//                        .append(resultSet.getString("case_number"))
//                        .append("\n");
//
//                details.append("Title: ")
//                        .append(resultSet.getString("title"))
//                        .append("\n");
//
//                details.append("Status: ")
//                        .append(resultSet.getString("status"))
//                        .append("\n");
//
//                details.append("Detective: ")
//                        .append(resultSet.getString("detective_name"))
//                        .append("\n");
//
//                details.append("------------------------------\n");
//            }
//        }
//
//        reportArea.setText(details.toString());
//    }
//}
package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportFrame extends JFrame {

    private JLabel casesLabel;
    private JLabel detectivesLabel;
    private JLabel suspectsLabel;
    private JLabel witnessesLabel;
    private JLabel evidenceLabel;

    private JTextArea reportArea;

    public ReportFrame() {

        setTitle("Crime Investigation Reports");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Crime Investigation Reports",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        // Summary panel
        JPanel summaryPanel = new JPanel(
                new GridLayout(5, 1, 5, 5)
        );

        summaryPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 50, 10, 50)
        );

        casesLabel = new JLabel();
        detectivesLabel = new JLabel();
        suspectsLabel = new JLabel();
        witnessesLabel = new JLabel();
        evidenceLabel = new JLabel();

        summaryPanel.add(casesLabel);
        summaryPanel.add(detectivesLabel);
        summaryPanel.add(suspectsLabel);
        summaryPanel.add(witnessesLabel);
        summaryPanel.add(evidenceLabel);

        // Case details area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(reportArea);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());

        contentPanel.add(
                summaryPanel,
                BorderLayout.NORTH
        );

        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // Refresh button
        JButton refreshButton =
                new JButton("Refresh Report");

        refreshButton.addActionListener(
                e -> generateReport()
        );

        JPanel bottomPanel = new JPanel();

        bottomPanel.add(refreshButton);

        // Main layout
        setLayout(new BorderLayout());

        add(
                headingLabel,
                BorderLayout.NORTH
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        generateReport();
    }

    private void generateReport() {

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            int totalCases = getCount(
                    connection,
                    "SELECT COUNT(*) FROM cases"
            );

            int totalDetectives = getCount(
                    connection,
                    "SELECT COUNT(*) FROM detectives"
            );

            int totalSuspects = getCount(
                    connection,
                    "SELECT COUNT(*) FROM suspects"
            );

            int totalWitnesses = getCount(
                    connection,
                    "SELECT COUNT(*) FROM witnesses"
            );

            int totalEvidence = getCount(
                    connection,
                    "SELECT COUNT(*) FROM evidence"
            );

            casesLabel.setText(
                    "Total Cases: " + totalCases
            );

            detectivesLabel.setText(
                    "Total Detectives: " + totalDetectives
            );

            suspectsLabel.setText(
                    "Total Suspects: " + totalSuspects
            );

            witnessesLabel.setText(
                    "Total Witnesses: " + totalWitnesses
            );

            evidenceLabel.setText(
                    "Total Evidence: " + totalEvidence
            );

            generateCaseDetails(connection);

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to generate report!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private int getCount(
            Connection connection,
            String sql) throws Exception {

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

    private void generateCaseDetails(
            Connection connection) throws Exception {

        String sql =
                "SELECT c.case_number, c.title, c.status, " +
                        "d.name AS detective_name " +
                        "FROM cases c " +
                        "LEFT JOIN detectives d " +
                        "ON c.detective_id = d.detective_id";

        StringBuilder details = new StringBuilder();

        details.append("CASE DETAILS\n");
        details.append("==============================\n\n");

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                details.append("Case Number: ")
                        .append(resultSet.getString("case_number"))
                        .append("\n");

                details.append("Title: ")
                        .append(resultSet.getString("title"))
                        .append("\n");

                details.append("Status: ")
                        .append(resultSet.getString("status"))
                        .append("\n");

                details.append("Detective: ")
                        .append(resultSet.getString("detective_name"))
                        .append("\n");

                details.append("------------------------------\n");
            }
        }

        reportArea.setText(details.toString());
    }
}