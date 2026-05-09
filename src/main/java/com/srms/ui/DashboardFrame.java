package com.srms.ui;

import com.srms.service.AuthService;
import com.srms.service.ResultService;
import com.srms.service.StudentService;
import com.srms.service.SubjectService;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class DashboardFrame extends JFrame {
    private final AuthService authService;
    private final StudentService studentService = new StudentService();
    private final SubjectService subjectService = new SubjectService();
    private final ResultService resultService = new ResultService();

    public DashboardFrame(AuthService authService) {
        this.authService = authService;
        setTitle("Student Result Management System - Dashboard");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel header = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + authService.getCurrentUser().getUsername());
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(event -> {
            authService.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        header.add(welcomeLabel, BorderLayout.WEST);
        header.add(logoutButton, BorderLayout.EAST);

        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        statsPanel.add(new JLabel("Students: " + studentService.getAllStudents().size()));
        statsPanel.add(new JLabel("Subjects: " + subjectService.getAllSubjects().size()));
        statsPanel.add(new JLabel("Results: " + resultService.getAllResults().size()));
        statsPanel.add(new JLabel("Pass %: " + calculatePassPercentage()));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", new StudentPanel());
        tabs.addTab("Subjects", new SubjectPanel());
        tabs.addTab("Results", new ResultPanel());
        tabs.addTab("View Results", new ViewResultPanel());

        add(header, BorderLayout.NORTH);
        add(statsPanel, BorderLayout.SOUTH);
        add(tabs, BorderLayout.CENTER);
    }

    private String calculatePassPercentage() {
        long total = resultService.getAllResults().stream().map(ResultRow::new).map(ResultRow::getStudentId).distinct().count();
        if (total == 0) {
            return "0%";
        }
        long passCount = resultService.getAllResults().stream().map(ResultRow::new)
                .collect(java.util.stream.Collectors.groupingBy(ResultRow::getStudentId))
                .values()
                .stream()
                .filter(rows -> rows.stream().allMatch(row -> row.getMarks() >= 50))
                .count();
        double percent = (passCount * 100.0) / total;
        return String.format("%.2f%%", percent);
    }

    private static class ResultRow {
        private final int studentId;
        private final double marks;

        private ResultRow(com.srms.model.Result result) {
            this.studentId = result.getStudentId();
            this.marks = result.getMarksObtained();
        }

        public int getStudentId() {
            return studentId;
        }

        public double getMarks() {
            return marks;
        }
    }
}
