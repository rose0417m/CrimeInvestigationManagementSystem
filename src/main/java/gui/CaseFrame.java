//package gui;
//
//import dao.CaseDAO;
//import dao.DetectiveDAO;
//import model.Case;
//import model.Detective;
//
//import javax.swing.*;
//import java.awt.*;
//import java.time.LocalDate;
//import java.util.List;
//
//public class CaseFrame extends JFrame {
//
//    private JTextField caseNumberField;
//    private JTextField titleField;
//    private JTextArea descriptionArea;
//    private JComboBox<String> statusBox;
//    private JComboBox<String> detectiveBox;
//
//    private List<Detective> detectives;
//
//    private JButton addButton;
//    private JButton viewButton;
//
//    public CaseFrame() {
//
//        setTitle("Case Management");
//        setSize(650, 550);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        // HEADING
//        JLabel headingLabel = new JLabel(
//                "Case Management",
//                SwingConstants.CENTER
//        );
//
//        headingLabel.setFont(
//                new Font("Arial", Font.BOLD, 22)
//        );
//
//        headingLabel.setBorder(
//                BorderFactory.createEmptyBorder(15, 10, 10, 10)
//        );
//
//        // FORM PANEL
//        JPanel formPanel = new JPanel(
//                new GridLayout(5, 2, 10, 10)
//        );
//
//        formPanel.setBorder(
//                BorderFactory.createEmptyBorder(20, 40, 20, 40)
//        );
//
//        caseNumberField = new JTextField();
//        titleField = new JTextField();
//
//        descriptionArea = new JTextArea(4, 20);
//        descriptionArea.setLineWrap(true);
//        descriptionArea.setWrapStyleWord(true);
//
//        statusBox = new JComboBox<>(
//                new String[]{
//                        "Open",
//                        "Closed",
//                        "Pending"
//                }
//        );
//
//        detectiveBox = new JComboBox<>();
//
//        loadDetectives();
//
//        formPanel.add(
//                new JLabel("Case Number:")
//        );
//        formPanel.add(caseNumberField);
//
//        formPanel.add(
//                new JLabel("Title:")
//        );
//        formPanel.add(titleField);
//
//        formPanel.add(
//                new JLabel("Description:")
//        );
//        formPanel.add(
//                new JScrollPane(descriptionArea)
//        );
//
//        formPanel.add(
//                new JLabel("Status:")
//        );
//        formPanel.add(statusBox);
//
//        formPanel.add(
//                new JLabel("Assigned Detective:")
//        );
//        formPanel.add(detectiveBox);
//
//        // BUTTONS
//        addButton = new JButton("Add Case");
//        viewButton = new JButton("View Cases");
//
//        addButton.setFont(
//                new Font("Arial", Font.BOLD, 14)
//        );
//
//        viewButton.setFont(
//                new Font("Arial", Font.BOLD, 14)
//        );
//
//        JPanel buttonPanel = new JPanel();
//
//        buttonPanel.setBorder(
//                BorderFactory.createEmptyBorder(10, 10, 15, 10)
//        );
//
//        buttonPanel.add(addButton);
//        buttonPanel.add(viewButton);
//
//        // MAIN LAYOUT
//        setLayout(new BorderLayout());
//
//        add(
//                headingLabel,
//                BorderLayout.NORTH
//        );
//
//        add(
//                formPanel,
//                BorderLayout.CENTER
//        );
//
//        add(
//                buttonPanel,
//                BorderLayout.SOUTH
//        );
//
//        // BUTTON ACTIONS
//        addButton.addActionListener(
//                e -> addCase()
//        );
//
//        viewButton.addActionListener(
//                e -> viewCases()
//        );
//    }
//
//    // LOAD DETECTIVES FROM DATABASE
//    private void loadDetectives() {
//
//        DetectiveDAO detectiveDAO =
//                new DetectiveDAO();
//
//        detectives =
//                detectiveDAO.getAllDetectives();
//
//        if (detectives.isEmpty()) {
//
//            detectiveBox.addItem(
//                    "No detectives available"
//            );
//
//            return;
//        }
//
//        for (Detective detective : detectives) {
//
//            detectiveBox.addItem(
//                    detective.getPersonId()
//                            + " - "
//                            + detective.getName()
//            );
//        }
//    }
//
//    // ADD CASE
//    private void addCase() {
//
//        String caseNumber =
//                caseNumberField.getText().trim();
//
//        String title =
//                titleField.getText().trim();
//
//        String description =
//                descriptionArea.getText().trim();
//
//        String status =
//                (String) statusBox.getSelectedItem();
//
//        // VALIDATE BASIC FIELDS
//        if (caseNumber.isEmpty() ||
//                title.isEmpty()) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Case Number and Title are required!",
//                    "Validation",
//                    JOptionPane.WARNING_MESSAGE
//            );
//
//            return;
//        }
//
//        // CHECK DETECTIVE
//        if (detectives.isEmpty()) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "No detectives are available. Please add a detective first.",
//                    "Validation",
//                    JOptionPane.WARNING_MESSAGE
//            );
//
//            return;
//        }
//
//        int selectedIndex =
//                detectiveBox.getSelectedIndex();
//
//        if (selectedIndex < 0 ||
//                selectedIndex >= detectives.size()) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Please select a detective.",
//                    "Validation",
//                    JOptionPane.WARNING_MESSAGE
//            );
//
//            return;
//        }
//
//        Detective selectedDetective =
//                detectives.get(selectedIndex);
//
//        int detectiveId =
//                selectedDetective.getPersonId();
//
//        // CREATE CASE OBJECT
//        Case newCase =
//                new Case();
//
//        newCase.setCaseNumber(
//                caseNumber
//        );
//
//        newCase.setTitle(
//                title
//        );
//
//        newCase.setDescription(
//                description
//        );
//
//        newCase.setStatus(
//                status
//        );
//
//        newCase.setDateCreated(
//                LocalDate.now()
//        );
//
//        newCase.setDetectiveId(
//                detectiveId
//        );
//
//        // SAVE TO DATABASE
//        CaseDAO caseDAO =
//                new CaseDAO();
//
//        boolean success =
//                caseDAO.addCase(newCase);
//
//        if (success) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Case added successfully!",
//                    "Success",
//                    JOptionPane.INFORMATION_MESSAGE
//            );
//
//            // CLEAR FORM
//            caseNumberField.setText("");
//            titleField.setText("");
//            descriptionArea.setText("");
//            statusBox.setSelectedIndex(0);
//
//        } else {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Failed to add case!",
//                    "Error",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//
//    // VIEW ALL CASES
//    private void viewCases() {
//
//        CaseDAO caseDAO =
//                new CaseDAO();
//
//        List<Case> cases =
//                caseDAO.getAllCases();
//
//        if (cases.isEmpty()) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "No cases found."
//            );
//
//            return;
//        }
//
//        StringBuilder caseDetails =
//                new StringBuilder();
//
//        for (Case caseData : cases) {
//
//            caseDetails.append(
//                            "Case ID: "
//                    )
//                    .append(
//                            caseData.getCaseId()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Case Number: "
//                    )
//                    .append(
//                            caseData.getCaseNumber()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Title: "
//                    )
//                    .append(
//                            caseData.getTitle()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Description: "
//                    )
//                    .append(
//                            caseData.getDescription()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Status: "
//                    )
//                    .append(
//                            caseData.getStatus()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Date Created: "
//                    )
//                    .append(
//                            caseData.getDateCreated()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                            "Detective ID: "
//                    )
//                    .append(
//                            caseData.getDetectiveId()
//                    )
//                    .append("\n");
//
//            caseDetails.append(
//                    "--------------------------------\n"
//            );
//        }
//
//        JTextArea textArea =
//                new JTextArea(
//                        caseDetails.toString()
//                );
//
//        textArea.setEditable(false);
//
//        textArea.setFont(
//                new Font("Arial", Font.PLAIN, 14)
//        );
//
//        JScrollPane scrollPane =
//                new JScrollPane(textArea);
//
//        scrollPane.setPreferredSize(
//                new Dimension(550, 400)
//        );
//
//        JOptionPane.showMessageDialog(
//                this,
//                scrollPane,
//                "All Cases",
//                JOptionPane.INFORMATION_MESSAGE
//        );
//    }
//}


