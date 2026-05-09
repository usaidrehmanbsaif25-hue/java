package com.srms.ui;

import com.srms.model.Student;
import com.srms.service.StudentService;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

public class StudentPanel extends JPanel {
    private final StudentService studentService = new StudentService();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Roll", "Name", "Gender", "DOB", "Email", "Phone", "Class"}, 0);
    private final JTable table = new JTable(tableModel);

    private final JTextField rollField = new JTextField();
    private final JTextField nameField = new JTextField();
    private final JTextField genderField = new JTextField();
    private final JTextField dobField = new JTextField("YYYY-MM-DD");
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField classField = new JTextField();

    public StudentPanel() {
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        refreshTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 10, 10));
        panel.add(new JLabel("Roll No"));
        panel.add(rollField);
        panel.add(new JLabel("Full Name"));
        panel.add(nameField);
        panel.add(new JLabel("Gender"));
        panel.add(genderField);
        panel.add(new JLabel("DOB"));
        panel.add(dobField);
        panel.add(new JLabel("Email"));
        panel.add(emailField);
        panel.add(new JLabel("Phone"));
        panel.add(phoneField);
        panel.add(new JLabel("Class"));
        panel.add(classField);
        return panel;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(event -> handleAdd());
        updateButton.addActionListener(event -> handleUpdate());
        deleteButton.addActionListener(event -> handleDelete());
        refreshButton.addActionListener(event -> refreshTable());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        return panel;
    }

    private void handleAdd() {
        try {
            Student student = getStudentFromForm();
            studentService.addStudent(student);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Student added");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void handleUpdate() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            showError("Select a row to update");
            return;
        }
        try {
            Student student = getStudentFromForm();
            student.setStudentId((int) tableModel.getValueAt(selected, 0));
            studentService.updateStudent(student);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Student updated");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void handleDelete() {
        int selected = table.getSelectedRow();
        if (selected == -1) {
            showError("Select a row to delete");
            return;
        }
        int id = (int) tableModel.getValueAt(selected, 0);
        studentService.deleteStudent(id);
        refreshTable();
        JOptionPane.showMessageDialog(this, "Student deleted");
    }

    private Student getStudentFromForm() {
        Student student = new Student();
        student.setRollNo(rollField.getText());
        student.setFullName(nameField.getText());
        student.setGender(genderField.getText());
        String dobText = dobField.getText().trim();
        if (!dobText.isEmpty() && !"YYYY-MM-DD".equals(dobText)) {
            student.setDob(LocalDate.parse(dobText));
        }
        student.setEmail(emailField.getText());
        student.setPhone(phoneField.getText());
        student.setClassName(classField.getText());
        return student;
    }

    private void refreshTable() {
        List<Student> students = studentService.getAllStudents();
        tableModel.setRowCount(0);
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getStudentId(),
                    student.getRollNo(),
                    student.getFullName(),
                    student.getGender(),
                    student.getDob(),
                    student.getEmail(),
                    student.getPhone(),
                    student.getClassName()
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
