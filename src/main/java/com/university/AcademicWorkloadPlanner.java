package com.university;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AcademicWorkloadPlanner {
    private List<Staff> staffList;
    private List<Activity> activities;
    private Scanner scanner;

    public AcademicWorkloadPlanner() {
        staffList = new ArrayList<>();
        activities = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("Welcome to the Academic Workload Planner");

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Add Staff");
            System.out.println("2. Add Activity");
            System.out.println("3. View Staff");
            System.out.println("4. View Activities");
            System.out.println("5. Export to CSV");
            System.out.println("6. Exit");

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStaff();
                    break;
                case 2:
                    addActivity();
                    break;
                case 3:
                    viewStaff();
                    break;
                case 4:
                    viewActivities();
                    break;
                case 5:
                    exportToCSV();
                    break;
                case 6:
                    System.out.println("Thank you for using the Academic Workload Planner. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addStaff() {
        System.out.println("\nAdding new staff member");
        String name = getStringInput("Enter staff name: ");
        String role = getStringInput("Enter role: ");
        String contractType = getStringInput("Enter contract type (Full-Time/Part-Time): ");

        Staff staff = new Staff(name, role, contractType);
        staffList.add(staff);
        System.out.println("Staff member added successfully.");
    }

    private void addActivity() {
        if (staffList.isEmpty()) {
            System.out.println("Please add a staff member first.");
            return;
        }

        System.out.println("\nAdding new activity");
        Staff staff = selectStaff();
        String type = getStringInput("Enter activity type (ATSR/TS/TLR/SA/Other): ");
        String description = getStringInput("Enter activity description: ");
        String trimester = getStringInput("Enter trimester (Trimester 1/Trimester 2/Trimester 3/All Year): ");
        double hoursPerInstance = getDoubleInput("Enter hours per instance: ");
        int instances = getIntInput("Enter number of instances: ");

        Activity activity = new Activity(staff, type, description, trimester, hoursPerInstance, instances);
        activities.add(activity);
        staff.addActivityHours(type, activity.getTotalHours());
        System.out.println("Activity added successfully.");
    }

    private void viewStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff members added yet.");
            return;
        }

        System.out.println("\nStaff List:");
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            System.out.printf("%d. %s\n", i + 1, staff);
            System.out.printf("   ATSR: %.2f, TS: %.2f, TLR: %.2f, SA: %.2f, Other: %.2f\n",
                    staff.getActivityHours("ATSR"),
                    staff.getActivityHours("TS"),
                    staff.getActivityHours("TLR"),
                    staff.getActivityHours("SA"),
                    staff.getActivityHours("Other"));
            System.out.printf("   Total Hours: %.2f\n", staff.getTotalHours());
        }
    }

    private void viewActivities() {
        if (activities.isEmpty()) {
            System.out.println("No activities added yet.");
            return;
        }

        System.out.println("\nActivity List:");
        for (int i = 0; i < activities.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, activities.get(i));
        }
    }

    private void exportToCSV() {
        if (activities.isEmpty()) {
            System.out.println("No activities to export.");
            return;
        }

        String filename = getStringInput("Enter the filename to export (e.g., workload.csv): ");

        try (FileWriter writer = new FileWriter(filename)) {
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
                        staff.getActivityHours("ATSR"),
                        staff.getActivityHours("TS"),
                        staff.getActivityHours("TLR"),
                        staff.getActivityHours("SA"),
                        staff.getActivityHours("Other"),
                        staff.getTotalHours()));
            }

            System.out.println("Workload summary exported successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting workload summary: " + e.getMessage());
        }
    }

    private Staff selectStaff() {
        viewStaff();
        int staffIndex = getIntInput("Select a staff member (enter the number): ") - 1;
        return staffList.get(staffIndex);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public static void main(String[] args) {
        new AcademicWorkloadPlanner().run();
    }

    private static class Staff {
        private String name;
        private String role;
        private String contractType;
        private Map<String, Double> activityHours;

        public Staff(String name, String role, String contractType) {
            this.name = name;
            this.role = role;
            this.contractType = contractType;
            this.activityHours = new HashMap<>();
            activityHours.put("ATSR", 0.0);
            activityHours.put("TS", 0.0);
            activityHours.put("TLR", 0.0);
            activityHours.put("SA", 0.0);
            activityHours.put("Other", 0.0);
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }

        public String getContractType() {
            return contractType;
        }

        public void addActivityHours(String activityType, double hours) {
            activityHours.put(activityType, activityHours.get(activityType) + hours);
        }

        public double getActivityHours(String activityType) {
            return activityHours.get(activityType);
        }

        public double getTotalHours() {
            return activityHours.values().stream().mapToDouble(Double::doubleValue).sum();
        }

        @Override
        public String toString() {
            return String.format("%s (%s, %s)", name, role, contractType);
        }
    }

    private static class Activity {
        private Staff staff;
        private String type;
        private String description;
        private String trimester;
        private double hoursPerInstance;
        private int instances;

        public Activity(Staff staff, String type, String description, String trimester, double hoursPerInstance,
                int instances) {
            this.staff = staff;
            this.type = type;
            this.description = description;
            this.trimester = trimester;
            this.hoursPerInstance = hoursPerInstance;
            this.instances = instances;
        }

        public Staff getStaff() {
            return staff;
        }

        public String getType() {
            return type;
        }

        public String getDescription() {
            return description;
        }

        public String getTrimester() {
            return trimester;
        }

        public double getHoursPerInstance() {
            return hoursPerInstance;
        }

        public int getInstances() {
            return instances;
        }

        public double getTotalHours() {
            return hoursPerInstance * instances;
        }

        @Override
        public String toString() {
            return String.format("%s - %s: %s (%.2f hours)", staff.getName(), type, description, getTotalHours());
        }
    }
}
