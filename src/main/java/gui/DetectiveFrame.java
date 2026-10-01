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
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Detective Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 20, 40)
        );

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        specializationField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Specialization:"));
        formPanel.add(specializationField);

        addButton = new JButton("Add Detective");
        viewButton = new JButton("View Detectives");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addDetective());

        viewButton.addActionListener(e -> viewDetectives());
    }

    private void addDetective() {

        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String specialization = specializationField.getText();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name is required!"
            );

            return;
        }

        Detective detective = new Detective();

        detective.setName(name);
        detective.setPhone(phone);
        detective.setEmail(email);
        detective.setSpecialization(specialization);

        DetectiveDAO detectiveDAO = new DetectiveDAO();

        boolean success = detectiveDAO.addDetective(detective);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Detective added successfully!"
            );

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

    private void viewDetectives() {

        DetectiveDAO detectiveDAO = new DetectiveDAO();

        List<Detective> detectives =
                detectiveDAO.getAllDetectives();

        if (detectives.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No detectives found."
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        for (Detective detective : detectives) {

            details.append("Detective ID: ")
                    .append(detective.getPersonId())
                    .append("\n");

            details.append("Name: ")
                    .append(detective.getName())
                    .append("\n");

            details.append("Phone: ")
                    .append(detective.getPhone())
                    .append("\n");

            details.append("Email: ")
                    .append(detective.getEmail())
                    .append("\n");

            details.append("Specialization: ")
                    .append(detective.getSpecialization())
                    .append("\n");

            details.append("--------------------------------\n");
        }

        JTextArea textArea = new JTextArea(
                details.toString()
        );

        textArea.setEditable(false);

        JScrollPane scrollPane =
                new JScrollPane(textArea);

        scrollPane.setPreferredSize(
                new Dimension(500, 350)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Detectives",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}