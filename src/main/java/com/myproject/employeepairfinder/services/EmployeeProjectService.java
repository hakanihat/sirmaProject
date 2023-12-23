package com.myproject.employeepairfinder.services;

import com.myproject.employeepairfinder.models.EmployeeProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeProjectService {

    private final CsvLoaderService csvLoaderService;
    private final CsvManipulationService csvManipulationService;

    private final List<EmployeeProject> employeesProjects;

    @Autowired
    public EmployeeProjectService(CsvLoaderService csvLoaderService, CsvManipulationService csvManipulationService) {
        this.csvLoaderService = csvLoaderService;
        this.csvManipulationService = csvManipulationService;
        this.employeesProjects = csvLoaderService.loadEmployeeProjectsFromCsv();
    }

    public EmployeeProject getEmployeeProjectById(int empId, int projectId) {
        return employeesProjects.stream()
                .filter(employeeProject ->
                        employeeProject.getEmpID() == empId && employeeProject.getProjectID() == projectId)
                .findFirst()
                .orElse(null);
    }


    public EmployeeProject saveEmployeeProject(EmployeeProject employeeProject) {
        employeesProjects.add(employeeProject);
        csvManipulationService.saveEmployeeProjectsToCsv(List.of(employeeProject));
        return employeeProject;
    }

    public boolean updateEmployeeProject(int empId, int projectId, LocalDate updatedDateTo) {
        Optional<EmployeeProject> optionalEmployeeProject = employeesProjects.stream()
                .filter(e -> e.getEmpID() == empId && e.getProjectID() == projectId)
                .findFirst();

        if (optionalEmployeeProject.isPresent()) {
            EmployeeProject existingEmployeeProject = optionalEmployeeProject.get();
            existingEmployeeProject.setDateTo(updatedDateTo);
            csvManipulationService.updateEmployeeProjectInCsv(existingEmployeeProject);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteEmployeeProject(int empId, int projectId) {
        EmployeeProject employeeProjectToDelete = employeesProjects.stream()
                .filter(employeeProject ->
                        employeeProject.getEmpID() == empId && employeeProject.getProjectID() == projectId)
                .findFirst()
                .orElse(null);

        if (employeeProjectToDelete != null) {
            employeesProjects.remove(employeeProjectToDelete);
            csvManipulationService.deleteEmployeeProjectFromCsv(employeeProjectToDelete);
            return true;
        } else {
            return false;
        }
    }

}
