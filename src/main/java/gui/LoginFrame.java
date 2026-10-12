
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
        setMinimumSize(new Dimension(500, 380));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(245, 245, 245));

        // TITLE
        JLabel titleLabel = new JLabel(
                "<html><center>"
                        + "CRIME INVESTIGATION<br>"
                        + "MANAGEMENT SYSTEM"
                        + "</center></html>",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel subtitleLabel = new JLabel(
                "Secure Login",
                SwingConstants.CENTER
        );

        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel titlePanel = new JPanel(
                new GridLayout(2, 1, 0, 8)
        );

        titlePanel.setBackground(mainPanel.getBackground());
        titlePanel.setBorder(
                BorderFactory.createEmptyBorder(25, 15, 5, 15)
        );

        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        mainPanel.add(titlePanel, BorderLayout.NORTH);

        // LOGIN FORM
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));

        usernameField = new JTextField(18);
        passwordField = new JPasswordField(18);

        usernameField.setPreferredSize(new Dimension(220, 35));
        passwordField.setPreferredSize(new Dimension(220, 35));

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(140, 42));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(mainPanel.getBackground());

        // Keep the form compact even when the window is maximized.
        formPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 20, 5, 20)
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(8, 10, 5, 10);
        formPanel.add(loginButton, gbc);

        // Put the compact form in the center of the available area.
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(mainPanel.getBackground());

        GridBagConstraints formConstraints = new GridBagConstraints();

        formConstraints.gridx = 0;
        formConstraints.gridy = 0;
        formConstraints.weighty = 1.0;
        formConstraints.anchor = GridBagConstraints.NORTH;
        formConstraints.insets = new Insets(25, 0, 0, 0);

        centerPanel.add(formPanel, formConstraints);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // FOOTER
        JLabel footerLabel = new JLabel(
                "Authorized users only",
                SwingConstants.CENTER
        );

        footerLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        footerLabel.setBorder(
                BorderFactory.createEmptyBorder(5, 10, 15, 10)
        );

        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // LOGIN ACTIONS
        loginButton.addActionListener(e -> login());

        passwordField.addActionListener(e -> login());

        // Open as a normal centered window, not maximized.
        setLocationRelativeTo(null);
    }

    private void login() {

        String username = usernameField.getText().trim();

        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password.",
                    "Login",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        UserDAO userDAO = new UserDAO();

        User user = userDAO.login(username, password);

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
