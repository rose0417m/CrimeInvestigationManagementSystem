package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {

        setTitle("Crime Investigation Management System - Login");
        setSize(600, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // =========================
        // TITLE
        // =========================

        JLabel titleLabel = new JLabel(
                "<html><center>" +
                        "CRIME INVESTIGATION<br>" +
                        "MANAGEMENT SYSTEM" +
                        "</center></html>",
                SwingConstants.CENTER
        );

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JLabel subtitleLabel = new JLabel(
                "Secure Login",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(
                new Font("Arial", Font.PLAIN, 15)
        );

        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 20, 15, 20
                )
        );

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        // =========================
        // LOGIN FORM
        // =========================

        JLabel usernameLabel =
                new JLabel("Username:");

        JLabel passwordLabel =
                new JLabel("Password:");

        usernameLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        passwordLabel.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        usernameField =
                new JTextField();

        passwordField =
                new JPasswordField();

        usernameField.setPreferredSize(
                new Dimension(220, 35)
        );

        passwordField.setPreferredSize(
                new Dimension(220, 35)
        );

        loginButton =
                new JButton("Login");

        loginButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        loginButton.setPreferredSize(
                new Dimension(120, 40)
        );

        // Form using GridBagLayout
        JPanel formPanel =
                new JPanel(new GridBagLayout());

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 80, 15, 80
                )
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10, 10, 10, 10);

        gbc.anchor =
                GridBagConstraints.CENTER;

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;

        formPanel.add(
                usernameLabel,
                gbc
        );

        // Username field
        gbc.gridx = 1;

        formPanel.add(
                usernameField,
                gbc
        );

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;

        formPanel.add(
                passwordLabel,
                gbc
        );

        // Password field
        gbc.gridx = 1;

        formPanel.add(
                passwordField,
                gbc
        );

        // Login button
        gbc.gridx = 1;
        gbc.gridy = 2;

        formPanel.add(
                loginButton,
                gbc
        );

        // =========================
        // FOOTER
        // =========================

        JLabel footerLabel = new JLabel(
                "Authorized users only",
                SwingConstants.CENTER
        );

        footerLabel.setFont(
                new Font("Arial", Font.ITALIC, 12)
        );

        footerLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        5, 10, 15, 10
                )
        );

        // =========================
        // MAIN LAYOUT
        // =========================

        setLayout(
                new BorderLayout()
        );

        add(
                titlePanel,
                BorderLayout.NORTH
        );

        add(
                formPanel,
                BorderLayout.CENTER
        );

        add(
                footerLabel,
                BorderLayout.SOUTH
        );

        // =========================
        // LOGIN ACTION
        // =========================

        loginButton.addActionListener(
                e -> login()
        );

        passwordField.addActionListener(
                e -> login()
        );
    }

    private void login() {

        String username =
                usernameField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        if (username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        UserDAO userDAO =
                new UserDAO();

        User user =
                userDAO.login(
                        username,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!\nWelcome, "
                            + user.getUsername()
            );

            DashboardFrame dashboardFrame =
                    new DashboardFrame(
                            user.getUsername(),
                            user.getRole()
                    );

            dashboardFrame.setVisible(true);

            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username or password!",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

//package gui;
//
//import dao.UserDAO;
//import model.User;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class LoginFrame extends JFrame {
//
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JButton loginButton;
//
//    public LoginFrame() {
//
//        setTitle("Crime Investigation Management System - Login");
//        setSize(400, 250);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(4, 2, 10, 10));
//
//        JLabel usernameLabel = new JLabel("Username:");
//        JLabel passwordLabel = new JLabel("Password:");
//
//        usernameField = new JTextField();
//        passwordField = new JPasswordField();
//
//        loginButton = new JButton("Login");
//
//        panel.setBorder(
//                BorderFactory.createEmptyBorder(30, 30, 30, 30)
//        );
//
//        panel.add(usernameLabel);
//        panel.add(usernameField);
//
//        panel.add(passwordLabel);
//        panel.add(passwordField);
//
//        panel.add(new JLabel());
//        panel.add(loginButton);
//
//        add(panel);
//
//        loginButton.addActionListener(e -> login());
//    }
//
//    private void login() {
//
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//
//        UserDAO userDAO = new UserDAO();
//
//        User user = userDAO.login(username, password);
//
//        if (user != null) {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Login successful!\nWelcome, " + user.getUsername()
//            );
//
//            DashboardFrame dashboardFrame =
//                    new DashboardFrame(user.getUsername(), user.getRole());
//
//            dashboardFrame.setVisible(true);
//
//            dispose();
//
//        } else {
//
//            JOptionPane.showMessageDialog(
//                    this,
//                    "Invalid username or password!",
//                    "Login Failed",
//                    JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//}

