package com.srms.utils;

import com.srms.exceptions.InvalidMarksException;

public class GradeCalculator {
    private GradeCalculator() {
    }

    public static String calculateGrade(double marksPercentage) {
        if (marksPercentage < 0 || marksPercentage > 100) {
            throw new InvalidMarksException("Marks percentage must be between 0 and 100");
        }
        if (marksPercentage >= 90) {
            return "A+";
        }
        if (marksPercentage >= 80) {
            return "A";
        }
        if (marksPercentage >= 70) {
            return "B";
        }
        if (marksPercentage >= 60) {
            return "C";
        }
        if (marksPercentage >= 50) {
            return "D";
        }
        return "F";
    }
}
