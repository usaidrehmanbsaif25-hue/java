package com.srms.dao.impl;

import com.srms.dao.StudentDAO;
import com.srms.database.DBConnection;
import com.srms.model.Student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (roll_no, full_name, gender, dob, email, phone, class_name) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getRollNo());
            statement.setString(2, student.getFullName());
            statement.setString(3, student.getGender());
            statement.setDate(4, student.getDob() != null ? Date.valueOf(student.getDob()) : null);
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getPhone());
            statement.setString(7, student.getClassName());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to add student", exception);
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET roll_no=?, full_name=?, gender=?, dob=?, email=?, phone=?, class_name=? WHERE student_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getRollNo());
            statement.setString(2, student.getFullName());
            statement.setString(3, student.getGender());
            statement.setDate(4, student.getDob() != null ? Date.valueOf(student.getDob()) : null);
            statement.setString(5, student.getEmail());
            statement.setString(6, student.getPhone());
            statement.setString(7, student.getClassName());
            statement.setInt(8, student.getStudentId());
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to update student", exception);
        }
    }

    @Override
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            return statement.executeUpdate() > 0;
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to delete student", exception);
        }
    }

    @Override
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get student", exception);
        }
        return null;
    }

    @Override
    public Student getStudentByRollNo(String rollNo) {
        String sql = "SELECT * FROM students WHERE roll_no=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rollNo);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get student by roll", exception);
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        List<Student> students = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapStudent(rs));
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Failed to get students", exception);
        }
        return students;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getInt("student_id"));
        student.setRollNo(rs.getString("roll_no"));
        student.setFullName(rs.getString("full_name"));
        student.setGender(rs.getString("gender"));
        Date dob = rs.getDate("dob");
        if (dob != null) {
            student.setDob(dob.toLocalDate());
        }
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setClassName(rs.getString("class_name"));
        return student;
    }
}
