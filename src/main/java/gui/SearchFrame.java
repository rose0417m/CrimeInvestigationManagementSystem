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

        setTitle("Search Investigation Records");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // HEADER
        // =========================

        JLabel headingLabel = new JLabel(
                "SEARCH INVESTIGATION RECORDS",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        headingLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 10, 15, 10
                )
        );

        // =========================
        // SEARCH CONTROLS
        // =========================

        JLabel searchInLabel =
                new JLabel("Search In:");

        JLabel keywordLabel =
                new JLabel("Keyword:");

        searchInLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        keywordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
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

        searchField.setPreferredSize(
                new Dimension(250, 35)
        );

        searchButton =
                new JButton("Search");

        searchButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        searchButton.setPreferredSize(
                new Dimension(110, 35)
        );

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel =
                new JPanel(new GridBagLayout());

        searchPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 40, 20, 40
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(8, 8, 8, 8);

        gbc.anchor =
                GridBagConstraints.CENTER;

        // Search In label
        gbc.gridx = 0;
        gbc.gridy = 0;

        searchPanel.add(
                searchInLabel,
                gbc
        );

        // Search type
        gbc.gridx = 1;

        searchPanel.add(
                searchTypeBox,
                gbc
        );

        // Keyword label
        gbc.gridx = 0;
        gbc.gridy = 1;

        searchPanel.add(
                keywordLabel,
                gbc
        );

        // Keyword field
        gbc.gridx = 1;

        searchPanel.add(
                searchField,
                gbc
        );

        // Search button
        gbc.gridx = 2;
        gbc.gridy = 1;

        searchPanel.add(
                searchButton,
                gbc
        );

        // =========================
        // RESULTS AREA
        // =========================

        resultArea = new JTextArea();

        resultArea.setEditable(false);

        resultArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        resultArea.setLineWrap(true);

        resultArea.setWrapStyleWord(true);

        resultArea.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 10, 10
                )
        );

        JScrollPane scrollPane =
                new JScrollPane(resultArea);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        "Search Results"
                )
        );

        // =========================
        // MAIN PANEL
        // =========================

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.add(
                searchPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // MAIN FRAME
        // =========================

        setLayout(
                new BorderLayout()
        );

        add(
                headingLabel,
                BorderLayout.NORTH
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =========================
        // BUTTON ACTION
        // =========================

        searchButton.addActionListener(
                e -> performSearch()
        );

        // Press Enter in keyword field
        searchField.addActionListener(
                e -> performSearch()
        );
    }

    // =========================
    // SEARCH
    // =========================

    private void performSearch() {

        String searchType =
                (String) searchTypeBox.getSelectedItem();

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a keyword!",
                    "Search",
                    JOptionPane.WARNING_MESSAGE
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

                statement.setString(
                        1,
                        searchPattern
                );

                statement.setString(
                        2,
                        searchPattern
                );

                if (searchType.equals("Evidence")) {

                    statement.setString(
                            3,
                            searchPattern
                    );
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

    // =========================
    // DISPLAY RESULTS
    // =========================

    private void displayResults(
            ResultSet resultSet,
            String searchType) throws Exception {

        StringBuilder results =
                new StringBuilder();

        boolean found = false;

        while (resultSet.next()) {

            found = true;

            // =========================
            // CASES
            // =========================

            if (searchType.equals("Cases")) {

                results.append("Case Number: ")
                        .append(
                                resultSet.getString(
                                        "case_number"
                                )
                        )
                        .append("\n");

                results.append("Title: ")
                        .append(
                                resultSet.getString(
                                        "title"
                                )
                        )
                        .append("\n");

                results.append("Description: ")
                        .append(
                                resultSet.getString(
                                        "description"
                                )
                        )
                        .append("\n");

                results.append("Status: ")
                        .append(
                                resultSet.getString(
                                        "status"
                                )
                        )
                        .append("\n");

                results.append("Detective: ")
                        .append(
                                resultSet.getString(
                                        "detective_name"
                                )
                        )
                        .append("\n");
            }

            // =========================
            // DETECTIVES
            // =========================

            else if (searchType.equals("Detectives")) {

                results.append("Detective ID: ")
                        .append(
                                resultSet.getInt(
                                        "detective_id"
                                )
                        )
                        .append("\n");

                results.append("Name: ")
                        .append(
                                resultSet.getString(
                                        "name"
                                )
                        )
                        .append("\n");

                results.append("Phone: ")
                        .append(
                                resultSet.getString(
                                        "phone"
                                )
                        )
                        .append("\n");

                results.append("Email: ")
                        .append(
                                resultSet.getString(
                                        "email"
                                )
                        )
                        .append("\n");

                results.append("Specialization: ")
                        .append(
                                resultSet.getString(
                                        "specialization"
                                )
                        )
                        .append("\n");
            }

            // =========================
            // SUSPECTS
            // =========================

            else if (searchType.equals("Suspects")) {

                results.append("Suspect ID: ")
                        .append(
                                resultSet.getInt(
                                        "suspect_id"
                                )
                        )
                        .append("\n");

                results.append("Name: ")
                        .append(
                                resultSet.getString(
                                        "name"
                                )
                        )
                        .append("\n");

                results.append("Age: ")
                        .append(
                                resultSet.getInt(
                                        "age"
                                )
                        )
                        .append("\n");

                results.append("Gender: ")
                        .append(
                                resultSet.getString(
                                        "gender"
                                )
                        )
                        .append("\n");

                results.append("Address: ")
                        .append(
                                resultSet.getString(
                                        "address"
                                )
                        )
                        .append("\n");

                results.append("Phone: ")
                        .append(
                                resultSet.getString(
                                        "phone"
                                )
                        )
                        .append("\n");
            }

            // =========================
            // WITNESSES
            // =========================

            else if (searchType.equals("Witnesses")) {

                results.append("Witness ID: ")
                        .append(
                                resultSet.getInt(
                                        "witness_id"
                                )
                        )
                        .append("\n");

                results.append("Name: ")
                        .append(
                                resultSet.getString(
                                        "name"
                                )
                        )
                        .append("\n");

                results.append("Phone: ")
                        .append(
                                resultSet.getString(
                                        "phone"
                                )
                        )
                        .append("\n");

                results.append("Statement: ")
                        .append(
                                resultSet.getString(
                                        "statement"
                                )
                        )
                        .append("\n");
            }

            // =========================
            // EVIDENCE
            // =========================

            else if (searchType.equals("Evidence")) {

                results.append("Evidence ID: ")
                        .append(
                                resultSet.getInt(
                                        "evidence_id"
                                )
                        )
                        .append("\n");

                results.append("Case ID: ")
                        .append(
                                resultSet.getInt(
                                        "case_id"
                                )
                        )
                        .append("\n");

                results.append("Evidence Type: ")
                        .append(
                                resultSet.getString(
                                        "evidence_type"
                                )
                        )
                        .append("\n");

                results.append("Description: ")
                        .append(
                                resultSet.getString(
                                        "description"
                                )
                        )
                        .append("\n");

                results.append("Location Found: ")
                        .append(
                                resultSet.getString(
                                        "location_found"
                                )
                        )
                        .append("\n");

                results.append("Date Collected: ")
                        .append(
                                resultSet.getDate(
                                        "date_collected"
                                )
                        )
                        .append("\n");
            }

            results.append(
                    "\n--------------------------------\n\n"
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

        resultArea.setCaretPosition(0);
    }
}