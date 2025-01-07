package com.university.model;

import javafx.beans.property.*;

public class Staff {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty role = new SimpleStringProperty();
    private final StringProperty contractType = new SimpleStringProperty();
    private final IntegerProperty atsr = new SimpleIntegerProperty();
    private final IntegerProperty ts = new SimpleIntegerProperty();
    private final IntegerProperty tlr = new SimpleIntegerProperty();
    private final IntegerProperty sa = new SimpleIntegerProperty();
    private final IntegerProperty other = new SimpleIntegerProperty();

    public Staff(String name, String role, String contractType) {
        this.name.set(name);
        this.role.set(role);
        this.contractType.set(contractType);
    }

    // Getters and setters for all properties
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
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

    public String getContractType() {
        return contractType.get();
    }

    public void setContractType(String value) {
        contractType.set(value);
    }

    public StringProperty contractTypeProperty() {
        return contractType;
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

    public int getTlr() {
        return tlr.get();
    }

    public void setTlr(int value) {
        tlr.set(value);
    }

    public IntegerProperty tlrProperty() {
        return tlr;
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

    public int getTotal() {
        return getAtsr() + getTs() + getTlr() + getSa() + getOther();
    }

    @Override
    public String toString() {
        return getName();
    }
}
