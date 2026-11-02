package com.badri.employee_management_system.Dto;

import jakarta.validation.constraints.*;

public class EmployeeRequestDto {
    private long employeeId;

    @NotBlank(message = "Emplooyee Name is required")
    private String employeeName;
    @NotNull(message = "Employee age is required")
    @Min(value = 18,message = "Employee age must be greaterthan 18")
    private Integer employeeAge;
    @NotBlank(message = "Employee phone is required")
    @Pattern(
            regexp = "^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String employeePhone;
    @NotBlank(message = "Employee email required")
    @Email(message = "Invalid Email Format")
    private String employeeEmail;
    @NotBlank(message = "Employee department is required")
    private String employeeDepartment;
    @NotNull
    @Positive(message = "Salary must be greater than zero")
    private Double employeeSalary;
    @NotBlank(message = "Employee address is required")
    private String employeeAddress;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Integer getEmployeeAge() {
        return employeeAge;
    }

    public void setEmployeeAge(Integer employeeAge) {
        this.employeeAge = employeeAge;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeDepartment() {
        return employeeDepartment;
    }

    public void setEmployeeDepartment(String employeeDepartment) {
        this.employeeDepartment = employeeDepartment;
    }

    public Double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(Double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }
}
