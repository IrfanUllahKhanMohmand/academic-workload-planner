package com.university.model;

import javafx.beans.property.*;

public class Activity {
    private final ObjectProperty<Staff> staff = new SimpleObjectProperty<>();
    private final StringProperty type = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty trimester = new SimpleStringProperty();
    private final DoubleProperty hoursPerInstance = new SimpleDoubleProperty();
    private final IntegerProperty instances = new SimpleIntegerProperty();
    private final DoubleProperty totalHours = new SimpleDoubleProperty();

    public Activity(Staff staff, String type, String description,
            String trimester, double hoursPerInstance, int instances) {
        this.staff.set(staff);
        this.type.set(type);
        this.description.set(description);
        this.trimester.set(trimester);
        this.hoursPerInstance.set(hoursPerInstance);
        this.instances.set(instances);
        this.totalHours.set(hoursPerInstance * instances);
    }

    // Getters and setters for all properties
    public Staff getStaff() {
        return staff.get();
    }

    public void setStaff(Staff value) {
        staff.set(value);
    }

    public ObjectProperty<Staff> staffProperty() {
        return staff;
    }

    public String getType() {
        return type.get();
    }

    public void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getTrimester() {
        return trimester.get();
    }

    public void setTrimester(String value) {
        trimester.set(value);
    }

    public StringProperty trimesterProperty() {
        return trimester;
    }

    public double getHoursPerInstance() {
        return hoursPerInstance.get();
    }

    public void setHoursPerInstance(double value) {
        hoursPerInstance.set(value);
        updateTotalHours();
    }

    public DoubleProperty hoursPerInstanceProperty() {
        return hoursPerInstance;
    }

    public int getInstances() {
        return instances.get();
    }

    public void setInstances(int value) {
        instances.set(value);
        updateTotalHours();
    }

    public IntegerProperty instancesProperty() {
        return instances;
    }

    public double getTotalHours() {
        return totalHours.get();
    }

    public DoubleProperty totalHoursProperty() {
        return totalHours;
    }

    private void updateTotalHours() {
        totalHours.set(hoursPerInstance.get() * instances.get());
    }
}
