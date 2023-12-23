package com.myproject.employeepairfinder.controllers;

import com.myproject.employeepairfinder.dtos.ProjectDurationDTO;
import com.myproject.employeepairfinder.models.EmployeeProject;
import com.myproject.employeepairfinder.services.EmployeePairService;
import com.myproject.employeepairfinder.services.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/employee-projects")
public class EmployeeProjectController {
    private final EmployeePairService employeePairService;
    private final EmployeeProjectService employeeProjectService;

    @Autowired
    public EmployeeProjectController(EmployeePairService employeePairService, EmployeeProjectService employeeProjectService) {
        this.employeePairService = employeePairService;
        this.employeeProjectService = employeeProjectService;
    }

    @GetMapping("/longest-collaboration")
    public List<ProjectDurationDTO> getLongestCollaborations() {
        return employeePairService.findLongestCollaborations();
    }

    @GetMapping("/{empId}/{projectId}")
    public ResponseEntity<EmployeeProject> getEmployeeProject(@PathVariable int empId, @PathVariable int projectId) {
        EmployeeProject employeeProject = employeeProjectService.getEmployeeProjectById(empId,projectId);
        if (employeeProject != null) {
            return new ResponseEntity<>(employeeProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{empId}/{projectId}")
    public ResponseEntity<EmployeeProject> updateEmployeeProjectDateTo(
            @PathVariable int empId,
            @PathVariable int projectId,
            @RequestBody String updatedDateToStr) {

        try {
            LocalDate updatedDateTo = LocalDate.parse(updatedDateToStr);
            boolean updatedEmployeeProject = employeeProjectService.updateEmployeeProject(empId, projectId, updatedDateTo);

            if (updatedEmployeeProject) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping
    public ResponseEntity<EmployeeProject> saveEmployeeProject(@RequestBody EmployeeProject employeeProject) {
        EmployeeProject savedEmployeeProject = employeeProjectService.saveEmployeeProject(employeeProject);
        return new ResponseEntity<>(savedEmployeeProject, HttpStatus.CREATED);
    }

    @DeleteMapping("/{empId}/{projectId}")
    public ResponseEntity<Void> deleteEmployeeProject(@PathVariable int empId, @PathVariable int projectId) {
        boolean success = employeeProjectService.deleteEmployeeProject(empId, projectId);
        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
