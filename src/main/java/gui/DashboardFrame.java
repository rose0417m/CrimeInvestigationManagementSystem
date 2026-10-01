package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String username, String role) {

        setTitle("Crime Investigation Management System - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel(
                "Welcome, " + username + " (" + role + ")",
                SwingConstants.CENTER
        );

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JButton caseButton = new JButton("Case Management");
        JButton detectiveButton = new JButton("Detective Management");
        JButton suspectButton = new JButton("Suspect Management");
        JButton witnessButton = new JButton("Witness Management");
        JButton evidenceButton = new JButton("Evidence Management");
        JButton reportButton = new JButton("Reports");
        JButton searchButton = new JButton("Search");
        JButton logoutButton = new JButton("Logout");

        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15));

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        );

        buttonPanel.add(caseButton);
        buttonPanel.add(detectiveButton);
        buttonPanel.add(suspectButton);
        buttonPanel.add(witnessButton);
        buttonPanel.add(evidenceButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);

        caseButton.addActionListener(e -> {
            CaseFrame caseFrame = new CaseFrame();
            caseFrame.setVisible(true);
        });
        detectiveButton.addActionListener(e -> {
            DetectiveFrame detectiveFrame = new DetectiveFrame();
            detectiveFrame.setVisible(true);
        });
        suspectButton.addActionListener(e -> {
            SuspectFrame suspectFrame = new SuspectFrame();
            suspectFrame.setVisible(true);
        });
        witnessButton.addActionListener(e -> {
            WitnessFrame witnessFrame = new WitnessFrame();
            witnessFrame.setVisible(true);
        });
        evidenceButton.addActionListener(e -> {
            EvidenceFrame evidenceFrame = new EvidenceFrame();
            evidenceFrame.setVisible(true);
        });
        reportButton.addActionListener(e -> {
            ReportFrame reportFrame = new ReportFrame();
            reportFrame.setVisible(true);
        });
        searchButton.addActionListener(e -> {
            SearchFrame searchFrame = new SearchFrame();
            searchFrame.setVisible(true);
        });
        logoutButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);

                dispose();
            }
        });

        setLayout(new BorderLayout());

        add(welcomeLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}