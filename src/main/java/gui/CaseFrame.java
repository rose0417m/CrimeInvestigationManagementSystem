package gui;

import dao.CaseDAO;
import dao.CaseLinkDAO;
import dao.DetectiveDAO;
import dao.SuspectDAO;
import dao.WitnessDAO;

import model.Case;
import model.Detective;
import model.Suspect;
import model.Witness;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    private JButton linkSuspectButton;
    private JButton linkWitnessButton;
    private JButton viewLinkedSuspectsButton;
    private JButton viewLinkedWitnessesButton;

    public CaseFrame() {

        setTitle("Case Management");
        setSize(780, 700);
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

        formPanel.add(new JLabel("Select Case:"));
        formPanel.add(caseBox);

        // CASE NUMBER
        caseNumberField = new JTextField();

        formPanel.add(new JLabel("Case Number:"));
        formPanel.add(caseNumberField);

        // TITLE
        titleField = new JTextField();

        formPanel.add(new JLabel("Title:"));
        formPanel.add(titleField);

        // DESCRIPTION
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        // STATUS
        statusBox = new JComboBox<>(
                new String[]{
                        "Open",
                        "Closed",
                        "Pending"
                }
        );

        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusBox);

        // ASSIGNED DETECTIVE
        detectiveBox = new JComboBox<>();

        formPanel.add(new JLabel("Assigned Detective:"));
        formPanel.add(detectiveBox);

        // LOAD DATA
        loadDetectives();
        loadCases();

        // CREATE BUTTONS
        addButton = new JButton("Add Case");
        updateButton = new JButton("Update Case");
        viewButton = new JButton("View Cases");

        linkSuspectButton = new JButton("Link Suspect");
        linkWitnessButton = new JButton("Link Witness");

        viewLinkedSuspectsButton =
                new JButton("View Linked Suspects");

        viewLinkedWitnessesButton =
                new JButton("View Linked Witnesses");

        JButton[] buttons = {
                addButton,
                updateButton,
                viewButton,
                linkSuspectButton,
                linkWitnessButton,
                viewLinkedSuspectsButton,
                viewLinkedWitnessesButton
        };

        for (JButton button : buttons) {
            button.setFont(
                    new Font("Arial", Font.BOLD, 13)
            );
            button.setFocusPainted(false);
        }

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(
                new GridLayout(0, 3, 10, 10)
        );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 25, 20, 25)
        );

        for (JButton button : buttons) {
            buttonPanel.add(button);
        }

        // MAIN LAYOUT
        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // EXISTING BUTTON ACTIONS
        addButton.addActionListener(e -> addCase());
        updateButton.addActionListener(e -> updateCase());
        viewButton.addActionListener(e -> viewCases());

        // NEW LINKING BUTTON ACTIONS
        linkSuspectButton.addActionListener(
                e -> linkSuspectToCase()
        );

        linkWitnessButton.addActionListener(
                e -> linkWitnessToCase()
        );

        viewLinkedSuspectsButton.addActionListener(
                e -> viewLinkedSuspects()
        );

        viewLinkedWitnessesButton.addActionListener(
                e -> viewLinkedWitnesses()
        );

        // LOAD DETAILS WHEN A CASE IS SELECTED
        caseBox.addActionListener(
                e -> loadSelectedCase()
        );
    }

    // LOAD DETECTIVES FROM DATABASE
    private void loadDetectives() {

        DetectiveDAO detectiveDAO = new DetectiveDAO();

        detectives = detectiveDAO.getAllDetectives();

        if (detectives == null) {
            detectives = new ArrayList<>();
        }

        if (detectives.isEmpty()) {
            detectiveBox.addItem("No detectives available");
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

        CaseDAO caseDAO = new CaseDAO();

        cases = caseDAO.getAllCases();

        if (cases == null) {
            cases = new ArrayList<>();
        }

        caseBox.removeAllItems();

        // Placeholder at index 0
        caseBox.addItem("-- Select a case --");

        for (Case caseData : cases) {

            caseBox.addItem(
                    caseData.getCaseNumber()
                            + " - "
                            + caseData.getTitle()
            );
        }

        // Keep the form empty when the window opens
        caseBox.setSelectedIndex(0);
        clearForm();
    }

    // LOAD SELECTED CASE DETAILS
    private void loadSelectedCase() {

        int selectedIndex = caseBox.getSelectedIndex();

        if (selectedIndex <= 0) {
            clearForm();
            return;
        }

        if (cases == null ||
                selectedIndex > cases.size()) {
            return;
        }

        // Index 0 is the placeholder
        Case selectedCase = cases.get(selectedIndex - 1);

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

        // Select the detective assigned to the case
        if (detectiveBox.getItemCount() > 0) {
            detectiveBox.setSelectedIndex(0);
        }

        for (int i = 0; i < detectives.size(); i++) {

            if (detectives.get(i).getPersonId()
                    == selectedCase.getDetectiveId()) {

                detectiveBox.setSelectedIndex(i);
                break;
            }
        }
    }

    // GET THE CASE SELECTED IN THE DROPDOWN
    private Case getSelectedCase() {

        int selectedIndex = caseBox.getSelectedIndex();

        if (cases == null ||
                selectedIndex <= 0 ||
                selectedIndex > cases.size()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a case first.",
                    "Select Case",
                    JOptionPane.WARNING_MESSAGE
            );

            return null;
        }

        return cases.get(selectedIndex - 1);
    }

    // ADD A NEW CASE
    private void addCase() {

        String caseNumber =
                caseNumberField.getText().trim();

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String status =
                (String) statusBox.getSelectedItem();

        if (caseNumber.isEmpty() || title.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case Number and Title are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (detectives == null || detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives are available. Please add a detective first.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int detectiveIndex = detectiveBox.getSelectedIndex();

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

        Detective selectedDetective =
                detectives.get(detectiveIndex);

        Case newCase = new Case();

        newCase.setCaseNumber(caseNumber);
        newCase.setTitle(title);
        newCase.setDescription(description);
        newCase.setStatus(status);
        newCase.setDateCreated(LocalDate.now());

        newCase.setDetectiveId(
                selectedDetective.getPersonId()
        );

        CaseDAO caseDAO = new CaseDAO();

        boolean success = caseDAO.addCase(newCase);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCases();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add case!\n"
                            + "Make sure the case number is unique.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // UPDATE AN EXISTING CASE
    private void updateCase() {

        Case selectedCase = getSelectedCase();

        if (selectedCase == null) {
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

        if (caseNumber.isEmpty() || title.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case Number and Title are required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (detectives == null || detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives are available.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int detectiveIndex = detectiveBox.getSelectedIndex();

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

        Detective selectedDetective =
                detectives.get(detectiveIndex);

        selectedCase.setCaseNumber(caseNumber);
        selectedCase.setTitle(title);
        selectedCase.setDescription(description);
        selectedCase.setStatus(status);

        selectedCase.setDetectiveId(
                selectedDetective.getPersonId()
        );

        CaseDAO caseDAO = new CaseDAO();

        boolean success = caseDAO.updateCase(selectedCase);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Case updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            loadCases();

        } else {

            loadCases();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to update case!\n"
                            + "Make sure the case number is unique.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LINK AN EXISTING SUSPECT TO THE SELECTED CASE
    private void linkSuspectToCase() {

        Case selectedCase = getSelectedCase();

        if (selectedCase == null) {
            return;
        }

        SuspectDAO suspectDAO = new SuspectDAO();

        List<Suspect> suspects = suspectDAO.getAllSuspects();

        if (suspects == null || suspects.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No suspects found. Please add a suspect first.",
                    "No Suspects",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        JComboBox<String> suspectBox = new JComboBox<>();

        for (Suspect suspect : suspects) {

            suspectBox.addItem(
                    suspect.getPersonId()
                            + " - "
                            + suspect.getName()
            );
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                suspectBox,
                "Select a Suspect for "
                        + selectedCase.getCaseNumber(),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        int suspectIndex = suspectBox.getSelectedIndex();

        if (suspectIndex < 0 ||
                suspectIndex >= suspects.size()) {
            return;
        }

        Suspect selectedSuspect = suspects.get(suspectIndex);

        CaseLinkDAO caseLinkDAO = new CaseLinkDAO();

        boolean success = caseLinkDAO.linkSuspectToCase(
                selectedCase.getCaseId(),
                selectedSuspect.getPersonId()
        );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    selectedSuspect.getName()
                            + " has been linked to "
                            + selectedCase.getCaseNumber()
                            + ".\nIf already linked, no duplicate was created.",
                    "Suspect Linked",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not link the suspect to this case.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // LINK AN EXISTING WITNESS TO THE SELECTED CASE
    private void linkWitnessToCase() {

        Case selectedCase = getSelectedCase();

        if (selectedCase == null) {
            return;
        }

        WitnessDAO witnessDAO = new WitnessDAO();

        List<Witness> witnesses = witnessDAO.getAllWitnesses();

        if (witnesses == null || witnesses.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No witnesses found. Please add a witness first.",
                    "No Witnesses",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        JComboBox<String> witnessBox = new JComboBox<>();

        for (Witness witness : witnesses) {

            witnessBox.addItem(
                    witness.getPersonId()
                            + " - "
                            + witness.getName()
            );
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                witnessBox,
                "Select a Witness for "
                        + selectedCase.getCaseNumber(),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice != JOptionPane.OK_OPTION) {
            return;
        }

        int witnessIndex = witnessBox.getSelectedIndex();

        if (witnessIndex < 0 ||
                witnessIndex >= witnesses.size()) {
            return;
        }

        Witness selectedWitness = witnesses.get(witnessIndex);

        CaseLinkDAO caseLinkDAO = new CaseLinkDAO();

        boolean success = caseLinkDAO.linkWitnessToCase(
                selectedCase.getCaseId(),
                selectedWitness.getPersonId()
        );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    selectedWitness.getName()
                            + " has been linked to "
                            + selectedCase.getCaseNumber()
                            + ".\nIf already linked, no duplicate was created.",
                    "Witness Linked",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Could not link the witness to this case.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // VIEW SUSPECTS LINKED TO THE SELECTED CASE
    private void viewLinkedSuspects() {

        Case selectedCase = getSelectedCase();

        if (selectedCase == null) {
            return;
        }

        CaseLinkDAO caseLinkDAO = new CaseLinkDAO();

        List<String> linkedSuspects =
                caseLinkDAO.getSuspectsForCase(
                        selectedCase.getCaseId()
                );

        showLinkedRecords(
                linkedSuspects,
                "Linked Suspects - "
                        + selectedCase.getCaseNumber(),
                "No suspects are linked to this case yet."
        );
    }

    // VIEW WITNESSES LINKED TO THE SELECTED CASE
    private void viewLinkedWitnesses() {

        Case selectedCase = getSelectedCase();

        if (selectedCase == null) {
            return;
        }

        CaseLinkDAO caseLinkDAO = new CaseLinkDAO();

        List<String> linkedWitnesses =
                caseLinkDAO.getWitnessesForCase(
                        selectedCase.getCaseId()
                );

        showLinkedRecords(
                linkedWitnesses,
                "Linked Witnesses - "
                        + selectedCase.getCaseNumber(),
                "No witnesses are linked to this case yet."
        );
    }

    // DISPLAY LINKED RECORDS
    private void showLinkedRecords(
            List<String> records,
            String windowTitle,
            String emptyMessage) {

        if (records == null || records.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    emptyMessage,
                    windowTitle,
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        for (String record : records) {

            details.append(record)
                    .append("\n")
                    .append("--------------------------------\n\n");
        }

        JTextArea textArea = new JTextArea(
                details.toString()
        );

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JScrollPane scrollPane = new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 350)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                windowTitle,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // CLEAR THE FORM
    private void clearForm() {

        caseNumberField.setText("");
        titleField.setText("");
        descriptionArea.setText("");

        statusBox.setSelectedIndex(0);

        if (detectiveBox.getItemCount() > 0) {
            detectiveBox.setSelectedIndex(0);
        }
    }

    // VIEW ALL CASES
    private void viewCases() {

        CaseDAO caseDAO = new CaseDAO();

        List<Case> allCases = caseDAO.getAllCases();

        if (allCases == null || allCases.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No cases found."
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        details.append("CASE RECORDS\n");
        details.append("================================\n\n");

        for (Case caseData : allCases) {

            details.append("Case ID: ")
                    .append(caseData.getCaseId())
                    .append("\n");

            details.append("Case Number: ")
                    .append(caseData.getCaseNumber())
                    .append("\n");

            details.append("Title: ")
                    .append(caseData.getTitle())
                    .append("\n");

            details.append("Description: ")
                    .append(caseData.getDescription())
                    .append("\n");

            details.append("Status: ")
                    .append(caseData.getStatus())
                    .append("\n");

            details.append("Date Created: ")
                    .append(caseData.getDateCreated())
                    .append("\n");

            details.append("Detective ID: ")
                    .append(caseData.getDetectiveId())
                    .append("\n");

            details.append("--------------------------------\n");
        }

        JTextArea textArea = new JTextArea(
                details.toString()
        );

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        textArea.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        JScrollPane scrollPane = new JScrollPane(textArea);

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