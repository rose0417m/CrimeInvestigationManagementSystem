package gui;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String username, String role) {

        setTitle("Crime Investigation Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // HEADER
        // =========================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        );

        JLabel titleLabel = new JLabel(
                "CRIME INVESTIGATION MANAGEMENT SYSTEM"
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        JLabel userLabel = new JLabel(
                "Logged in as: " + username + " (" + role + ")"
        );

        userLabel.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        headerPanel.add(
                titleLabel,
                BorderLayout.WEST
        );

        headerPanel.add(
                userLabel,
                BorderLayout.EAST
        );

        // =========================
        // MODULE BUTTONS
        // =========================

        JButton caseButton =
                new JButton("Case Management");

        JButton detectiveButton =
                new JButton("Detective Management");

        JButton suspectButton =
                new JButton("Suspect Management");

        JButton witnessButton =
                new JButton("Witness Management");

        JButton evidenceButton =
                new JButton("Evidence Management");

        JButton reportButton =
                new JButton("Reports");

        JButton searchButton =
                new JButton("Search");

        JButton logoutButton =
                new JButton("Logout");

        JButton[] buttons = {
                caseButton,
                detectiveButton,
                suspectButton,
                witnessButton,
                evidenceButton,
                reportButton,
                searchButton,
                logoutButton
        };

        for (JButton button : buttons) {

            button.setFont(
                    new Font("Arial", Font.BOLD, 14)
            );

            button.setFocusPainted(false);

            button.setPreferredSize(
                    new Dimension(250, 70)
            );
        }

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(4, 2, 20, 20)
                );

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        30, 60, 30, 60
                )
        );

        buttonPanel.add(caseButton);
        buttonPanel.add(detectiveButton);
        buttonPanel.add(suspectButton);
        buttonPanel.add(witnessButton);
        buttonPanel.add(evidenceButton);
        buttonPanel.add(reportButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(logoutButton);

        // =========================
        // FOOTER
        // =========================

        JLabel footerLabel = new JLabel(
                "Crime Investigation Management System",
                SwingConstants.CENTER
        );

        footerLabel.setFont(
                new Font("Arial", Font.PLAIN, 12)
        );

        footerLabel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // =========================
        // BUTTON ACTIONS
        // =========================

        caseButton.addActionListener(e -> {

            CaseFrame caseFrame =
                    new CaseFrame();

            caseFrame.setVisible(true);
        });

        detectiveButton.addActionListener(e -> {

            DetectiveFrame detectiveFrame =
                    new DetectiveFrame();

            detectiveFrame.setVisible(true);
        });

        suspectButton.addActionListener(e -> {

            SuspectFrame suspectFrame =
                    new SuspectFrame();

            suspectFrame.setVisible(true);
        });

        witnessButton.addActionListener(e -> {

            WitnessFrame witnessFrame =
                    new WitnessFrame();

            witnessFrame.setVisible(true);
        });

        evidenceButton.addActionListener(e -> {

            EvidenceFrame evidenceFrame =
                    new EvidenceFrame();

            evidenceFrame.setVisible(true);
        });

        reportButton.addActionListener(e -> {

            ReportFrame reportFrame =
                    new ReportFrame();

            reportFrame.setVisible(true);
        });

        searchButton.addActionListener(e -> {

            SearchFrame searchFrame =
                    new SearchFrame();

            searchFrame.setVisible(true);
        });

        logoutButton.addActionListener(e -> {

            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Are you sure you want to logout?",
                            "Logout",
                            JOptionPane.YES_NO_OPTION
                    );

            if (choice == JOptionPane.YES_OPTION) {

                LoginFrame loginFrame =
                        new LoginFrame();

                loginFrame.setVisible(true);

                dispose();
            }
        });

        // =========================
        // MAIN LAYOUT
        // =========================

        setLayout(new BorderLayout());

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(
                footerLabel,
                BorderLayout.SOUTH
        );
    }
}