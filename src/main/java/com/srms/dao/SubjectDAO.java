package com.srms.dao;

import com.srms.model.Subject;

import java.util.List;

public interface SubjectDAO {
    boolean addSubject(Subject subject);

    boolean updateSubject(Subject subject);

    boolean deleteSubject(int subjectId);

    Subject getSubjectById(int subjectId);

    List<Subject> getAllSubjects();
}
