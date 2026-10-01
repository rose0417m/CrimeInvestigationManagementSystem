package gui;

import dao.CaseDAO;
import model.Case;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CaseFrame extends JFrame {

    private JTextField caseNumberField;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusBox;
    private JTextField detectiveIdField;

    private JButton addButton;
    private JButton viewButton;

    public CaseFrame() {

        setTitle("Case Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Case Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        );

        caseNumberField = new JTextField();
        titleField = new JTextField();
        descriptionArea = new JTextArea(3, 20);

        statusBox = new JComboBox<>(
                new String[]{"Open", "Closed", "Pending"}
        );

        detectiveIdField = new JTextField();

        formPanel.add(new JLabel("Case Number:"));
        formPanel.add(caseNumberField);

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusBox);

        formPanel.add(new JLabel("Detective ID:"));
        formPanel.add(detectiveIdField);

        addButton = new JButton("Add Case");
        viewButton = new JButton("View Cases");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addCase());

        viewButton.addActionListener(e -> viewCases());
    }


    private void addCase() {

        String caseNumber = caseNumberField.getText();
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String status = (String) statusBox.getSelectedItem();
        String detectiveText = detectiveIdField.getText();

        if (caseNumber.isEmpty() || title.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case Number and Title are required!"
            );

            return;
        }

        int detectiveId;

        try {

            detectiveId = Integer.parseInt(detectiveText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Detective ID must be a number!"
            );

            return;
        }

        Case newCase = new Case();

        newCase.setCaseNumber(caseNumber);
        newCase.setTitle(title);
        newCase.setDescription(description);
        newCase.setStatus(status);
        newCase.setDateCreated(LocalDate.now());
        newCase.setDetectiveId(detectiveId);

        CaseDAO caseDAO = new CaseDAO();

        boolean success = caseDAO.addCase(newCase);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case added successfully!"
            );

            caseNumberField.setText("");
            titleField.setText("");
            descriptionArea.setText("");
            detectiveIdField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add case!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private void viewCases() {

        CaseDAO caseDAO = new CaseDAO();

        List<Case> cases = caseDAO.getAllCases();

        if (cases.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cases found."
            );

            return;
        }

        StringBuilder caseDetails = new StringBuilder();

        for (Case caseData : cases) {

            caseDetails.append("Case ID: ")
                    .append(caseData.getCaseId())
                    .append("\n");

            caseDetails.append("Case Number: ")
                    .append(caseData.getCaseNumber())
                    .append("\n");

            caseDetails.append("Title: ")
                    .append(caseData.getTitle())
                    .append("\n");

            caseDetails.append("Description: ")
                    .append(caseData.getDescription())
                    .append("\n");

            caseDetails.append("Status: ")
                    .append(caseData.getStatus())
                    .append("\n");

            caseDetails.append("Date Created: ")
                    .append(caseData.getDateCreated())
                    .append("\n");

            caseDetails.append("Detective ID: ")
                    .append(caseData.getDetectiveId())
                    .append("\n");

            caseDetails.append("--------------------------------\n");
        }

        JTextArea textArea = new JTextArea(
                caseDetails.toString()
        );

        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Cases",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}