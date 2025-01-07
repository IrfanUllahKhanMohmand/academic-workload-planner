package com.university.controller;

import com.university.model.Activity;
import com.university.model.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class MainViewController {

    @FXML
    private TextField staffNameField;
    @FXML
    private TextField roleField;
    @FXML
    private ComboBox<String> contractTypeComboBox;
    @FXML
    private ComboBox<Staff> staffComboBox;
    @FXML
    private ComboBox<String> activityTypeComboBox;
    @FXML
    private TextField activityDescriptionField;
    @FXML
    private ComboBox<String> trimesterComboBox;
    @FXML
    private TextField hoursPerInstanceField;
    @FXML
    private TextField instancesField;
    @FXML
    private TableView<Activity> activitiesTable;
    @FXML
    private TableColumn<Activity, String> staffNameColumn;
    @FXML
    private TableColumn<Activity, String> roleColumn;
    @FXML
    private TableColumn<Activity, String> contractTypeColumn;
    @FXML
    private TableColumn<Activity, String> typeColumn;
    @FXML
    private TableColumn<Activity, String> descriptionColumn;
    @FXML
    private TableColumn<Activity, String> trimesterColumn;
    @FXML
    private TableColumn<Activity, Double> hoursPerInstanceColumn;
    @FXML
    private TableColumn<Activity, Integer> instancesColumn;
    @FXML
    private TableColumn<Activity, Double> totalHoursColumn;
    @FXML
    private Label totalHoursLabel;

    private ObservableList<Staff> staffList = FXCollections.observableArrayList();
    private ObservableList<Activity> activities = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        contractTypeComboBox.getItems().addAll("Full-Time", "Part-Time");
        activityTypeComboBox.getItems().addAll("ATSR", "TS", "TLR", "SA", "Other");
        trimesterComboBox.getItems().addAll("Trimester 1", "Trimester 2", "Trimester 3", "All Year");

        staffNameColumn.setCellValueFactory(cellData -> cellData.getValue().getStaff().nameProperty());
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getStaff().roleProperty());
        contractTypeColumn.setCellValueFactory(cellData -> cellData.getValue().getStaff().contractTypeProperty());
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        trimesterColumn.setCellValueFactory(new PropertyValueFactory<>("trimester"));
        hoursPerInstanceColumn.setCellValueFactory(new PropertyValueFactory<>("hoursPerInstance"));
        instancesColumn.setCellValueFactory(new PropertyValueFactory<>("instances"));
        totalHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalHours"));

        activitiesTable.setItems(activities);
        staffComboBox.setItems(staffList);

        activities.addListener(
                (javafx.collections.ListChangeListener.Change<? extends Activity> c) -> updateTotalHours());
    }

    @FXML
    private void addStaff() {
        String name = staffNameField.getText();
        String role = roleField.getText();
        String contractType = contractTypeComboBox.getValue();

        if (name.isEmpty() || role.isEmpty() || contractType == null) {
            showAlert("Please fill in all staff fields.");
            return;
        }

        Staff staff = new Staff(name, role, contractType);
        staffList.add(staff);
        staffComboBox.getSelectionModel().select(staff);

        clearStaffFields();
    }

    @FXML
    private void addActivity() {
        Staff selectedStaff = staffComboBox.getValue();
        String type = activityTypeComboBox.getValue();
        String description = activityDescriptionField.getText();
        String trimester = trimesterComboBox.getValue();

        if (selectedStaff == null || type == null || description.isEmpty() || trimester == null ||
                hoursPerInstanceField.getText().isEmpty() || instancesField.getText().isEmpty()) {
            showAlert("Please fill in all activity fields.");
            return;
        }

        double hoursPerInstance = Double.parseDouble(hoursPerInstanceField.getText());
        int instances = Integer.parseInt(instancesField.getText());

        Activity activity = new Activity(selectedStaff, type, description, trimester, hoursPerInstance, instances);
        activities.add(activity);

        updateStaffHours(selectedStaff, type, activity.getTotalHours());

        clearActivityFields();
    }

    private void updateStaffHours(Staff staff, String activityType, double hours) {
        switch (activityType) {
            case "ATSR":
                staff.setAtsr(staff.getAtsr() + (int) hours);
                break;
            case "TS":
                staff.setTs(staff.getTs() + (int) hours);
                break;
            case "TLR":
                staff.setTlr(staff.getTlr() + (int) hours);
                break;
            case "SA":
                staff.setSa(staff.getSa() + (int) hours);
                break;
            case "Other":
                staff.setOther(staff.getOther() + (int) hours);
                break;
        }
    }

    private void clearStaffFields() {
        staffNameField.clear();
        roleField.clear();
        contractTypeComboBox.getSelectionModel().clearSelection();
    }

    private void clearActivityFields() {
        activityTypeComboBox.getSelectionModel().clearSelection();
        activityDescriptionField.clear();
        trimesterComboBox.getSelectionModel().clearSelection();
        hoursPerInstanceField.clear();
        instancesField.clear();
    }

    private void updateTotalHours() {
        double total = activities.stream().mapToDouble(Activity::getTotalHours).sum();
        totalHoursLabel.setText(String.format("Total Hours: %.2f", total));
    }

    @FXML
    private void exportToCSV() {
        if (activities.isEmpty()) {
            showAlert("No activities to export.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Workload Summary");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(
                        "Staff Name,Role,Contract Type,Activity Type,Description,Trimester,Hours Per Instance,Instances,Total Hours\n");
                for (Activity activity : activities) {
                    Staff staff = activity.getStaff();
                    writer.write(String.format("%s,%s,%s,%s,%s,%s,%.2f,%d,%.2f\n",
                            staff.getName(),
                            staff.getRole(),
                            staff.getContractType(),
                            activity.getType(),
                            activity.getDescription(),
                            activity.getTrimester(),
                            activity.getHoursPerInstance(),
                            activity.getInstances(),
                            activity.getTotalHours()));
                }

                writer.write("\nStaff Summary\n");
                for (Staff staff : staffList) {
                    writer.write(String.format("%s,%s,%s,ATSR,%.2f,TS,%.2f,TLR,%.2f,SA,%.2f,Other,%.2f,Total,%.2f\n",
                            staff.getName(),
                            staff.getRole(),
                            staff.getContractType(),
                            (double) staff.getAtsr(),
                            (double) staff.getTs(),
                            (double) staff.getTlr(),
                            (double) staff.getSa(),
                            (double) staff.getOther(),
                            (double) staff.getTotal()));
                }

                writer.write("\nOverall Summary\n");
                Map<String, Double> totalsByType = activities.stream()
                        .collect(Collectors.groupingBy(Activity::getType,
                                Collectors.summingDouble(Activity::getTotalHours)));

                for (Map.Entry<String, Double> entry : totalsByType.entrySet()) {
                    writer.write(String.format("%s,%.2f\n", entry.getKey(), entry.getValue()));
                }

                writer.write(
                        String.format("Total,%.2f\n", activities.stream().mapToDouble(Activity::getTotalHours).sum()));

                showAlert("Workload summary exported successfully.");
            } catch (IOException e) {
                showAlert("Error exporting workload summary: " + e.getMessage());
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
