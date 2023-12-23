package com.myproject.employeepairfinder.models;

import java.time.LocalDate;

public class EmployeeProject {
    private final int empID;
    private final int projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public EmployeeProject(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }


}
