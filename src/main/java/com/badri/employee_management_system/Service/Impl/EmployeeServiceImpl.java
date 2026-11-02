package com.badri.employee_management_system.Service.Impl;

import com.badri.employee_management_system.Dto.DepartmentCountResponseDto;
import com.badri.employee_management_system.Dto.EmployeeRequestDto;
import com.badri.employee_management_system.Dto.EmployeeResponseDto;
import com.badri.employee_management_system.Dto.EmployeeSalaryStatusDto;
import com.badri.employee_management_system.Entity.Employee;
import com.badri.employee_management_system.Exception.EmployeeNotFoundException;
import com.badri.employee_management_system.Repository.EmployeeRepository;
import com.badri.employee_management_system.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private EmployeeResponseDto mapToResponseDto(Employee employee){
        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmployeeAge(employee.getEmployeeAge());
        dto.setEmployeePhone(employee.getEmployeePhone());
        dto.setEmployeeEmail(employee.getEmployeeEmail());
        dto.setEmployeeDepartment(employee.getEmployeeDepartment());
        dto.setEmployeeSalary(employee.getEmployeeSalary());
        dto.setEmployeeAddress(employee.getEmployeeAddress());
        return dto;
    }

    @Override
    public EmployeeResponseDto saveEmployee(EmployeeRequestDto dto) {

        Employee employee= new Employee();

        employee.setEmployeeName(dto.getEmployeeName());
        employee.setEmployeeAge(dto.getEmployeeAge());
        employee.setEmployeePhone(dto.getEmployeePhone());
        employee.setEmployeeEmail(dto.getEmployeeEmail());
        employee.setEmployeeDepartment(dto.getEmployeeDepartment());
        employee.setEmployeeSalary(dto.getEmployeeSalary());
        employee.setEmployeeAddress(dto.getEmployeeAddress());
        Employee savedEmployee =employeeRepository.save(employee);

        return mapToResponseDto(savedEmployee);
    }

    @Override
    public Page<EmployeeResponseDto> getAllEmployees(int page, int size, String sortBy, String direction) {
        Sort sort;
        if(direction.equalsIgnoreCase("asc")){
            sort= Sort.by(sortBy).ascending();
        }
        else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Employee> employees=employeeRepository.findAll(pageable);
        return employees.map(this::mapToResponseDto);
    }


    //pagination a
//    @Override
//    public Page<EmployeeResponseDto> getAllEmployees(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page,size);
//       Page<Employee> employees= employeeRepository.findAll(pageable);
//
//        return employees.map(this::mapToResponseDto);
//    }


//    @Override
//    public List<EmployeeResponseDto> getAllEmployees() {
//        return employeeRepository.findAll().stream().map(this::mapToResponseDto).collect(Collectors.toList());
//    }


    //pagination
