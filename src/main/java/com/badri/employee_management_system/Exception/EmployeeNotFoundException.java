package com.badri.employee_management_system.Exception;

public class EmployeeNotFoundException extends RuntimeException{

    public EmployeeNotFoundException (String message){
        super(message);
    }
}
