package com.srms.utils;

import com.srms.model.Result;

import java.util.List;

public class GPAUtil {
    private GPAUtil() {
    }

    public static double calculateGpa(List<Result> results) {
        if (results == null || results.isEmpty()) {
            return 0;
        }
        double total = results.stream().mapToDouble(Result::getMarksObtained).sum();
        return total / results.size() / 20.0;
    }
}
