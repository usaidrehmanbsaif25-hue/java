package com.srms.service;

import com.srms.dao.ResultDAO;
import com.srms.dao.SubjectDAO;
import com.srms.dao.impl.ResultDAOImpl;
import com.srms.dao.impl.SubjectDAOImpl;
import com.srms.exceptions.InvalidMarksException;
import com.srms.model.Result;
import com.srms.model.Subject;
import com.srms.utils.GradeCalculator;
import com.srms.utils.GPAUtil;

import java.util.List;

public class ResultService {
    private final ResultDAO resultDAO = new ResultDAOImpl();
    private final SubjectDAO subjectDAO = new SubjectDAOImpl();

    public boolean addResult(Result result) {
        validateMarks(result.getSubjectId(), result.getMarksObtained());
        result.setGrade(GradeCalculator.calculateGrade(result.getMarksObtained()));
        return resultDAO.addResult(result);
    }

    public boolean updateResult(Result result) {
        validateMarks(result.getSubjectId(), result.getMarksObtained());
        result.setGrade(GradeCalculator.calculateGrade(result.getMarksObtained()));
        return resultDAO.updateResult(result);
    }

    public boolean deleteResult(int resultId) {
        return resultDAO.deleteResult(resultId);
    }

    public List<Result> getResultsByStudentId(int studentId) {
        return resultDAO.getResultsByStudentId(studentId);
    }

    public List<Result> getAllResults() {
        return resultDAO.getAllResults();
    }

    public double calculateGpa(List<Result> results) {
        return GPAUtil.calculateGpa(results);
    }

    public boolean isPass(List<Result> results) {
        return results.stream().allMatch(result -> result.getMarksObtained() >= 50);
    }

    private void validateMarks(int subjectId, double marks) {
        Subject subject = subjectDAO.getSubjectById(subjectId);
        if (subject == null) {
            throw new InvalidMarksException("Subject not found for marks validation");
        }
        if (marks < 0 || marks > subject.getMaxMarks()) {
            throw new InvalidMarksException("Marks must be between 0 and " + subject.getMaxMarks());
        }
    }
}
