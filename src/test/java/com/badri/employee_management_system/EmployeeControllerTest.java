package com.badri.employee_management_system;


import com.badri.employee_management_system.Controller.EmployeeController;
import com.badri.employee_management_system.Dto.EmployeeRequestDto;
import com.badri.employee_management_system.Dto.EmployeeResponseDto;

import com.badri.employee_management_system.Exception.EmployeeNotFoundException;
import com.badri.employee_management_system.Security.JwtAuthenticationFilter;
import com.badri.employee_management_system.Security.JwtService;
import com.badri.employee_management_system.Service.AuthService;
import com.badri.employee_management_system.Service.EmployeeService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @MockBean
    private EmployeeService employeeService ;

    @MockBean
    private AuthService authservice;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter ;

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_shouldReturnCreatedResponse() throws Exception {

        EmployeeResponseDto responseDto = new EmployeeResponseDto();

        responseDto.setEmployeeId(1L);
        responseDto.setEmployeeName("Badri");
        responseDto.setEmployeeAge(25);
        responseDto.setEmployeeDepartment("IT");
        responseDto.setEmployeeSalary(60000.00);

        when(employeeService.saveEmployee(any(EmployeeRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(
               post("/employees").contentType(MediaType.APPLICATION_JSON)
                       .content("""
                                      {  "employeeName": "Badri",
                                         "employeeAge": 25,
                                         "employeeDepartment": "IT",
                                         "employeeSalary": 60000.00,
                                         "employeePhone": "9876543210",
                                         "employeeEmail": "badri@gmail.com",
                                         "employeeAddress": "Proddatur"}"""
                       )).andExpect(status().isCreated())

                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Employee saved succesfully"))
                .andExpect(jsonPath("$.data.employeeName")
                        .value("Badri"))
                .andExpect(jsonPath("$.data.employeeDepartment")
                        .value("IT"))
                .andExpect(jsonPath("$.data.employeeSalary")
                        .value(60000.00));


        verify(employeeService)
                .saveEmployee(any(EmployeeRequestDto.class));



    }

    @Test
    @WithMockUser(roles = "USER")
    void getById_shouldReturnEmployee()throws Exception {

        EmployeeResponseDto responseDto = new EmployeeResponseDto();

        responseDto.setEmployeeId(1L);
        responseDto.setEmployeeName("Badri");
        responseDto.setEmployeeAge(25);
        responseDto.setEmployeeDepartment("IT");
        responseDto.setEmployeeSalary(60000.00);

        when(employeeService.getById(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/employees/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Employee Retrieved successfully"))
                .andExpect(jsonPath("$.data.employeeId").value(1))
                .andExpect(jsonPath("$.data.employeeName").value("Badri"))
                .andExpect(jsonPath("$.data.employeeAge").value(25))
                .andExpect(jsonPath("$.data.employeeDepartment").value("IT"))
                .andExpect(jsonPath("$.data.employeeSalary").value(60000));
        verify(employeeService).getById(1L);

    }

    @Test
    @WithMockUser
    void getAllEmployees_shouldReturnEmployees() throws Exception {

        EmployeeResponseDto employee1 = new EmployeeResponseDto();
        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);

        EmployeeResponseDto employee2 = new EmployeeResponseDto();
        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Virat");
        employee2.setEmployeeAge(28);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(50000.00);

        List<EmployeeResponseDto> employeesList = Arrays.asList(employee1,employee2);
        Page<EmployeeResponseDto> employeePage = new PageImpl<>(employeesList);

        when(employeeService.getAllEmployees(0,5,"employeeId","asc")).thenReturn(employeePage);

        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Employees retrieved successfully"))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content.length()").value(2))
                .andExpect(jsonPath("$.data.content[0].employeeId").value(1))
                .andExpect(jsonPath("$.data.content[0].employeeName").value("Badri"))
                .andExpect(jsonPath("$.data.content[0].employeeDepartment").value("IT"))
                .andExpect(jsonPath("$.data.content[1].employeeId").value(2))
                .andExpect(jsonPath("$.data.content[1].employeeName").value("Virat"))
                .andExpect(jsonPath("$.data.content[1].employeeDepartment").value("HR"));
                verify(employeeService).getAllEmployees(0,5,"employeeId","asc");


    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateEmployee_shouldReturnUpdatedEmployee() throws Exception {

        // Arrange
        EmployeeResponseDto responseDto = new EmployeeResponseDto();

        responseDto.setEmployeeId(1L);
        responseDto.setEmployeeName("Badri Updated");
        responseDto.setEmployeeAge(26);
        responseDto.setEmployeeDepartment("IT");
        responseDto.setEmployeeSalary(70000.00);

        when(employeeService.updateEmployee(
                anyLong(),
                any(EmployeeRequestDto.class)
        )).thenReturn(responseDto);


        // Act & Assert
        mockMvc.perform(
                        put("/employees/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                    {
                                        "employeeName": "Badri Updated",
                                        "employeeAge": 26,
                                        "employeePhone": "9876543210",
                                        "employeeEmail": "badri@gmail.com",
                                        "employeeDepartment": "IT",
                                        "employeeSalary": 70000.00,
                                        "employeeAddress": "Kadapa"
                                    }
                                    """)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.employeeId").value(1))
                .andExpect(jsonPath("$.data.employeeName")
                        .value("Badri Updated"))
                .andExpect(jsonPath("$.data.employeeAge").value(26))
                .andExpect(jsonPath("$.data.employeeDepartment")
                        .value("IT"))
                .andExpect(jsonPath("$.data.employeeSalary")
                        .value(70000.00));


        // Verify
        verify(employeeService).updateEmployee(
                eq(1L),
                any(EmployeeRequestDto.class)
        );
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteEmployee_shouldReturnSuccessResponse() throws Exception {

        mockMvc.perform(
                        delete("/employees/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("Employee deleted succesfully"));


        verify(employeeService).deleteEmployee(1L);
    }




    @Test
    void searchEmployeeByName_shouldReturnEmployees() throws Exception {

        EmployeeResponseDto employee1 = new EmployeeResponseDto();

        employee1.setEmployeeId(1L);
        employee1.setEmployeeName("Badri");
        employee1.setEmployeeAge(25);
        employee1.setEmployeeDepartment("IT");
        employee1.setEmployeeSalary(60000.00);


        EmployeeResponseDto employee2 = new EmployeeResponseDto();

        employee2.setEmployeeId(2L);
        employee2.setEmployeeName("Badri Kumar");
        employee2.setEmployeeAge(26);
        employee2.setEmployeeDepartment("HR");
        employee2.setEmployeeSalary(65000.00);


        List<EmployeeResponseDto> employees =
                Arrays.asList(employee1, employee2);


        when(employeeService.searchEmployeeByName("Badri")).thenReturn(employees);


        mockMvc.perform(get("/employees/search").param("name", "Badri"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.length()")
                        .value(2))
                .andExpect(jsonPath("$.data[0].employeeName")
                        .value("Badri"))
                .andExpect(jsonPath("$.data[1].employeeName")
                        .value("Badri Kumar"));


        verify(employeeService)
                .searchEmployeeByName("Badri");
    }




    @Test
    void searchEmployeesByDepartment_shouldReturnEmployees()
            throws Exception {

        EmployeeResponseDto employee = new EmployeeResponseDto();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);


        when(employeeService.searchEmployeesByDepartment("IT"))
                .thenReturn(List.of(employee));


        mockMvc.perform(get("/employees/department").param("department", "IT")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()")
                        .value(1))
                .andExpect(jsonPath("$.data[0].employeeDepartment")
                        .value("IT"));


        verify(employeeService)
                .searchEmployeesByDepartment("IT");
    }




    @Test
    void searchEmployeesBySalary_shouldReturnEmployees()
            throws Exception {

        EmployeeResponseDto employee = new EmployeeResponseDto();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeAge(25);
        employee.setEmployeeDepartment("IT");
        employee.setEmployeeSalary(60000.00);


        when(employeeService.searchEmployeesBySalary(50000, 70000)).thenReturn(List.of(employee));


        mockMvc.perform(
                        get("/employees/salary")
                                .param("minSalary", "50000")
                                .param("maxSalary", "70000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()")
                        .value(1))
                .andExpect(jsonPath("$.data[0].employeeSalary")
                        .value(60000.00));


        verify(employeeService).searchEmployeesBySalary(50000, 70000);
    }




    @Test
    void searchEmployeeBySalaryGreaterThan_shouldReturnEmployees()
            throws Exception {

        EmployeeResponseDto employee = new EmployeeResponseDto();

        employee.setEmployeeId(1L);
        employee.setEmployeeName("Badri");
        employee.setEmployeeSalary(70000.00);


        when(employeeService.searchEmployeeBySalaryGreaterThan(50000)).thenReturn(List.of(employee));


        mockMvc.perform(
                        get("/employees/salary/GreaterThan")
                                .param("salary", "50000")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()")
                        .value(1))
                .andExpect(jsonPath("$.data[0].employeeSalary")
                        .value(70000.00));


        verify(employeeService).searchEmployeeBySalaryGreaterThan(50000);
    }



    @Test
    void findTotalCountOfEmployees_shouldReturnCount()
            throws Exception {

        when(employeeService.findTotalCountOfEmployees()).thenReturn(10L);


        mockMvc.perform(
                        get("/employees/total/count")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").value(10));


        verify(employeeService).findTotalCountOfEmployees();
    }


    @Test
    void findEmployeeMaximumSalary_shouldReturnMaximumSalary()
            throws Exception {

        when(employeeService.findEmployeeMaximumSalary()).thenReturn(100000.00);


        mockMvc.perform(
                        get("/employees/salary/max")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data")
                        .value(100000.00));


        verify(employeeService).findEmployeeMaximumSalary();
    }




    @Test
    void findEmployeeMinimumSalary_shouldReturnMinimumSalary()
            throws Exception {

        when(employeeService.findEmployeeMinimumSalary()).thenReturn(30000.00);


        mockMvc.perform(
                        get("/employees/salary/min")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data")
                        .value(30000.00));


        verify(employeeService).findEmployeeMinimumSalary();
    }


    @Test
    void findEmployeeAverageSalary_shouldReturnAverageSalary()
            throws Exception {

        when(employeeService.findEmployeeAverageSalary()).thenReturn(60000.00);


        mockMvc.perform(
                        get("/employees/salary/avg")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data")
                        .value(60000.00));


        verify(employeeService).findEmployeeAverageSalary();
    }




    @Test
    void findEmployeeSumOfSalary_shouldReturnTotalSalary()
            throws Exception {

        when(employeeService.findEmployeesSumOfSalary())
                .thenReturn(180000.00);


        mockMvc.perform(
                        get("/employees/salary/sum")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data")
                        .value(180000.00));


        verify(employeeService).findEmployeesSumOfSalary();
    }



    @Test
    void getDistinctDepartments_shouldReturnDistinctDepartments()
            throws Exception {

        when(employeeService.getDistinctDepartments())
                .thenReturn(List.of("IT", "HR", "Finance"));


        mockMvc.perform(
                        get("/employees/department/distinct")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()")
                        .value(3))
                .andExpect(jsonPath("$.data[0]")
                        .value("IT"))
                .andExpect(jsonPath("$.data[1]")
                        .value("HR"))
                .andExpect(jsonPath("$.data[2]")
                        .value("Finance"));


        verify(employeeService).getDistinctDepartments();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void getById_employeeNotFound_shouldReturn404() throws Exception {

        long employeeId = 100L;

        when(employeeService.getById(employeeId)).thenThrow(new EmployeeNotFoundException("employee not found with id  " + employeeId));

        mockMvc.perform(
                        get("/employees/{id}", employeeId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message")
                        .value("employee not found with id  100"));

        verify(employeeService).getById(employeeId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_missingEmployeeName_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeAge": 25,
                "employeePhone": "9876543210",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeSalary": 60000,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(EmployeeRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_invalidEmail_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeName": "Badri",
                "employeeAge": 25,
                "employeePhone": "9876543210",
                "employeeEmail": "badri-invalid-email",
                "employeeDepartment": "IT",
                "employeeSalary": 60000,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(EmployeeRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_invalidPhone_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeName": "Badri",
                "employeeAge": 25,
                "employeePhone": "98765",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeSalary": 60000,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(EmployeeRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_invalidAge_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeName": "Badri",
                "employeeAge": 17,
                "employeePhone": "9876543210",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeSalary": 60000,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(EmployeeRequestDto.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_invalidSalary_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeName": "Badri",
                "employeeAge": 25,
                "employeePhone": "9876543210",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeSalary": 0,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).saveEmployee(any(EmployeeRequestDto.class));


    }
    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void saveEmployee_missingEmployeeSalary_shouldReturn400() throws Exception {

        String requestBody = """
            {
                "employeeName": "Badri",
                "employeeAge": 25,
                "employeePhone": "9876543210",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        post("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never())
                .saveEmployee(any(EmployeeRequestDto.class));
    }
    @Test
    @WithMockUser(roles = "ADMIN")
    @AutoConfigureMockMvc(addFilters = false)
    void updateEmployee_missingEmployeeName_shouldReturn400() throws Exception {

        long employeeId = 1L;

        String requestBody = """
            {
                "employeeAge": 25,
                "employeePhone": "9876543210",
                "employeeEmail": "badri@gmail.com",
                "employeeDepartment": "IT",
                "employeeSalary": 60000,
                "employeeAddress": "Proddatur"
            }
            """;

        mockMvc.perform(
                        put("/employees/{id}", employeeId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());

        verify(employeeService, never())
                .updateEmployee(
                        anyLong(),
                        any(EmployeeRequestDto.class)
                );
    }
}


