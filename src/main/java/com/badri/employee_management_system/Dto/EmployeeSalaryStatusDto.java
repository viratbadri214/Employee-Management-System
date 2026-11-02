package com.badri.employee_management_system.Dto;

public class EmployeeSalaryStatusDto {

    private String employeeName;
    private  String salaryStatus;

    public EmployeeSalaryStatusDto(String employeeName, String salaryStatus) {
        this.employeeName = employeeName;
        this.salaryStatus = salaryStatus;
    }

    public String getEmployeeName() {
        return employeeName;
    }



    public String getSalaryStatus() {
        return salaryStatus;
    }


}
