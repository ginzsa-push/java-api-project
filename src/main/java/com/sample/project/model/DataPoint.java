package com.sample.project.model;

public class DataPoint {
    
    private String id;
    private int unit;
    private String originalUnit;

    public DataPoint(String _id, int unit, String originalUnit) {
        this.id = _id;
        this.unit = unit;
        this.originalUnit = originalUnit;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getOriginalUnit() {
        return this.originalUnit;
    }

    public void setOriginalUnit(String originalUnit) {
        this.originalUnit = originalUnit;
    }
}