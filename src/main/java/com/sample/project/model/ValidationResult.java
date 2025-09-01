package com.sample.project.model;


public class ValidationResult {
    private String number;
    private String time_unit;

    public ValidationResult(String number, String time_unit) {
        this.number = number;
        this.time_unit = time_unit;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTime_unit() {
        return time_unit;
    }

    public void setTime_unit(String time_unit) {
        this.time_unit = time_unit;
    }
}