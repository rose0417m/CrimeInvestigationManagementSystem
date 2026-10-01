package gui;

import dao.SuspectDAO;
import model.Suspect;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SuspectFrame extends JFrame {

    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderBox;
    private JTextField addressField;
    private JTextField phoneField;

    private JButton addButton;
    private JButton viewButton;

    public SuspectFrame() {

        setTitle("Suspect Management");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // HEADING
        JLabel headingLabel = new JLabel(
                "Suspect Management",
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
                new GridLayout(5, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 50, 20, 50
                )
        );

        nameField = new JTextField();
        ageField = new JTextField();

        genderBox = new JComboBox<>(
                new String[]{
                        "Male",
                        "Female",
                        "Other"
                }
        );

        addressField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(
                new JLabel("Name:")
        );
        formPanel.add(nameField);

        formPanel.add(
                new JLabel("Age:")
        );
        formPanel.add(ageField);

        formPanel.add(
                new JLabel("Gender:")
        );
        formPanel.add(genderBox);

        formPanel.add(
                new JLabel("Address:")
        );
        formPanel.add(addressField);

        formPanel.add(
                new JLabel("Phone:")
        );
        formPanel.add(phoneField);

        // BUTTONS
        addButton = new JButton(
                "Add Suspect"
        );

        viewButton = new JButton(
                "View Suspects"
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
                e -> addSuspect()
        );

        viewButton.addActionListener(
                e -> viewSuspects()
        );

        // PRESS ENTER TO ADD
        phoneField.addActionListener(
                e -> addSuspect()
        );
    }

    // ADD SUSPECT
    private void addSuspect() {

        String name =
                nameField.getText().trim();

        String ageText =
                ageField.getText().trim();

        String gender =
                (String) genderBox.getSelectedItem();

        String address =
                addressField.getText().trim();

        String phone =
                phoneField.getText().trim();

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

        // AGE VALIDATION
        if (ageText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age is required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        int age;

        try {

            age = Integer.parseInt(ageText);

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (age < 1 || age > 120) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid age between 1 and 120.",
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

        // CREATE SUSPECT OBJECT
        Suspect suspect =
                new Suspect();

        suspect.setName(name);
        suspect.setAge(age);
        suspect.setGender(gender);
        suspect.setAddress(address);
        suspect.setPhone(phone);

        // SAVE TO DATABASE
        SuspectDAO suspectDAO =
                new SuspectDAO();

        boolean success =
                suspectDAO.addSuspect(
                        suspect
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Suspect added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // CLEAR FORM
            nameField.setText("");
            ageField.setText("");
            genderBox.setSelectedIndex(0);
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

    // VIEW ALL SUSPECTS
    private void viewSuspects() {

        SuspectDAO suspectDAO =
                new SuspectDAO();

        List<Suspect> suspects =
                suspectDAO.getAllSuspects();

        if (suspects.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No suspects found."
            );

            return;
        }

        StringBuilder details =
                new StringBuilder();

        details.append(
                "SUSPECT RECORDS\n"
        );

        details.append(
                "================================\n\n"
        );

        for (Suspect suspect : suspects) {

            details.append(
                            "Suspect ID: "
                    )
                    .append(
                            suspect.getPersonId()
                    )
                    .append("\n");

            details.append(
                            "Name: "
                    )
                    .append(
                            suspect.getName()
                    )
                    .append("\n");

            details.append(
                            "Age: "
                    )
                    .append(
                            suspect.getAge()
                    )
                    .append("\n");

            details.append(
                            "Gender: "
                    )
                    .append(
                            suspect.getGender()
                    )
                    .append("\n");

            details.append(
                            "Address: "
                    )
                    .append(
                            suspect.getAddress()
                    )
                    .append("\n");

            details.append(
                            "Phone: "
                    )
                    .append(
                            suspect.getPhone()
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
                "All Suspects",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}