package com.srms.dao.impl;

import com.srms.dao.ResultDAO;
import com.srms.database.DBConnection;
import com.srms.model.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResultDAOImpl implements ResultDAO {
    @Override
    public boolean addResult(Result result) {
        String sql = "INSERT INTO results (student_id, subject_id, marks_obtained, grade, semester) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, result.getStudentId());
            statement.setInt(2, result.getSubjectId());
            statement.setDouble(3, result.getMarksObtained());
            statement.setString(4, result.getGrade());
            statement.setString(5, result.getSemester());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to add result", exception);
        }
    }

    @Override
    public boolean updateResult(Result result) {
        String sql = "UPDATE results SET student_id=?, subject_id=?, marks_obtained=?, grade=?, semester=? WHERE result_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, result.getStudentId());
            statement.setInt(2, result.getSubjectId());
            statement.setDouble(3, result.getMarksObtained());
            statement.setString(4, result.getGrade());
            statement.setString(5, result.getSemester());
            statement.setInt(6, result.getResultId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update result", exception);
        }
    }

    @Override
    public boolean deleteResult(int resultId) {
        String sql = "DELETE FROM results WHERE result_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, resultId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to delete result", exception);
        }
    }

    @Override
    public List<Result> getResultsByStudentId(int studentId) {
        String sql = "SELECT * FROM results WHERE student_id=?";
        List<Result> results = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    results.add(mapResult(rs));
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get results", exception);
        }
        return results;
    }

    @Override
    public List<Result> getAllResults() {
        String sql = "SELECT * FROM results";
        List<Result> results = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                results.add(mapResult(rs));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get results", exception);
        }
        return results;
    }

    private Result mapResult(ResultSet rs) throws SQLException {
        Result result = new Result();
        result.setResultId(rs.getInt("result_id"));
        result.setStudentId(rs.getInt("student_id"));
        result.setSubjectId(rs.getInt("subject_id"));
        result.setMarksObtained(rs.getDouble("marks_obtained"));
        result.setGrade(rs.getString("grade"));
        result.setSemester(rs.getString("semester"));
        return result;
    }
}
