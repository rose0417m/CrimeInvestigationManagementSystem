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
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel headingLabel = new JLabel(
                "Witness Management",
                SwingConstants.CENTER
        );

        headingLabel.setFont(
                new Font("Arial", Font.BOLD, 22)
        );

        JPanel formPanel = new JPanel(
                new GridLayout(3, 2, 10, 10)
        );

        formPanel.setBorder(
                BorderFactory.createEmptyBorder(30, 40, 20, 40)
        );

        nameField = new JTextField();
        phoneField = new JTextField();
        statementArea = new JTextArea(4, 20);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Statement:"));
        formPanel.add(new JScrollPane(statementArea));

        addButton = new JButton("Add Witness");
        viewButton = new JButton("View Witnesses");

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        setLayout(new BorderLayout());

        add(headingLabel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addWitness());

        viewButton.addActionListener(e -> viewWitnesses());
    }

    private void addWitness() {

        String name = nameField.getText();
        String phone = phoneField.getText();
        String statement = statementArea.getText();

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Name is required!"
            );

            return;
        }

        Witness witness = new Witness();

        witness.setName(name);
        witness.setPhone(phone);
        witness.setStatement(statement);

        WitnessDAO witnessDAO = new WitnessDAO();

        boolean success = witnessDAO.addWitness(witness);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Witness added successfully!"
            );

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

    private void viewWitnesses() {

        WitnessDAO witnessDAO = new WitnessDAO();

        List<Witness> witnesses =
                witnessDAO.getAllWitnesses();

        if (witnesses.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "No witnesses found."
            );

            return;
        }

        StringBuilder details = new StringBuilder();

        for (Witness witness : witnesses) {

            details.append("Witness ID: ")
                    .append(witness.getPersonId())
                    .append("\n");

            details.append("Name: ")
                    .append(witness.getName())
                    .append("\n");

            details.append("Phone: ")
                    .append(witness.getPhone())
                    .append("\n");

            details.append("Statement: ")
                    .append(witness.getStatement())
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
                "All Witnesses",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}