//    @Override
//    public Page<EmployeeResponseDto> getAllEmployees(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page,size);
//       Page<Employee> employees= employeeRepository.findAll(pageable);
//
//        return employees.map(this::mapToResponseDto);
//    }

    @Override
    public EmployeeResponseDto getById(long id) {
       Employee employee= employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("employee not found with id  "+id));
        return mapToResponseDto(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployee(long id, EmployeeRequestDto dto) {

       Employee employee= employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("employee not found with id  "+id));

       employee.setEmployeeName(dto.getEmployeeName());
       employee.setEmployeeAge(dto.getEmployeeAge());
       employee.setEmployeePhone(dto.getEmployeePhone());
       employee.setEmployeeEmail(dto.getEmployeeEmail());
       employee.setEmployeeDepartment(dto.getEmployeeDepartment());
       employee.setEmployeeSalary(dto.getEmployeeSalary());
       employee.setEmployeeAddress(dto.getEmployeeAddress());
       Employee updatedEmployee=employeeRepository.save(employee);

        return mapToResponseDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        Employee employee= employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("employee not found with id  "+id));
        employeeRepository.delete(employee);

    }

    @Override
    public List<EmployeeResponseDto> searchEmployeeByName(String employeeName) {

        List<Employee> employees=employeeRepository.findByEmployeeNameContainingIgnoreCase(employeeName);

        System.out.println(employees);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> searchEmployeesByDepartment(String employeeDepartment) {
        List<Employee> employees=employeeRepository.findByEmployeeDepartment(employeeDepartment);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> searchEmployeesBySalary(double minSalary, double maxSalary) {
        System.out.println("min salary  "+minSalary);
        System.out.println("max salary   "+maxSalary);
       List<Employee> employees =employeeRepository.findByEmployeeSalaryBetween(minSalary, maxSalary);
       return  employees.stream().map(this::mapToResponseDto).toList();

    }

    @Override
    public List<EmployeeResponseDto> searchEmployeesByAge(Integer minAge, Integer maxAge) {
       List<Employee> employees = employeeRepository.findByEmployeeAgeBetween(minAge, maxAge);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> searchEmployeeBySalaryGreaterThan(double salary) {
       List<Employee> employees =employeeRepository.findByEmployeeSalaryGreaterThan(salary);
       return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesWithSalaryLessThan(double salary) {
       List <Employee> employees = employeeRepository.findByEmployeeSalaryLessThan(salary);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesWithGreaterThanEqual(double salary) {
       List<Employee> employees = employeeRepository.findByEmployeeSalaryGreaterThanEqual(salary);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesWithLessThanEqual(double salary) {
        List<Employee> employees = employeeRepository.findByEmployeeSalaryLessThanEqual(salary);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByDepartments(List<String> departments) {

        List<Employee> employees =
                employeeRepository.findByEmployeeDepartmentIn(departments);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesNotInDepartment(String department) {

        List<Employee> employees =
                employeeRepository.findByEmployeeDepartmentNot(department);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByDepartment(String employeeDepartment) {
       List<Employee> employees = employeeRepository.findEmployeesByDepartment(employeeDepartment);
        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByDepartmentAndSalary(String department , double salary) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentAndSalary(department , salary);
        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByDepartmentOrDepartment(String department1, String department2) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentOrDepartment(department1,department2);
        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }


    @Override
    public List<EmployeeResponseDto> findEmployeesByNameStartsWith(String prefix) {

       List<Employee> employees = employeeRepository.findByEmployeeNameStartsWith(prefix);

        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByNameEndsWith(String prefix) {
        List<Employee> employees = employeeRepository.findByEmployeeNameEndsWith(prefix);

        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByNameContaining(String name) {

        List<Employee> employees =
                employeeRepository.findEmployeesByNameContaining(name);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<EmployeeResponseDto> findEmployeesByNameStartingWith(String name) {

        List<Employee> employees =
                employeeRepository.findEmployeesByNameStartingWith(name);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }
    @Override
    public List<EmployeeResponseDto> findEmployeesByNameEndingWith(String name) {

        List<Employee> employees =
                employeeRepository.findEmployeesByNameEndingWith(name);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesOrderBySalaryDesc() {
      List<Employee> employees = employeeRepository.findEmployeesOrderBySalaryDesc();
      return  employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesOrderBySalaryAsc() {
       List<Employee> employees = employeeRepository.findEmployeesOrderBySalaryAsc();
       return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesOrderByNameAsc() {
       List<Employee> employees =employeeRepository.findEmployeesOrderByNameAsc();
       return  employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesOrderByNameDesc() {
        List<Employee> employees =employeeRepository.findEmployeesOrderByNameAsc();
        return  employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public Long findTotalCountOfEmployees() {
      return employeeRepository.findTotalCountOfEmployees();

    }

    @Override
    public Double findEmployeeMaximumSalary() {
        return employeeRepository.findMaximumSalary();
    }

    @Override
    public Double findEmployeeMinimumSalary() {
        return employeeRepository.findMinimumSalary();
    }

    @Override
    public Double findEmployeeAverageSalary() {
        return employeeRepository.findAverageSalary();
    }

    @Override
    public Double findEmployeesSumOfSalary() {
        return employeeRepository.findSumOfSalary();
    }

    @Override
    public List<DepartmentCountResponseDto> getEmployeeCountByDepartment() {
        return employeeRepository.getEmployeeCountByDepartment();
    }

    @Override
    public List<DepartmentCountResponseDto> findDepartmentEmployeesCountGreaterThan(Long count) {
        return employeeRepository.findDepartmentHavingEmployeesCountGreaterThan(count);
    }

    @Override
    public List<String> getDistinctDepartments() {
        return employeeRepository.getDistinctDepartments();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeeByDepartmentIn(List<String> departments) {
        List<Employee> employees = employeeRepository.findEmployeesByDepartmentIn(departments);
        return employees.stream().map(this::mapToResponseDto).toList();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesByDepartmentNotIn(List<String> departments) {

        List<Employee> employees =
                employeeRepository.findEmployeesByDepartmentNotIn(departments);

        return employees.stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public List<EmployeeSalaryStatusDto> findEmployeeSalaryStatus() {
        return employeeRepository.findEmployeeSalaryStatus();
    }

    @Override
    public List<EmployeeResponseDto> findEmployeesBySalaryGreaterThanNative(double salary) {
        List<Employee> employees = employeeRepository.findEmployeesBySalaryGreaterThanNative(salary);
        return employees.stream().map(this::mapToResponseDto).toList();
    }



}
