package com.srms.ui;

import com.srms.model.Subject;
import com.srms.service.SubjectService;

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
import java.util.List;

public class SubjectPanel extends JPanel {
    private final SubjectService subjectService = new SubjectService();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Max Marks", "Class"}, 0);
    private final JTable table = new JTable(tableModel);

    private final JTextField nameField = new JTextField();
    private final JTextField maxMarksField = new JTextField();
    private final JTextField classField = new JTextField();

    public SubjectPanel() {
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(buildFormPanel(), BorderLayout.NORTH);
        add(buildButtonPanel(), BorderLayout.SOUTH);
        refreshTable();
    }

    private JPanel buildFormPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 6, 10, 10));
        panel.add(new JLabel("Subject"));
        panel.add(nameField);
        panel.add(new JLabel("Max Marks"));
        panel.add(maxMarksField);
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
            Subject subject = getSubjectFromForm();
            subjectService.addSubject(subject);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Subject added");
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
            Subject subject = getSubjectFromForm();
            subject.setSubjectId((int) tableModel.getValueAt(selected, 0));
            subjectService.updateSubject(subject);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Subject updated");
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
        subjectService.deleteSubject(id);
        refreshTable();
        JOptionPane.showMessageDialog(this, "Subject deleted");
    }

    private Subject getSubjectFromForm() {
        Subject subject = new Subject();
        subject.setSubjectName(nameField.getText());
        subject.setMaxMarks(Integer.parseInt(maxMarksField.getText()));
        subject.setClassName(classField.getText());
        return subject;
    }

    private void refreshTable() {
        List<Subject> subjects = subjectService.getAllSubjects();
        tableModel.setRowCount(0);
        for (Subject subject : subjects) {
            tableModel.addRow(new Object[]{
                    subject.getSubjectId(),
                    subject.getSubjectName(),
                    subject.getMaxMarks(),
                    subject.getClassName()
            });
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
