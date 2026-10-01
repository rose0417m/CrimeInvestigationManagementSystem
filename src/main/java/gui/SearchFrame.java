package gui;

import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchFrame extends JFrame {

    private JComboBox<String> searchTypeBox;
    private JTextField searchField;
    private JTextArea resultArea;

    private JButton searchButton;

    public SearchFrame() {

        setTitle("Search");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Search Investigation Records",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        searchTypeBox = new JComboBox<>(
                new String[]{
                        "Cases",
                        "Detectives",
                        "Suspects",
                        "Witnesses",
                        "Evidence"
                }
        );

        searchField = new JTextField();

        searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel(
                new GridLayout(2, 2, 10, 10)
        );

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        searchPanel.add(
                new JLabel("Search In:")
        );

        searchPanel.add(searchTypeBox);

        searchPanel.add(
                new JLabel("Keyword:")
        );

        searchPanel.add(searchField);

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(searchButton);

        resultArea = new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        setLayout(new BorderLayout());

        add(
                headingLabel,
                BorderLayout.NORTH
        );

        add(
                searchPanel,
                BorderLayout.CENTER
        );

        add(
                scrollPane,
                BorderLayout.SOUTH
        );

        add(
                buttonPanel,
                BorderLayout.EAST
        );

        searchButton.addActionListener(
                e -> performSearch()
        );
    }

    private void performSearch() {

        String searchType =
                (String) searchTypeBox.getSelectedItem();

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a keyword!"
            );

            return;
        }

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            String sql = "";

            switch (searchType) {

                case "Cases":
                    sql =
                            "SELECT c.case_number, c.title, " +
                                    "c.description, c.status, " +
                                    "d.name AS detective_name " +
                                    "FROM cases c " +
                                    "LEFT JOIN detectives d " +
                                    "ON c.detective_id = d.detective_id " +
                                    "WHERE c.case_number LIKE ? " +
                                    "OR c.title LIKE ?";
                    break;

                case "Detectives":
                    sql =
                            "SELECT * FROM detectives " +
                                    "WHERE name LIKE ? " +
                                    "OR specialization LIKE ?";
                    break;

                case "Suspects":
                    sql =
                            "SELECT * FROM suspects " +
                                    "WHERE name LIKE ? " +
                                    "OR gender LIKE ?";
                    break;

                case "Witnesses":
                    sql =
                            "SELECT * FROM witnesses " +
                                    "WHERE name LIKE ? " +
                                    "OR statement LIKE ?";
                    break;

                case "Evidence":
                    sql =
                            "SELECT * FROM evidence " +
                                    "WHERE evidence_type LIKE ? " +
                                    "OR description LIKE ? " +
                                    "OR location_found LIKE ?";
                    break;
            }

            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {

                String searchPattern =
                        "%" + keyword + "%";

                statement.setString(1, searchPattern);
                statement.setString(2, searchPattern);

                if (searchType.equals("Evidence")) {
                    statement.setString(3, searchPattern);
                }

                try (ResultSet resultSet =
                             statement.executeQuery()) {

                    displayResults(
                            resultSet,
                            searchType
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Search failed!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void displayResults(
            ResultSet resultSet,
            String searchType) throws Exception {

        StringBuilder results =
                new StringBuilder();

        boolean found = false;

        while (resultSet.next()) {

            found = true;

            if (searchType.equals("Cases")) {

                results.append("Case Number: ")
                        .append(resultSet.getString("case_number"))
                        .append("\n");

                results.append("Title: ")
                        .append(resultSet.getString("title"))
                        .append("\n");

                results.append("Description: ")
                        .append(resultSet.getString("description"))
                        .append("\n");

                results.append("Status: ")
                        .append(resultSet.getString("status"))
                        .append("\n");

                results.append("Detective: ")
                        .append(resultSet.getString("detective_name"))
                        .append("\n");
            }

            else if (searchType.equals("Detectives")) {

                results.append("Detective ID: ")
                        .append(resultSet.getInt("detective_id"))
                        .append("\n");

                results.append("Name: ")
                        .append(resultSet.getString("name"))
                        .append("\n");

                results.append("Phone: ")
                        .append(resultSet.getString("phone"))
                        .append("\n");

                results.append("Email: ")
                        .append(resultSet.getString("email"))
                        .append("\n");

                results.append("Specialization: ")
                        .append(resultSet.getString("specialization"))
                        .append("\n");
            }

            else if (searchType.equals("Suspects")) {

                results.append("Suspect ID: ")
                        .append(resultSet.getInt("suspect_id"))
                        .append("\n");

                results.append("Name: ")
                        .append(resultSet.getString("name"))
                        .append("\n");

                results.append("Age: ")
                        .append(resultSet.getInt("age"))
                        .append("\n");

                results.append("Gender: ")
                        .append(resultSet.getString("gender"))
                        .append("\n");

                results.append("Address: ")
                        .append(resultSet.getString("address"))
                        .append("\n");

                results.append("Phone: ")
                        .append(resultSet.getString("phone"))
                        .append("\n");
            }

            else if (searchType.equals("Witnesses")) {

                results.append("Witness ID: ")
                        .append(resultSet.getInt("witness_id"))
                        .append("\n");

                results.append("Name: ")
                        .append(resultSet.getString("name"))
                        .append("\n");

                results.append("Phone: ")
                        .append(resultSet.getString("phone"))
                        .append("\n");

                results.append("Statement: ")
                        .append(resultSet.getString("statement"))
                        .append("\n");
            }

            else if (searchType.equals("Evidence")) {

                results.append("Evidence ID: ")
                        .append(resultSet.getInt("evidence_id"))
                        .append("\n");

                results.append("Case ID: ")
                        .append(resultSet.getInt("case_id"))
                        .append("\n");

                results.append("Evidence Type: ")
                        .append(resultSet.getString("evidence_type"))
                        .append("\n");

                results.append("Description: ")
                        .append(resultSet.getString("description"))
                        .append("\n");

                results.append("Location Found: ")
                        .append(resultSet.getString("location_found"))
                        .append("\n");

                results.append("Date Collected: ")
                        .append(resultSet.getDate("date_collected"))
                        .append("\n");
            }

            results.append(
                    "--------------------------------\n"
            );
        }

        if (!found) {

            results.append(
                    "No matching records found."
            );
        }

        resultArea.setText(
                results.toString()
        );
    }
}