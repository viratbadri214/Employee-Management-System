package com.badri.employee_management_system.Service;

import com.badri.employee_management_system.Dto.DepartmentCountResponseDto;
import com.badri.employee_management_system.Dto.EmployeeRequestDto;
import com.badri.employee_management_system.Dto.EmployeeResponseDto;
import com.badri.employee_management_system.Dto.EmployeeSalaryStatusDto;
import com.badri.employee_management_system.Entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeService {

    public EmployeeResponseDto saveEmployee(EmployeeRequestDto dto);
    //public List<EmployeeResponseDto> getAllEmployees();
    //pagination
  //  Page<EmployeeResponseDto> getAllEmployees (int page ,int size);
    Page<EmployeeResponseDto> getAllEmployees (int page ,int size,String sortBy ,String direction);
    public EmployeeResponseDto getById(long id);
    public EmployeeResponseDto updateEmployee(long id , EmployeeRequestDto dto);
    public void deleteEmployee(long id);
    List<EmployeeResponseDto> searchEmployeeByName(String employeeName);
    List<EmployeeResponseDto> searchEmployeesByDepartment(String employeeDepartment);
    List<EmployeeResponseDto> searchEmployeesBySalary(double minSalary ,double maxSalary);
    List<EmployeeResponseDto> searchEmployeesByAge(Integer minAge ,Integer maxAge);
    List<EmployeeResponseDto> searchEmployeeBySalaryGreaterThan(double salary);
    List<EmployeeResponseDto> findEmployeesWithSalaryLessThan (double salary);
    List<EmployeeResponseDto> findEmployeesWithGreaterThanEqual(double salary);
    List<EmployeeResponseDto> findEmployeesWithLessThanEqual(double salary);

    List<EmployeeResponseDto> findEmployeesByNameStartsWith(String prefix);
    List<EmployeeResponseDto> findEmployeesByNameEndsWith(String prefix);
    List<EmployeeResponseDto> findEmployeesByDepartments(List<String> departments);
    List<EmployeeResponseDto> findEmployeesNotInDepartment(String department);
    List<EmployeeResponseDto> findEmployeesByDepartment(String employeeDepartment);
    List<EmployeeResponseDto> findEmployeesByDepartmentAndSalary(String department , double salary);
    List<EmployeeResponseDto> findEmployeesByDepartmentOrDepartment(String department1 , String department2);


    //LIKE operations
    List<EmployeeResponseDto> findEmployeesByNameContaining(String name);
    List<EmployeeResponseDto> findEmployeesByNameStartingWith(String name);
    List<EmployeeResponseDto> findEmployeesByNameEndingWith(String name);

    // ORDERBY operations
    //salary Desc order operation
     List<EmployeeResponseDto> findEmployeesOrderBySalaryDesc();

     //salary ASC order operation
    List<EmployeeResponseDto> findEmployeesOrderBySalaryAsc();
    List<EmployeeResponseDto> findEmployeesOrderByNameAsc();
    List<EmployeeResponseDto> findEmployeesOrderByNameDesc();

    //count operation
    public Long findTotalCountOfEmployees();

    public Double findEmployeeMaximumSalary();

    public Double findEmployeeMinimumSalary();

    public Double findEmployeeAverageSalary();

    public Double findEmployeesSumOfSalary();

    public List<DepartmentCountResponseDto> getEmployeeCountByDepartment();

    public List<DepartmentCountResponseDto> findDepartmentEmployeesCountGreaterThan(Long count);

    public List<String> getDistinctDepartments();

    public List<EmployeeResponseDto> findEmployeeByDepartmentIn(List<String> departments);
    List<EmployeeResponseDto> findEmployeesByDepartmentNotIn(List<String> departments);

    List<EmployeeSalaryStatusDto> findEmployeeSalaryStatus();

    List<EmployeeResponseDto> findEmployeesBySalaryGreaterThanNative(double salary);




}
