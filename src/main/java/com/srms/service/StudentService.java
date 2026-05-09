package com.srms.service;

import com.srms.dao.StudentDAO;
import com.srms.dao.impl.StudentDAOImpl;
import com.srms.exceptions.DuplicateStudentException;
import com.srms.model.Student;
import com.srms.utils.Validator;

import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAOImpl();

    public boolean addStudent(Student student) {
        Validator.requireNonEmpty(student.getRollNo(), "Roll number is required");
        Validator.requireNonEmpty(student.getFullName(), "Full name is required");
        if (!Validator.isEmailValid(student.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (studentDAO.getStudentByRollNo(student.getRollNo()) != null) {
            throw new DuplicateStudentException("Roll number already exists");
        }
        return studentDAO.addStudent(student);
    }

    public boolean updateStudent(Student student) {
        Validator.requireNonEmpty(student.getRollNo(), "Roll number is required");
        Validator.requireNonEmpty(student.getFullName(), "Full name is required");
        if (!Validator.isEmailValid(student.getEmail())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int studentId) {
        return studentDAO.deleteStudent(studentId);
    }

    public Student getStudentById(int studentId) {
        return studentDAO.getStudentById(studentId);
    }

    public Student getStudentByRollNo(String rollNo) {
        return studentDAO.getStudentByRollNo(rollNo);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}
