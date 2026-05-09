package com.srms.ui;

import com.srms.model.Result;
import com.srms.model.Student;
import com.srms.service.ResultService;
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
import java.awt.FlowLayout;
import java.util.List;

public class ViewResultPanel extends JPanel {
    private final StudentService studentService = new StudentService();
    private final ResultService resultService = new ResultService();
    private final JTextField rollField = new JTextField(10);
    private final JLabel gpaLabel = new JLabel("GPA: 0.00");
    private final JLabel passLabel = new JLabel("Status: N/A");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Result ID", "Subject ID", "Marks", "Grade", "Semester"}, 0);
    private final JTable table = new JTable(tableModel);

    public ViewResultPanel() {
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Roll No:"));
        topPanel.add(rollField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(event -> handleSearch());
        topPanel.add(searchButton);
        topPanel.add(gpaLabel);
        topPanel.add(passLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void handleSearch() {
        try {
            String rollNo = rollField.getText().trim();
            Student student = studentService.getStudentByRollNo(rollNo);
            if (student == null) {
                showError("Student not found");
                return;
            }
            List<Result> results = resultService.getResultsByStudentId(student.getStudentId());
            tableModel.setRowCount(0);
            for (Result result : results) {
                tableModel.addRow(new Object[]{
                        result.getResultId(),
                        result.getSubjectId(),
                        result.getMarksObtained(),
                        result.getGrade(),
                        result.getSemester()
                });
            }
            gpaLabel.setText(String.format("GPA: %.2f", resultService.calculateGpa(results)));
            passLabel.setText("Status: " + (resultService.isPass(results) ? "PASS" : "FAIL"));
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
