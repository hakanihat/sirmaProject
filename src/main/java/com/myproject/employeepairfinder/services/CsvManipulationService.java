package com.myproject.employeepairfinder.services;

import com.myproject.employeepairfinder.models.EmployeeProject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CsvManipulationService {

    private static final String CSV_FILE_PATH = "src/main/resources/data.csv";
    private CsvLoaderService csvLoaderService;

    public CsvManipulationService(CsvLoaderService csvLoaderService) {
        this.csvLoaderService = csvLoaderService;
    }

    public void saveEmployeeProjectsToCsv(List<EmployeeProject> employeeProjects) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(CSV_FILE_PATH, true)))) {
            for (EmployeeProject employeeProject : employeeProjects) {
                String csvLine = String.format("%d,%d,%s,%s",
                        employeeProject.getEmpID(),
                        employeeProject.getProjectID(),
                        employeeProject.getDateFrom(),
                        employeeProject.getDateTo());
                writer.println(csvLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeProjectInCsv(EmployeeProject updatedEmployeeProject) {
        List<EmployeeProject> projects = csvLoaderService.loadEmployeeProjectsFromCsv();

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (EmployeeProject employeeProject : projects) {
                if (employeeProject.getEmpID() == updatedEmployeeProject.getEmpID()
                        && employeeProject.getProjectID() == updatedEmployeeProject.getProjectID()) {
                    writer.println(formatEmployeeProject(updatedEmployeeProject));
                } else {
                    writer.println(formatEmployeeProject(employeeProject));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteEmployeeProjectFromCsv(EmployeeProject employeeProject) {
        List<EmployeeProject> projects = csvLoaderService.loadEmployeeProjectsFromCsv();

        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
            for (EmployeeProject project : projects) {
                if (project.getEmpID() == employeeProject.getEmpID()
                        && project.getProjectID() == employeeProject.getProjectID()) {
                    continue;
                }
                writer.println(formatEmployeeProject(project));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatEmployeeProject(EmployeeProject employeeProject) {
        return String.format("%d,%d,%s,%s",
                employeeProject.getEmpID(),
                employeeProject.getProjectID(),
                employeeProject.getDateFrom(),
                employeeProject.getDateTo());
    }
}
