package com.srms.service;

import com.srms.dao.SubjectDAO;
import com.srms.dao.impl.SubjectDAOImpl;
import com.srms.model.Subject;
import com.srms.utils.Validator;

import java.util.List;

public class SubjectService {
    private final SubjectDAO subjectDAO = new SubjectDAOImpl();

    public boolean addSubject(Subject subject) {
        Validator.requireNonEmpty(subject.getSubjectName(), "Subject name is required");
        if (subject.getMaxMarks() <= 0) {
            throw new IllegalArgumentException("Max marks must be positive");
        }
        return subjectDAO.addSubject(subject);
    }

    public boolean updateSubject(Subject subject) {
        Validator.requireNonEmpty(subject.getSubjectName(), "Subject name is required");
        if (subject.getMaxMarks() <= 0) {
            throw new IllegalArgumentException("Max marks must be positive");
        }
        return subjectDAO.updateSubject(subject);
    }

    public boolean deleteSubject(int subjectId) {
        return subjectDAO.deleteSubject(subjectId);
    }

    public Subject getSubjectById(int subjectId) {
        return subjectDAO.getSubjectById(subjectId);
    }

    public List<Subject> getAllSubjects() {
        return subjectDAO.getAllSubjects();
    }
}
