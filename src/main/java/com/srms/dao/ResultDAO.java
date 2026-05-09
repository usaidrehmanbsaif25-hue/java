package com.srms.dao;

import com.srms.model.Result;

import java.util.List;

public interface ResultDAO {
    boolean addResult(Result result);

    boolean updateResult(Result result);

    boolean deleteResult(int resultId);

    List<Result> getResultsByStudentId(int studentId);

    List<Result> getAllResults();
}
