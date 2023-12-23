package com.myproject.employeepairfinder.dtos;

public class ProjectDurationDTO {
    private int employee1;
    private int employee2;
    private int projectID;
    private int durationDays;

    public void setEmployee1(int employee1) {
        this.employee1 = employee1;
    }

    public void setEmployee2(int employee2) {
        this.employee2 = employee2;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }



    public int getEmployee1() {
        return employee1;
    }

    public int getEmployee2() {
        return employee2;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getDurationDays() {
        return durationDays;
    }
}
