package gui;

import dao.SuspectDAO;
import model.Suspect;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SuspectFrame extends JFrame {

    private JTextField nameField;
    private JTextField ageField;
    private JTextField genderField;
    private JTextField addressField;
    private JTextField phoneField;

    private JButton addButton;
    private JButton viewButton;

    public SuspectFrame() {

        setTitle("Suspect Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Suspect Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(5, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 20, 40)
        );

        nameField = new JTextField();
        ageField = new JTextField();
        genderField = new JTextField();
        addressField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);

        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        addButton = new JButton("Add Suspect");
        viewButton = new JButton("View Suspects");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addSuspect());

        viewButton.addActionListener(e -> viewSuspects());
    }

    private void addSuspect() {

        String name = nameField.getText();
        String ageText = ageField.getText();
        String gender = genderField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name is required!"
            );

            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number!"
            );

            return;
        }

        Suspect suspect = new Suspect();

        suspect.setName(name);
        suspect.setAge(age);
        suspect.setGender(gender);
        suspect.setAddress(address);
        suspect.setPhone(phone);

        SuspectDAO suspectDAO = new SuspectDAO();

        boolean success = suspectDAO.addSuspect(suspect);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Suspect added successfully!"
            );

            nameField.setText("");
            ageField.setText("");
            genderField.setText("");
            addressField.setText("");
            phoneField.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add suspect!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void viewSuspects() {

        SuspectDAO suspectDAO = new SuspectDAO();

        List<Suspect> suspects =
                suspectDAO.getAllSuspects();

        if (suspects.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No suspects found."
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        for (Suspect suspect : suspects) {

            details.append("Suspect ID: ")
                    .append(suspect.getPersonId())
                    .append("\n");

            details.append("Name: ")
                    .append(suspect.getName())
                    .append("\n");

            details.append("Age: ")
                    .append(suspect.getAge())
                    .append("\n");

            details.append("Gender: ")
                    .append(suspect.getGender())
                    .append("\n");

            details.append("Address: ")
                    .append(suspect.getAddress())
                    .append("\n");

            details.append("Phone: ")
                    .append(suspect.getPhone())
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
                new Dimension(500, 400)
        );

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "All Suspects",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}