package com.badri.employee_management_system;

import com.badri.employee_management_system.Dto.EmployeeRequestDto;
import com.badri.employee_management_system.Dto.EmployeeResponseDto;

import com.badri.employee_management_system.Entity.Employee;
import com.badri.employee_management_system.Exception.EmployeeNotFoundException;
import com.badri.employee_management_system.Repository.EmployeeRepository;
import com.badri.employee_management_system.Service.Impl.EmployeeServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void getById_shouldReturnEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeePhone("9876543210");
        employee.setEmployeeEmail("badri@gmail.com");
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);
        employee.setEmployeeAddress("Kadapa");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDto result = employeeService.getById(1L);

        assertEquals(1L, result.getEmployeeId());
        assertEquals("Badri", result.getEmployeeName());
        assertEquals(25, result.getEmployeeAge());
        assertEquals("9876543210", result.getEmployeePhone());
        assertEquals("badri@gmail.com", result.getEmployeeEmail());
        assertEquals("IT", result.getEmployeeDepartment());
        assertEquals(60000, result.getEmployeeSalary());
        assertEquals("Kadapa", result.getEmployeeAddress());

        verify(employeeRepository).findById(1L);
    }

    @Test
    void getById_shouldThrowException_whenEmployeeNotFound() {
        when(employeeRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(100L));
        verify(employeeRepository).findById(100L);
    }

    @Test
    void createEmployee_shouldCreateEmployeeSuccessfully() {

        EmployeeRequestDto requestDto =
                new EmployeeRequestDto();

        requestDto.setEmployeeName("Badri");
        requestDto.setEmployeeAge(25);
        requestDto.setEmployeePhone("9876543210");
        requestDto.setEmployeeEmail("badri@gmail.com");
        requestDto.setEmployeeDepartment("IT");
        requestDto.setEmployeeSalary(60000.00);
        requestDto.setEmployeeAddress("Kadapa");

        Employee employee = new Employee();
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeePhone("9876543210");
        employee.setEmployeeEmail("badri@gmail.com");
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);
        employee.setEmployeeAddress("Kadapa");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeResponseDto result = employeeService.saveEmployee(requestDto);


        assertEquals("Badri", result.getEmployeeName());
        assertEquals(25, result.getEmployeeAge());
        assertEquals("IT", result.getEmployeeDepartment());
        assertEquals(60000.00, result.getEmployeeSalary());


        verify(employeeRepository)
                .save(any(Employee.class));
    }

    @Test
    void updateEmployee_shouldUpdateEmployeeSuccessfully() {

        EmployeeRequestDto requestDto = new EmployeeRequestDto();
        requestDto.setEmployeeId(1L);
        requestDto.setEmployeeName("ViratBadri");
        requestDto.setEmployeeAge(26);
        requestDto.setEmployeePhone("9999999999");
        requestDto.setEmployeeEmail("updated@gmail.com");
        requestDto.setEmployeeDepartment("Development");
        requestDto.setEmployeeSalary(70000.00);
        requestDto.setEmployeeAddress("Hyderabad");

        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeePhone("9876543210");
        employee.setEmployeeEmail("badri@gmail.com");
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);
        employee.setEmployeeAddress("Kadapa");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeResponseDto result = employeeService.updateEmployee(1L, requestDto);

        assertEquals(1L, result.getEmployeeId());
        assertEquals("ViratBadri", result.getEmployeeName());
        assertEquals(26, result.getEmployeeAge());
        assertEquals("9999999999", result.getEmployeePhone());
        assertEquals("updated@gmail.com", result.getEmployeeEmail());
        assertEquals("Development", result.getEmployeeDepartment());
        assertEquals(70000.00, result.getEmployeeSalary());
        assertEquals("Hyderabad", result.getEmployeeAddress());

        verify(employeeRepository).findById(1L);

        verify(employeeRepository).save(any(Employee.class));

    }

    @Test
    void deleteEmployee_shouldDeleteEmployeeSuccessfully() {

        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeePhone("9876543210");
        employee.setEmployeeEmail("badri@gmail.com");
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);
        employee.setEmployeeAddress("Kadapa");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1L);

        verify(employeeRepository).findById(1L);

        verify(employeeRepository).delete(employee);
    }

    @Test
    void deleteEmployee_shouldThrowExceptionWhenEmployeeNotFound() {


        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());


        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(999L));

        verify(employeeRepository).findById(999L);


        verify(employeeRepository, never()).delete(any(Employee.class));

    }

    @Test
    void getById_shouldReturnEmployeeSuccessfully() {

        Employee employee = new Employee();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeePhone("9876543210");
        employee.setEmployeeEmail("badri@gmail.com");
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);
        employee.setEmployeeAddress("Kadapa");

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        EmployeeResponseDto result = employeeService.getById(1L);

        assertEquals(1L, result.getEmployeeId());
        assertEquals("Badri", result.getEmployeeName());
        assertEquals(25, result.getEmployeeAge());
        assertEquals("IT", result.getEmployeeDepartment());
        assertEquals(60000.00, result.getEmployeeSalary());

        verify(employeeRepository).findById(1L);

    }

    @Test
    void getById_shouldThrowEmployeeNotFoundExceptionWhenEmployeeNotFound() {

        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getById(999L));
        verify(employeeRepository).findById(999L);
    }

    @Test
    void getAllEmployees_shouldReturnAllEmployeesSuccessfully() {

        int page = 0;
        int size = 10;
        String sortBy = "employeeSalary";
        String direction = "asc";

        Employee employee1 = new Employee();

        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);


        Employee employee2 = new Employee();

        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(50000.00);


        List<Employee> employees =
                Arrays.asList(employee1, employee2);


        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).ascending()
                );

        Page<Employee> employeePage =
                new PageImpl<>(
                        employees,
                        pageable,
                        employees.size()
                );

