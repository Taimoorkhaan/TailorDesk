package com.digiconvalley.tailordesk.Model;

public class MeasurementDetail {
    private int measurementId;
    private int measurementValue;
    private int measurementName;

    public MeasurementDetail(int measurementId, int measurementValue, int measurementName) {
        this.measurementId = measurementId;
        this.measurementValue = measurementValue;
        this.measurementName = measurementName;
    }

    public int getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(int measurementId) {
        this.measurementId = measurementId;
    }

    public int getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(int measurementValue) {
        this.measurementValue = measurementValue;
    }

    public int getMeasurementName() {
        return measurementName;
    }

    public void setMeasurementName(int measurementName) {
        this.measurementName = measurementName;
    }
}
