package gui;

import dao.EvidenceDAO;
import model.Evidence;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EvidenceFrame extends JFrame {

    private JTextField caseIdField;
    private JTextField typeField;
    private JTextArea descriptionArea;
    private JTextField locationField;

    private JButton addButton;
    private JButton viewButton;

    public EvidenceFrame() {

        setTitle("Evidence Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Evidence Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 20, 40)
        );

        caseIdField = new JTextField();
        typeField = new JTextField();
        descriptionArea = new JTextArea(3, 20);
        locationField = new JTextField();

        formPanel.add(new JLabel("Case ID:"));
        formPanel.add(caseIdField);

        formPanel.add(new JLabel("Evidence Type:"));
        formPanel.add(typeField);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Location Found:"));
        formPanel.add(locationField);

        addButton = new JButton("Add Evidence");
        viewButton = new JButton("View Evidence");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addEvidence());

        viewButton.addActionListener(e -> viewEvidence());
    }

    private void addEvidence() {

        String caseIdText = caseIdField.getText();
        String type = typeField.getText();
        String description = descriptionArea.getText();
        String location = locationField.getText();

        if (caseIdText.isEmpty() || type.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case ID and Evidence Type are required!"
            );

            return;
        }

        int caseId;

        try {

            caseId = Integer.parseInt(caseIdText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case ID must be a number!"
            );

            return;
        }

        Evidence evidence = new Evidence();

        evidence.setCaseId(caseId);
        evidence.setType(type);
        evidence.setDescription(description);
        evidence.setLocationFound(location);
        evidence.setDateCollected(LocalDate.now());

        EvidenceDAO evidenceDAO = new EvidenceDAO();

        boolean success = evidenceDAO.addEvidence(evidence);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Evidence added successfully!"
            );

            caseIdField.setText("");
            typeField.setText("");
            descriptionArea.setText("");
            locationField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add evidence!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewEvidence() {

        EvidenceDAO evidenceDAO = new EvidenceDAO();

        List<Evidence> evidenceList =
                evidenceDAO.getAllEvidence();

        if (evidenceList.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No evidence found."
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        for (Evidence evidence : evidenceList) {

            details.append("Evidence ID: ")
                    .append(evidence.getEvidenceId())
                    .append("\n");

            details.append("Case ID: ")
                    .append(evidence.getCaseId())
                    .append("\n");

            details.append("Evidence Type: ")
                    .append(evidence.getType())
                    .append("\n");

            details.append("Description: ")
                    .append(evidence.getDescription())
                    .append("\n");

            details.append("Location Found: ")
                    .append(evidence.getLocationFound())
                    .append("\n");

            details.append("Date Collected: ")
                    .append(evidence.getDateCollected())
                    .append("\n");

            details.append("--------------------------------\n");
        }

        JTextArea textArea = new JTextArea(
                details.toString()
        );

        textArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Evidence",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}