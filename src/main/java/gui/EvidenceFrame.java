package gui;

import dao.CaseDAO;
import dao.EvidenceDAO;
import model.Case;
import model.Evidence;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EvidenceFrame extends JFrame {

    private JComboBox<String> caseBox;
    private JTextField typeField;
    private JTextArea descriptionArea;
    private JTextField locationField;

    private JButton addButton;
    private JButton viewButton;

    private List<Case> cases;

    public EvidenceFrame() {

        setTitle("Evidence Management");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Evidence Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        headingLabel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 10, 10)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 50, 20, 50)
        );

        // Case dropdown
        caseBox = new JComboBox<>();

        loadCases();

        typeField = new JTextField();

        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        locationField = new JTextField();

        formPanel.add(new JLabel("Case:"));
        formPanel.add(caseBox);

        formPanel.add(new JLabel("Evidence Type:"));
        formPanel.add(typeField);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        formPanel.add(new JLabel("Location Found:"));
        formPanel.add(locationField);

        addButton = new JButton("Add Evidence");
        viewButton = new JButton("View Evidence");

        addButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        viewButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 15, 10)
        );

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addEvidence());

        viewButton.addActionListener(e -> viewEvidence());

        locationField.addActionListener(e -> addEvidence());
    }

    private void loadCases() {

        CaseDAO caseDAO = new CaseDAO();

        cases = caseDAO.getAllCases();

        if (cases.isEmpty()) {

            caseBox.addItem("No cases available");

            return;
        }

        for (Case caseData : cases) {

            caseBox.addItem(
                    caseData.getCaseId()
                            + " - "
                            + caseData.getCaseNumber()
                            + " - "
                            + caseData.getTitle()
            );
        }
    }

    private void addEvidence() {

        String type = typeField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String location =
                locationField.getText().trim();

        if (cases.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cases are available. Please add a case first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (type.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Evidence Type is required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int selectedIndex =
                caseBox.getSelectedIndex();

        if (selectedIndex < 0 ||
                selectedIndex >= cases.size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a case.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Case selectedCase =
                cases.get(selectedIndex);

        int caseId =
                selectedCase.getCaseId();

        Evidence evidence = new Evidence();

        evidence.setCaseId(caseId);
        evidence.setType(type);
        evidence.setDescription(description);
        evidence.setLocationFound(location);
        evidence.setDateCollected(LocalDate.now());

        EvidenceDAO evidenceDAO =
                new EvidenceDAO();

        boolean success =
                evidenceDAO.addEvidence(evidence);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Evidence added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

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

        EvidenceDAO evidenceDAO =
                new EvidenceDAO();

        List<Evidence> evidenceList =
                evidenceDAO.getAllEvidence();

        if (evidenceList.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No evidence found."
            );

            return;
        }

        StringBuilder details =
                new StringBuilder();

        details.append("EVIDENCE RECORDS\n");
        details.append("================================\n\n");

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

        JTextArea textArea =
                new JTextArea(details.toString());

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(550, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Evidence",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}