//        Page<EmployeeResponseDto> result =
//                employeeService.getAllEmployees(
//                        page,
//                        size,
//                        sortBy,
//                        direction
//                );


        when(employeeRepository.findAll(pageable))
                .thenReturn(employeePage);

        Page<EmployeeResponseDto> result =
                employeeService.getAllEmployees(
                        page,
                        size,
                        sortBy,
                        direction
                );

        assertEquals(2, result.getContent().size());

        assertEquals("Badri", result.getContent().get(0).getEmployeeName());

        assertEquals("Virat", result.getContent().get(1).getEmployeeName());
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(employeeRepository)
                .findAll(pageable);

    }

    @Test
    void getAllEmployees_ShouldReturnAllEmployeesInDesc() {
        int page = 0;
        int size = 10;
        String sortby = "employeeSalary";
        String direction = "dsc";

        Employee employee1 = new Employee();

        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Virat");
        employee1.setEmployeeAge(26);
        employee1.setEmployeeDepartment("HR");
        employee1.setEmployeeSalary(60000.00);


        Employee employee2 = new Employee();

        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Badri");
        employee2.setEmployeeAge(25);
        employee2.setEmployeeDepartment("IT");
        employee2.setEmployeeSalary(50000.00);

        List<Employee> employees = Arrays.asList(employee1, employee2);

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortby).descending());

        Page<Employee> employeePage = new PageImpl<>(employees, pageable, employees.size());

        when(employeeRepository.findAll(pageable)).thenReturn(employeePage);

        Page<EmployeeResponseDto> result = employeeService.getAllEmployees(page, size, sortby, direction);

        assertEquals(2, result.getContent().size());

        assertEquals("Virat", result.getContent().get(0).getEmployeeName()
        );

        assertEquals("Badri", result.getContent().get(1).getEmployeeName()
        );

        assertEquals(60000.00, result.getContent().get(0).getEmployeeSalary());

        assertEquals(50000.00, result.getContent().get(1).getEmployeeSalary());

        verify(employeeRepository).findAll(pageable);
    }

    @Test
    void SearchEmployeeNameIgnoreCase_shouldReturnMatchingEmployee() {

        String employeeName = "Badri";

        Employee employee1 = new Employee();

        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(26);
        employee1.setEmployeeDepartment("HR");
        employee1.setEmployeeSalary(60000.00);


        Employee employee2 = new Employee();

        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("BadriNath");
        employee2.setEmployeeAge(25);
        employee2.setEmployeeDepartment("IT");
        employee2.setEmployeeSalary(50000.00);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findByEmployeeNameContainingIgnoreCase(employeeName)).thenReturn(employees);
        List<EmployeeResponseDto> result = employeeService.searchEmployeeByName(employeeName);

        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals("BadriNath", result.get(1).getEmployeeName());
        assertEquals("HR", result.get(0).getEmployeeDepartment());
    }

    @Test
    void searhEmployeeByDepartment_shouldReturnMatchingDepartment() {

        String employeeDepartment = "HR";

        Employee employee1 = new Employee();

        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(26);
        employee1.setEmployeeDepartment("HR");
        employee1.setEmployeeSalary(60000.00);


        Employee employee2 = new Employee();

        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("BadriNath");
        employee2.setEmployeeAge(25);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(50000.00);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeRepository.findByEmployeeDepartment(employeeDepartment)).thenReturn(employees);
        List<EmployeeResponseDto> result = employeeService.searchEmployeesByDepartment(employeeDepartment);

        assertEquals(2, result.size());
        assertEquals("HR", result.get(0).getEmployeeDepartment());
        assertEquals("HR", result.get(1).getEmployeeDepartment());

        verify(employeeRepository).findByEmployeeDepartment(employeeDepartment);
    }

    @Test
    void findByEmployeeBySalary_shouldReturnEmployeesSalaryWithinTheRange() {

        double minSalary = 50000;
        double maxSalary = 70000;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeSalaryBetween(minSalary, maxSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.searchEmployeesBySalary(minSalary, maxSalary);

        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(60000, result.get(0).getEmployeeSalary());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(65000, result.get(1).getEmployeeSalary());

        verify(employeeRepository).findByEmployeeSalaryBetween(minSalary, maxSalary);


    }

    @Test
    void findByEmployeeAgeBetween_shouldReturnEmployeesWithinTheRange() {
        Integer minAge = 20;
        Integer maxAge = 35;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(24);
        employee1.setEmployeeDepartment("IT");

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(28);
        employee2.setEmployeeDepartment("HR");

        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeAgeBetween(minAge, maxAge)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.searchEmployeesByAge(minAge, maxAge);
        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(24, result.get(0).getEmployeeAge());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(28, result.get(1).getEmployeeAge());

        verify(employeeRepository).findByEmployeeAgeBetween(minAge, maxAge);
    }

    @Test
    void findByEmployeesSalaryGreaterThan_shouldReturnEmployeesSalaryGreaterThanGivenSalary() {

        double employeeSalary = 50000;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeSalaryGreaterThan(employeeSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.searchEmployeeBySalaryGreaterThan(employeeSalary);

        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(60000, result.get(0).getEmployeeSalary());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(65000, result.get(1).getEmployeeSalary());

        verify(employeeRepository).findByEmployeeSalaryGreaterThan(employeeSalary);
    }

    @Test
    void findByEmployeeSalaryLessthan_shouldReturnEmployeesLessThanThegivenSalary() {
        double employeeSalary = 50000;
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(35000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(45000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeSalaryLessThan(employeeSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesWithSalaryLessThan(employeeSalary);

        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(35000, result.get(0).getEmployeeSalary());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(45000, result.get(1).getEmployeeSalary());

        verify(employeeRepository).findByEmployeeSalaryLessThan(employeeSalary);
    }

    @Test
    void findEmployeesWithGreaterThanEqual_shouldReturnEmployeesWithinGivenBoundary() {

        double employeeSalary = 60000;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeSalaryGreaterThanEqual(employeeSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesWithGreaterThanEqual(employeeSalary);

        assertEquals(2, result.size());
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(60000, result.get(0).getEmployeeSalary());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(65000, result.get(1).getEmployeeSalary());

        verify(employeeRepository).findByEmployeeSalaryGreaterThanEqual(employeeSalary);
    }

    @Test
    void findEmployeesWithLessThanEqual_shouldReturnEmployeesWithinLimitedSalary() {

        double employeeSalary = 60000;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(45000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeSalaryLessThanEqual(employeeSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesWithLessThanEqual(employeeSalary);

        assertEquals(2, result.size()); //findEmployeesWithLessThanEqual
        assertEquals("Badri", result.get(0).getEmployeeName());
        assertEquals(60000, result.get(0).getEmployeeSalary());
        assertEquals("Virat", result.get(1).getEmployeeName());
        assertEquals(45000, result.get(1).getEmployeeSalary());

        verify(employeeRepository).findByEmployeeSalaryLessThanEqual(employeeSalary);
    }

    @Test
    void findByEmployeeDepartmentIn_shouldReturnRelatedDepartmentEmployees() {

        List<String> departments = new ArrayList<>();
        departments.add("IT");
        departments.add("HR");

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(45000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findEmployeesByDepartmentIn(departments)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeeByDepartmentIn(departments);

        assertEquals(2, result.size());
        assertEquals("IT", result.get(0).getEmployeeDepartment());
        assertEquals("HR", result.get(1).getEmployeeDepartment());

        verify(employeeRepository).findEmployeesByDepartmentIn(departments);


    }

    @Test
    void findByEmployeeDepartmentNot(){
        String employeeDepartment = "IT";
        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("Developer");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(45000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findByEmployeeDepartmentNot(employeeDepartment)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesNotInDepartment(employeeDepartment);

        assertEquals(2,result.size());
        assertEquals("Developer",result.get(0).getEmployeeDepartment());
        assertEquals("HR",result.get(1).getEmployeeDepartment());

        verify(employeeRepository).findByEmployeeDepartmentNot(employeeDepartment);
    }

    @Test
    void findEmployeesByDepartmentAndSalary(){

        String employeeDepartment = "IT";
        double employeeSalary =60000;

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findEmployeesByDepartmentAndSalary(employeeDepartment,employeeSalary)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesByDepartmentAndSalary(employeeDepartment,employeeSalary);

        assertEquals(2,result.size());
        assertEquals("Badri",result.get(0).getEmployeeName());
        assertEquals("IT",result.get(0).getEmployeeDepartment());
        assertEquals(60000,result.get(0).getEmployeeSalary());
        assertEquals("Virat",result.get(1).getEmployeeName());
        assertEquals("HR",result.get(1).getEmployeeDepartment());
        assertEquals(65000,result.get(1).getEmployeeSalary());

        verify(employeeRepository).findEmployeesByDepartmentAndSalary(employeeDepartment,employeeSalary);

    }

    @Test
    void findEmployeesByDepartmentOrDepartment_shouldReturnEmployeesFromEitherDepartment(){

        String employeeDepartment1 = "IT";
        String employeeDepartment2 ="HR";

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);

        when(employeeRepository.findEmployeesByDepartmentOrDepartment(employeeDepartment1,employeeDepartment2)).thenReturn(employees);

        List<EmployeeResponseDto> result = employeeService.findEmployeesByDepartmentOrDepartment(employeeDepartment1,employeeDepartment2);

        assertEquals(2,result.size());
        assertEquals("Badri",result.get(0).getEmployeeName());
        assertEquals("IT",result.get(0).getEmployeeDepartment());
        assertEquals("Virat",result.get(1).getEmployeeName());
        assertEquals("HR",result.get(1).getEmployeeDepartment());

        verify(employeeRepository).findEmployeesByDepartmentOrDepartment(employeeDepartment1,employeeDepartment2);


    }

    @Test
    void findByEmployeeNameStartsWith_shouldReturnRelatedEmployees(){
        String prefix = "Vir";

        Employee employee1 = new Employee();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("ViratKohli");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        Employee employee2 = new Employee();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("ViratBadri");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);

        List<Employee> employees =
                Arrays.asList(employee1, employee2);
        when(employeeRepository.findByEmployeeNameStartsWith(prefix)).thenReturn(employees);


        List<EmployeeResponseDto> result = employeeService.findEmployeesByNameStartsWith(prefix);

        assertEquals(2,result.size());
        assertEquals("ViratKohli",result.get(0).getEmployeeName());
        assertEquals("IT",result.get(0).getEmployeeDepartment());
        assertEquals("ViratBadri",result.get(1).getEmployeeName());
        assertEquals("HR",result.get(1).getEmployeeDepartment());

        verify(employeeRepository).findByEmployeeNameStartsWith(prefix);

    }

    @Test
    void findTotalCountOfEmployees_shouldReturnTotalEmployeeCount() {

        Long expectedCount = 10L;

        when(employeeRepository.findTotalCountOfEmployees()).thenReturn(expectedCount);

        Long result = employeeService.findTotalCountOfEmployees();
        assertEquals(10L, result);

        verify(employeeRepository).findTotalCountOfEmployees();
    }


    @Test
    void findEmployeeMaximumSalary_shouldReturnMaximumSalary() {

        Double expectedMaximumSalary = 90000.00;

        when(employeeRepository.findMaximumSalary()).thenReturn(expectedMaximumSalary);

        Double result = employeeService.findEmployeeMaximumSalary();

        assertEquals(90000.00, result);

        verify(employeeRepository).findMaximumSalary();
    }

    @Test
    void findEmployeeMinimumSalary_shouldReturnMinimumSalary() {


        Double expectedMinimumSalary = 30000.00;

        when(employeeRepository.findMinimumSalary()).thenReturn(expectedMinimumSalary);

        Double result = employeeService.findEmployeeMinimumSalary();

        assertEquals(30000.00, result);

        verify(employeeRepository).findMinimumSalary();
    }

    @Test
    void findEmployeeAverageSalary_shouldReturnAverageSalary() {

        Double expectedAverageSalary = 60000.00;

        when(employeeRepository.findAverageSalary()).thenReturn(expectedAverageSalary);

        Double result = employeeService.findEmployeeAverageSalary();

        assertEquals(60000.00, result);

        verify(employeeRepository).findAverageSalary();
    }

    @Test
    void findEmployeesSumOfSalary_shouldReturnTotalSalary() {

        Double expectedTotalSalary = 300000.00;

        when(employeeRepository.findSumOfSalary()).thenReturn(expectedTotalSalary);

        Double result = employeeService.findEmployeesSumOfSalary();

        assertEquals(300000.00, result);

        verify(employeeRepository).findSumOfSalary();
    }

}
