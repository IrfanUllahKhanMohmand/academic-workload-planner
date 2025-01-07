package com.university.controller;

import com.university.model.AcademicWorkload;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainViewController {

    @FXML
    private TextField staffNameField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private TextField atsrField;
    @FXML
    private TextField tsField;
    @FXML
    private TextField saField;
    @FXML
    private TextField otherField;
    @FXML
    private TextField tlrField;
    @FXML
    private TableView<AcademicWorkload> workloadTable;
    @FXML
    private TableColumn<AcademicWorkload, String> nameColumn;
    @FXML
    private TableColumn<AcademicWorkload, String> roleColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> atsrColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> tsColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> saColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> otherColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> tlrColumn;
    @FXML
    private TableColumn<AcademicWorkload, Integer> totalColumn;

    private ObservableList<AcademicWorkload> workloads = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        atsrColumn.setCellValueFactory(new PropertyValueFactory<>("atsr"));
        tsColumn.setCellValueFactory(new PropertyValueFactory<>("ts"));
        saColumn.setCellValueFactory(new PropertyValueFactory<>("sa"));
        otherColumn.setCellValueFactory(new PropertyValueFactory<>("other"));
        tlrColumn.setCellValueFactory(new PropertyValueFactory<>("tlr"));
        totalColumn
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotal()).asObject());

        workloadTable.setItems(workloads);

        roleComboBox.getItems().addAll("Lecturer", "Deputy Head of Subject", "Teaching and Research", "Reader",
                "Professor");
        roleComboBox.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> updateFieldsBasedOnRole(newVal));
    }

    private void updateFieldsBasedOnRole(String role) {
        switch (role) {
            case "Lecturer":
                atsrField.setText("550");
                tsField.setText("660");
                saField.setText("188");
                otherField.setText("172");
                tlrField.setText("0");
                break;
            case "Deputy Head of Subject":
                atsrField.setText("482");
                tsField.setText("578");
                saField.setText("188");
                otherField.setText("322");
                tlrField.setText("0");
                break;
            case "Teaching and Research":
                atsrField.setText("450");
                tsField.setText("540");
                saField.setText("188");
                otherField.setText("392");
                tlrField.setText("0");
                break;
            case "Reader":
                atsrField.setText("400");
                tsField.setText("480");
                saField.setText("188");
                otherField.setText("502");
                tlrField.setText("0");
                break;
            case "Professor":
                atsrField.setText("350");
                tsField.setText("420");
                saField.setText("188");
                otherField.setText("612");
                tlrField.setText("0");
                break;
        }
    }

    @FXML
    private void addWorkload() {
        try {
            String name = staffNameField.getText();
            String role = roleComboBox.getValue();
            int atsr = Integer.parseInt(atsrField.getText());
            int ts = Integer.parseInt(tsField.getText());
            int sa = Integer.parseInt(saField.getText());
            int other = Integer.parseInt(otherField.getText());
            int tlr = Integer.parseInt(tlrField.getText());

            AcademicWorkload workload = new AcademicWorkload(name, role, atsr, ts, sa, other, tlr);
            workloads.add(workload);

            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid numbers for all fields.");
        }
    }

    private void clearFields() {
        staffNameField.clear();
        roleComboBox.getSelectionModel().clearSelection();
        atsrField.clear();
        tsField.clear();
        saField.clear();
        otherField.clear();
        tlrField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