package gui;

import dao.CaseDAO;
import dao.DetectiveDAO;
import model.Case;
import model.Detective;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CaseFrame extends JFrame {

    private JComboBox<String> caseBox;
    private JTextField caseNumberField;
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JComboBox<String> statusBox;
    private JComboBox<String> detectiveBox;

    private List<Case> cases;
    private List<Detective> detectives;

    private JButton addButton;
    private JButton updateButton;
    private JButton viewButton;

    public CaseFrame() {

        setTitle("Case Management");
        setSize(700, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // HEADING
        JLabel headingLabel = new JLabel(
                "Case Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        headingLabel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 10, 10)
        );

        // FORM PANEL
        JPanel formPanel = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        // CASE SELECTION
        caseBox = new JComboBox<>();

        formPanel.add(
                new JLabel("Select Case:")
        );

        formPanel.add(caseBox);

        // CASE NUMBER
        caseNumberField = new JTextField();

        formPanel.add(
                new JLabel("Case Number:")
        );

        formPanel.add(caseNumberField);

        // TITLE
        titleField = new JTextField();

        formPanel.add(
                new JLabel("Title:")
        );

        formPanel.add(titleField);

        // DESCRIPTION
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        formPanel.add(
                new JLabel("Description:")
        );

        formPanel.add(
                new JScrollPane(descriptionArea)
        );

        // STATUS
        statusBox = new JComboBox<>(
                new String[]{
                        "Open",
                        "Closed",
                        "Pending"
                }
        );

        formPanel.add(
                new JLabel("Status:")
        );

        formPanel.add(statusBox);

        // DETECTIVE
        detectiveBox = new JComboBox<>();

        formPanel.add(
                new JLabel("Assigned Detective:")
        );

        formPanel.add(detectiveBox);

        // LOAD DATA
        loadDetectives();
        loadCases();

        // BUTTONS
        addButton = new JButton("Add Case");
        updateButton = new JButton("Update Case");
        viewButton = new JButton("View Cases");

        addButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        updateButton.setFont(
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
        buttonPanel.add(updateButton);
        buttonPanel.add(viewButton);

        // MAIN LAYOUT
        setLayout(new BorderLayout());

        add(
                headingLabel,
                BorderLayout.NORTH
        );

        add(
                formPanel,
                BorderLayout.CENTER
        );

        add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        // BUTTON ACTIONS
        addButton.addActionListener(
                e -> addCase()
        );

        updateButton.addActionListener(
                e -> updateCase()
        );

        viewButton.addActionListener(
                e -> viewCases()
        );

        // LOAD SELECTED CASE DETAILS
        caseBox.addActionListener(
                e -> loadSelectedCase()
        );
    }

    // LOAD DETECTIVES FROM DATABASE
    private void loadDetectives() {

        DetectiveDAO detectiveDAO =
                new DetectiveDAO();

        detectives =
                detectiveDAO.getAllDetectives();

        if (detectives.isEmpty()) {

            detectiveBox.addItem(
                    "No detectives available"
            );

            return;
        }

        for (Detective detective : detectives) {

            detectiveBox.addItem(
                    detective.getPersonId()
                            + " - "
                            + detective.getName()
            );
        }
    }

    // LOAD CASES FROM DATABASE
    private void loadCases() {

        CaseDAO caseDAO =
                new CaseDAO();

        cases =
                caseDAO.getAllCases();

        caseBox.removeAllItems();

        if (cases.isEmpty()) {

            caseBox.addItem(
                    "No cases available"
            );

            clearForm();

            return;
        }

        for (Case caseData : cases) {

            caseBox.addItem(
                    caseData.getCaseNumber()
                            + " - "
                            + caseData.getTitle()
            );
        }

        if (!cases.isEmpty()) {

            caseBox.setSelectedIndex(0);

            loadSelectedCase();
        }
    }

    // LOAD SELECTED CASE INTO FORM
    private void loadSelectedCase() {

        if (cases == null ||
                cases.isEmpty()) {

            return;
        }

        int selectedIndex =
                caseBox.getSelectedIndex();

        if (selectedIndex < 0 ||
                selectedIndex >= cases.size()) {

            return;
        }

        Case selectedCase =
                cases.get(selectedIndex);

        caseNumberField.setText(
                selectedCase.getCaseNumber()
        );

        titleField.setText(
                selectedCase.getTitle()
        );

        descriptionArea.setText(
                selectedCase.getDescription() == null
                        ? ""
                        : selectedCase.getDescription()
        );

        statusBox.setSelectedItem(
                selectedCase.getStatus()
        );

        // Select the assigned detective
        for (int i = 0; i < detectives.size(); i++) {

            if (detectives.get(i).getPersonId()
                    == selectedCase.getDetectiveId()) {

                detectiveBox.setSelectedIndex(i);
                break;
            }
        }
    }

    // ADD CASE
    private void addCase() {

        String caseNumber =
                caseNumberField.getText().trim();

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        // VALIDATE BASIC FIELDS
        if (caseNumber.isEmpty() ||
                title.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case Number and Title are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CHECK DETECTIVE
        if (detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives are available. Please add a detective first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int selectedIndex =
                detectiveBox.getSelectedIndex();

        if (selectedIndex < 0 ||
                selectedIndex >= detectives.size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a detective.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Detective selectedDetective =
                detectives.get(selectedIndex);

        int detectiveId =
                selectedDetective.getPersonId();

        // CREATE CASE OBJECT
        Case newCase =
                new Case();

        newCase.setCaseNumber(
                caseNumber
        );

        newCase.setTitle(
                title
        );

        newCase.setDescription(
                description
        );

        newCase.setStatus(
                status
        );

        newCase.setDateCreated(
                LocalDate.now()
        );

        newCase.setDetectiveId(
                detectiveId
        );

        // SAVE TO DATABASE
        CaseDAO caseDAO =
                new CaseDAO();

        boolean success =
                caseDAO.addCase(newCase);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

            loadCases();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add case!\n" +
                            "Make sure the case number is unique.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // UPDATE CASE
    private void updateCase() {

        // CHECK IF CASES EXIST
        if (cases == null ||
                cases.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cases are available to update.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int selectedCaseIndex =
                caseBox.getSelectedIndex();

        if (selectedCaseIndex < 0 ||
                selectedCaseIndex >= cases.size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a case.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String caseNumber =
                caseNumberField.getText().trim();

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        // VALIDATE BASIC FIELDS
        if (caseNumber.isEmpty() ||
                title.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case Number and Title are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CHECK DETECTIVE
        if (detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives are available.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int detectiveIndex =
                detectiveBox.getSelectedIndex();

        if (detectiveIndex < 0 ||
                detectiveIndex >= detectives.size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a detective.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // GET ORIGINAL CASE
        Case selectedCase =
                cases.get(selectedCaseIndex);

        Detective selectedDetective =
                detectives.get(detectiveIndex);

        // UPDATE OBJECT
        selectedCase.setCaseNumber(
                caseNumber
        );

        selectedCase.setTitle(
                title
        );

        selectedCase.setDescription(
                description
        );

        selectedCase.setStatus(
                status
        );

        selectedCase.setDetectiveId(
                selectedDetective.getPersonId()
        );

        // UPDATE DATABASE
        CaseDAO caseDAO =
                new CaseDAO();

        boolean success =
                caseDAO.updateCase(selectedCase);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // REFRESH CASE LIST
            loadCases();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update case!\n" +
                            "Make sure the case number is unique.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // CLEAR FORM
    private void clearForm() {

        caseNumberField.setText("");
        titleField.setText("");
        descriptionArea.setText("");
        statusBox.setSelectedIndex(0);
    }

    // VIEW ALL CASES
    private void viewCases() {

        CaseDAO caseDAO =
                new CaseDAO();

        List<Case> cases =
                caseDAO.getAllCases();

        if (cases.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cases found."
            );

            return;
        }

        StringBuilder caseDetails =
                new StringBuilder();

        caseDetails.append(
                "CASE RECORDS\n"
        );

        caseDetails.append(
                "================================\n\n"
        );

        for (Case caseData : cases) {

            caseDetails.append(
                            "Case ID: "
                    )
                    .append(
                            caseData.getCaseId()
                    )
                    .append("\n");

            caseDetails.append(
                            "Case Number: "
                    )
                    .append(
                            caseData.getCaseNumber()
                    )
                    .append("\n");

            caseDetails.append(
                            "Title: "
                    )
                    .append(
                            caseData.getTitle()
                    )
                    .append("\n");

            caseDetails.append(
                            "Description: "
                    )
                    .append(
                            caseData.getDescription()
                    )
                    .append("\n");

            caseDetails.append(
                            "Status: "
                    )
                    .append(
                            caseData.getStatus()
                    )
                    .append("\n");

            caseDetails.append(
                            "Date Created: "
                    )
                    .append(
                            caseData.getDateCreated()
                    )
                    .append("\n");

            caseDetails.append(
                            "Detective ID: "
                    )
                    .append(
                            caseData.getDetectiveId()
                    )
                    .append("\n");

            caseDetails.append(
                    "--------------------------------\n"
            );
        }

        JTextArea textArea =
                new JTextArea(
                        caseDetails.toString()
                );

        textArea.setEditable(false);

        textArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(550, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Cases",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}