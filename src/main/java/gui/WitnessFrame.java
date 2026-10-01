package gui;

import dao.WitnessDAO;
import model.Witness;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WitnessFrame extends JFrame {

    private JTextField nameField;
    private JTextField phoneField;
    private JTextArea statementArea;

    private JButton addButton;
    private JButton viewButton;

    public WitnessFrame() {

        setTitle("Witness Management");
        setSize(650, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // HEADING
        JLabel headingLabel = new JLabel(
                "Witness Management",
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
                new GridLayout(3, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 50, 20, 50
                )
        );

        nameField = new JTextField();
        phoneField = new JTextField();

        statementArea = new JTextArea(5, 20);

        statementArea.setLineWrap(true);
        statementArea.setWrapStyleWord(true);

        formPanel.add(
                new JLabel("Name:")
        );

        formPanel.add(
                nameField
        );

        formPanel.add(
                new JLabel("Phone:")
        );

        formPanel.add(
                phoneField
        );

        formPanel.add(
                new JLabel("Statement:")
        );

        formPanel.add(
                new JScrollPane(statementArea)
        );

        // BUTTONS
        addButton = new JButton(
                "Add Witness"
        );

        viewButton = new JButton(
                "View Witnesses"
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
                e -> addWitness()
        );

        viewButton.addActionListener(
                e -> viewWitnesses()
        );

        // PRESS ENTER TO ADD
        phoneField.addActionListener(
                e -> addWitness()
        );
    }

    // ADD WITNESS
    private void addWitness() {

        String name =
                nameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        String statement =
                statementArea.getText().trim();

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

        // STATEMENT VALIDATION
        if (statement.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Witness statement is required!",
                    "Validation",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // CREATE WITNESS OBJECT
        Witness witness =
                new Witness();

        witness.setName(name);
        witness.setPhone(phone);
        witness.setStatement(statement);

        // SAVE TO DATABASE
        WitnessDAO witnessDAO =
                new WitnessDAO();

        boolean success =
                witnessDAO.addWitness(
                        witness
                );

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Witness added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // CLEAR FORM
            nameField.setText("");
            phoneField.setText("");
            statementArea.setText("");

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to add witness!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // VIEW ALL WITNESSES
    private void viewWitnesses() {

        WitnessDAO witnessDAO =
                new WitnessDAO();

        List<Witness> witnesses =
                witnessDAO.getAllWitnesses();

        if (witnesses.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No witnesses found."
            );

            return;
        }

        StringBuilder details =
                new StringBuilder();

        details.append(
                "WITNESS RECORDS\n"
        );

        details.append(
                "================================\n\n"
        );

        for (Witness witness : witnesses) {

            details.append(
                            "Witness ID: "
                    )
                    .append(
                            witness.getPersonId()
                    )
                    .append("\n");

            details.append(
                            "Name: "
                    )
                    .append(
                            witness.getName()
                    )
                    .append("\n");

            details.append(
                            "Phone: "
                    )
                    .append(
                            witness.getPhone()
                    )
                    .append("\n");

            details.append(
                            "Statement: "
                    )
                    .append(
                            witness.getStatement()
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

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

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
                "All Witnesses",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}