package com.srms.dao.impl;

import com.srms.dao.SubjectDAO;
import com.srms.database.DBConnection;
import com.srms.model.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAOImpl implements SubjectDAO {
    @Override
    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO subjects (subject_name, max_marks, class_name) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectName());
            statement.setInt(2, subject.getMaxMarks());
            statement.setString(3, subject.getClassName());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to add subject", exception);
        }
    }

    @Override
    public boolean updateSubject(Subject subject) {
        String sql = "UPDATE subjects SET subject_name=?, max_marks=?, class_name=? WHERE subject_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, subject.getSubjectName());
            statement.setInt(2, subject.getMaxMarks());
            statement.setString(3, subject.getClassName());
            statement.setInt(4, subject.getSubjectId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update subject", exception);
        }
    }

    @Override
    public boolean deleteSubject(int subjectId) {
        String sql = "DELETE FROM subjects WHERE subject_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to delete subject", exception);
        }
    }

    @Override
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT * FROM subjects WHERE subject_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapSubject(rs);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get subject", exception);
        }
        return null;
    }

    @Override
    public List<Subject> getAllSubjects() {
        String sql = "SELECT * FROM subjects";
        List<Subject> subjects = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                subjects.add(mapSubject(rs));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get subjects", exception);
        }
        return subjects;
    }

    private Subject mapSubject(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("subject_id"));
        subject.setSubjectName(rs.getString("subject_name"));
        subject.setMaxMarks(rs.getInt("max_marks"));
        subject.setClassName(rs.getString("class_name"));
        return subject;
    }
}
