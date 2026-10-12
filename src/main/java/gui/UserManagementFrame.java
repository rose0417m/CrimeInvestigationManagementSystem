
package gui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private DefaultTableModel tableModel;
    private JTable userTable;

    private final UserDAO userDAO = new UserDAO();

    public UserManagementFrame(String currentRole) {

        // Check authorization before showing the screen
        if (currentRole == null
                || !currentRole.equalsIgnoreCase("Admin")) {

            JOptionPane.showMessageDialog(
                    null,
                    "Access denied. Only Admin can manage users."
            );

            dispose();
            return;
        }

        setTitle("User Management");
        setSize(650, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JLabel titleLabel = new JLabel(
                "Manage User Accounts",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Account creation form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));

        formPanel.add(new JLabel("Username:"));

        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));

        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton createButton = new JButton("Create Investigator");
        JButton clearButton = new JButton("Clear");

        formPanel.add(createButton);
        formPanel.add(clearButton);

        // Existing user accounts table
        String[] columns = {"User ID", "Username", "Role"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        userTable = new JTable(tableModel);

        JScrollPane tableScrollPane = new JScrollPane(userTable);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.NORTH);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        createButton.addActionListener(e -> createAccount());

        clearButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        add(mainPanel);

        refreshUsers();
        setVisible(true);
    }

    private void createAccount() {

        String username = usernameField.getText().trim();
        String password =
                new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter both username and password."
            );

            return;
        }

        if (username.length() > 50) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username must not exceed 50 characters."
            );

            return;
        }

        if (password.length() < 8) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 8 characters."
            );

            return;
        }

        boolean created = userDAO.createInvestigatorAccount(
                username,
                password
        );

        if (created) {

            JOptionPane.showMessageDialog(
                    this,
                    "Investigator account created successfully!"
            );

            usernameField.setText("");
            passwordField.setText("");

            refreshUsers();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Account creation failed. "
                            + "The username may already exist, "
                            + "or a database error occurred."
            );
        }
    }

    private void refreshUsers() {

        tableModel.setRowCount(0);

        List<User> users = userDAO.getAllUsers();

        for (User user : users) {

            tableModel.addRow(new Object[]{
                    user.getUserId(),
                    user.getUsername(),
                    user.getRole()
            });
        }
    }
}
