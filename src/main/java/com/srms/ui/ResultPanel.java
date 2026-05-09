package com.srms.ui;

import com.srms.model.Result;
import com.srms.model.Student;
import com.srms.model.Subject;
import com.srms.service.ResultService;
import com.srms.service.StudentService;
import com.srms.service.SubjectService;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

public class ResultPanel extends JPanel {
    private final ResultService resultService = new ResultService();
    private final StudentService studentService = new StudentService();
    private final SubjectService subjectService = new SubjectService();

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Student", "Subject", "Marks", "Grade", "Semester"}, 0);
    private final JTable table = new JTable(tableModel);

    private final JComboBox<Student> studentBox = new JComboBox<>();
    private final JComboBox<Subject> subjectBox = new JComboBox<>();
    private final JTextField marksField = new JTextField();
    private final JTextField semesterField = new JTextField();

    public ResultPanel() {
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        refreshDropdowns();
        refreshTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 8, 10, 10));
        panel.add(new JLabel("Student"));
        panel.add(studentBox);
        panel.add(new JLabel("Subject"));
        panel.add(subjectBox);
        panel.add(new JLabel("Marks"));
        panel.add(marksField);
        panel.add(new JLabel("Semester"));
        panel.add(semesterField);
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
        refreshButton.addActionListener(event -> {
            refreshDropdowns();
            refreshTable();
        });

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(refreshButton);
        return panel;
    }

    private void handleAdd() {
        try {
            Result result = getResultFromForm();
            resultService.addResult(result);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Result added");
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
            Result result = getResultFromForm();
            result.setResultId((int) tableModel.getValueAt(selected, 0));
            resultService.updateResult(result);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Result updated");
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
        resultService.deleteResult(id);
        refreshTable();
        JOptionPane.showMessageDialog(this, "Result deleted");
    }

    private Result getResultFromForm() {
        Student student = (Student) studentBox.getSelectedItem();
        Subject subject = (Subject) subjectBox.getSelectedItem();
        Result result = new Result();
        result.setStudentId(student != null ? student.getStudentId() : 0);
        result.setSubjectId(subject != null ? subject.getSubjectId() : 0);
        result.setMarksObtained(Double.parseDouble(marksField.getText()));
        result.setSemester(semesterField.getText());
        return result;
    }

    private void refreshDropdowns() {
        studentBox.removeAllItems();
        subjectBox.removeAllItems();
        List<Student> students = studentService.getAllStudents();
        List<Subject> subjects = subjectService.getAllSubjects();
        for (Student student : students) {
            studentBox.addItem(student);
        }
        for (Subject subject : subjects) {
            subjectBox.addItem(subject);
        }
    }

    private void refreshTable() {
        List<Result> results = resultService.getAllResults();
        tableModel.setRowCount(0);
        for (Result result : results) {
            tableModel.addRow(new Object[]{
                    result.getResultId(),
                    result.getStudentId(),
                    result.getSubjectId(),
                    result.getMarksObtained(),
                    result.getGrade(),
                    result.getSemester()
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
