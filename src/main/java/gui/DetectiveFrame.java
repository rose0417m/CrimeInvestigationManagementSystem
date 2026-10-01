package gui;

import dao.DetectiveDAO;
import model.Detective;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DetectiveFrame extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField specializationField;

    private JButton addButton;
    private JButton viewButton;

    public DetectiveFrame() {

        setTitle("Detective Management");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // HEADING
        JLabel headingLabel = new JLabel(
                "Detective Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        headingLabel.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 10, 10, 10
                )
        );

        // FORM PANEL
        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 50, 20, 50
                )
        );

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        specializationField = new JTextField();

        formPanel.add(
                new JLabel("Name:")
        );
        formPanel.add(nameField);

        formPanel.add(
                new JLabel("Phone:")
        );
        formPanel.add(phoneField);

        formPanel.add(
                new JLabel("Email:")
        );
        formPanel.add(emailField);

        formPanel.add(
                new JLabel("Specialization:")
        );
        formPanel.add(specializationField);

        // BUTTONS
        addButton = new JButton(
                "Add Detective"
        );

        viewButton = new JButton(
                "View Detectives"
        );

        addButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        viewButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 10, 15, 10
                )
        );

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        // MAIN LAYOUT
        setLayout(
                new BorderLayout()
        );

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
                e -> addDetective()
        );

        viewButton.addActionListener(
                e -> viewDetectives()
        );

        // PRESS ENTER TO ADD
        specializationField.addActionListener(
                e -> addDetective()
        );
    }

    // ADD DETECTIVE
    private void addDetective() {

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String email =
                emailField.getText().trim();

        String specialization =
                specializationField.getText().trim();

        // NAME VALIDATION
        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name is required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // PHONE VALIDATION
        if (!phone.isEmpty() &&
                !phone.matches("\\d{10}")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Phone number must contain exactly 10 digits.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // EMAIL VALIDATION
        if (!email.isEmpty() &&
                !email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CREATE DETECTIVE OBJECT
        Detective detective =
                new Detective();

        detective.setName(name);
        detective.setPhone(phone);
        detective.setEmail(email);
        detective.setSpecialization(
                specialization
        );

        // SAVE TO DATABASE
        DetectiveDAO detectiveDAO =
                new DetectiveDAO();

        boolean success =
                detectiveDAO.addDetective(
                        detective
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Detective added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // CLEAR FORM
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            specializationField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add detective!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // VIEW ALL DETECTIVES
    private void viewDetectives() {

        DetectiveDAO detectiveDAO =
                new DetectiveDAO();

        List<Detective> detectives =
                detectiveDAO.getAllDetectives();

        if (detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives found."
            );

            return;
        }

        StringBuilder details =
                new StringBuilder();

        details.append(
                "DETECTIVE RECORDS\n"
        );

        details.append(
                "================================\n\n"
        );

        for (Detective detective : detectives) {

            details.append(
                            "Detective ID: "
                    )
                    .append(
                            detective.getPersonId()
                    )
                    .append("\n");

            details.append(
                            "Name: "
                    )
                    .append(
                            detective.getName()
                    )
                    .append("\n");

            details.append(
                            "Phone: "
                    )
                    .append(
                            detective.getPhone()
                    )
                    .append("\n");

            details.append(
                            "Email: "
                    )
                    .append(
                            detective.getEmail()
                    )
                    .append("\n");

            details.append(
                            "Specialization: "
                    )
                    .append(
                            detective.getSpecialization()
                    )
                    .append("\n");

            details.append(
                    "--------------------------------\n"
            );
        }

        JTextArea textArea =
                new JTextArea(
                        details.toString()
                );

        textArea.setEditable(false);

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
                "All Detectives",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}