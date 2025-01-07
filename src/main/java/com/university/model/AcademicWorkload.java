package com.university.model;

import javafx.beans.property.*;

public class AcademicWorkload {
    private final StringProperty staffName = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty();
    private final IntegerProperty atsr = new SimpleIntegerProperty();
    private final IntegerProperty ts = new SimpleIntegerProperty();
    private final IntegerProperty sa = new SimpleIntegerProperty();
    private final IntegerProperty other = new SimpleIntegerProperty();
    private final IntegerProperty tlr = new SimpleIntegerProperty();

    public AcademicWorkload(String staffName, String role, int atsr, int ts, int sa, int other, int tlr) {
        this.staffName.set(staffName);
        this.role.set(role);
        this.atsr.set(atsr);
        this.ts.set(ts);
        this.sa.set(sa);
        this.other.set(other);
        this.tlr.set(tlr);
    }

    // Getters and setters for all properties
    public String getStaffName() {
        return staffName.get();
    }

    public void setStaffName(String value) {
        staffName.set(value);
    }

    public StringProperty staffNameProperty() {
        return staffName;
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String value) {
        role.set(value);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public int getAtsr() {
        return atsr.get();
    }

    public void setAtsr(int value) {
        atsr.set(value);
    }

    public IntegerProperty atsrProperty() {
        return atsr;
    }

    public int getTs() {
        return ts.get();
    }

    public void setTs(int value) {
        ts.set(value);
    }

    public IntegerProperty tsProperty() {
        return ts;
    }

    public int getSa() {
        return sa.get();
    }

    public void setSa(int value) {
        sa.set(value);
    }

    public IntegerProperty saProperty() {
        return sa;
    }

    public int getOther() {
        return other.get();
    }

    public void setOther(int value) {
        other.set(value);
    }

    public IntegerProperty otherProperty() {
        return other;
    }

    public int getTlr() {
        return tlr.get();
    }

    public void setTlr(int value) {
        tlr.set(value);
    }

    public IntegerProperty tlrProperty() {
        return tlr;
    }

    public int getTotal() {
        return getAtsr() + getTs() + getSa() + getOther() + getTlr();
    }
}
