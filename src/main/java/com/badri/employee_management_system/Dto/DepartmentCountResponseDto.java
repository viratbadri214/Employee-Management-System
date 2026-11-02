package com.badri.employee_management_system.Dto;

public class DepartmentCountResponseDto {

    private String department;
    private  Long employeeCount;

    public DepartmentCountResponseDto(String department, Long employeeCount) {
        this.department = department;
        this.employeeCount = employeeCount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }
}
