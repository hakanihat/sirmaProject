package com.myproject.employeepairfinder.services;

import com.myproject.employeepairfinder.dtos.ProjectDurationDTO;
import com.myproject.employeepairfinder.models.EmployeeProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EmployeePairService {

    private final CsvLoaderService csvLoaderService;

    @Autowired
    public EmployeePairService(CsvLoaderService csvLoaderService) {
        this.csvLoaderService = csvLoaderService;
    }

    public List<ProjectDurationDTO> findLongestCollaborations() {
        List<EmployeeProject> employeeProjects = csvLoaderService.loadEmployeeProjectsFromCsv();

        Map<String, List<ProjectDurationDTO>> collaborationMap = new HashMap<>();

        for (int i = 0; i < employeeProjects.size(); i++) {
            for (int j = i + 1; j < employeeProjects.size(); j++) {
                EmployeeProject project1 = employeeProjects.get(i);
                EmployeeProject project2 = employeeProjects.get(j);

                if (project1.getProjectID() == project2.getProjectID()) {
                    int overlapDays = calculateOverlap(project1.getDateFrom(), project1.getDateTo(),
                            project2.getDateFrom(), project2.getDateTo());

                    if (overlapDays > 0) {
                        String key = generateCollaborationKey(project1.getEmpID(), project2.getEmpID());

                        ProjectDurationDTO collaboration = new ProjectDurationDTO();
                        collaboration.setEmployee1(project1.getEmpID());
                        collaboration.setEmployee2(project2.getEmpID());
                        collaboration.setProjectID(project1.getProjectID());
                        collaboration.setDurationDays(overlapDays);

                        collaborationMap.computeIfAbsent(key, k -> new ArrayList<>()).add(collaboration);
                    }
                }
            }
        }

        List<ProjectDurationDTO> longestCollaborations = Collections.emptyList();
        int maxTotalDuration = 0;
        for (List<ProjectDurationDTO> collaborations : collaborationMap.values()) {
            int totalDuration = collaborations.stream().mapToInt(ProjectDurationDTO::getDurationDays).sum();
            if (totalDuration > maxTotalDuration) {
                longestCollaborations = collaborations;
                maxTotalDuration = totalDuration;
            }
        }

        return longestCollaborations;
    }



    private int calculateOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        LocalDate overlapStart = start1.isAfter(start2) ? start1 : start2;
        LocalDate overlapEnd = end1 != null && end2 != null ? (end1.isBefore(end2) ? end1 : end2) : null;

        if (overlapEnd != null && overlapEnd.isBefore(overlapStart)) {
            return 0;
        }

        return overlapEnd != null ?
                (int) ChronoUnit.DAYS.between(overlapStart, overlapEnd) + 1 :
                (int) ChronoUnit.DAYS.between(overlapStart, LocalDate.now()) + 1;
    }

    private String generateCollaborationKey(int employeeId1, int employeeId2) {
        int smallerEmployeeId = Math.min(employeeId1, employeeId2);
        int largerEmployeeId = Math.max(employeeId1, employeeId2);
        return String.format("%d_%d", smallerEmployeeId, largerEmployeeId);
    }

}
