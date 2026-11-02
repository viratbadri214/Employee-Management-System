package com.badri.employee_management_system.Controller;

import com.badri.employee_management_system.Dto.*;

import com.badri.employee_management_system.Service.EmployeeService;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    public EmployeeController (EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponseDto > >saveEmployee(@Valid @RequestBody EmployeeRequestDto dto){
        EmployeeResponseDto responseDto=employeeService.saveEmployee(dto);
       // ApiResponse<EmployeeResponseDto >respose=new ApiResponse<>(true,"data saved successfully",responseDto);


        return  ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Employee saved succesfully",responseDto));
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>> > getAllEmployees(){
//        List<EmployeeResponseDto> responseDtos=employeeService.getAllEmployees();
//       // ApiResponse<List<EmployeeResponseDto>> response=new ApiResponse<>(true,"Employee retrieved successfully",responseDtos);
//        return  ResponseEntity.ok().body(ApiResponse.success("Employees retrieved successfully",responseDtos));
//    }


//    //Pagination
//    @GetMapping
//    public  ResponseEntity<ApiResponse<Page<EmployeeResponseDto>>> getAllEmployees(@RequestParam (defaultValue = "0") int page,@RequestParam (defaultValue = "5") int size){
//        Page<EmployeeResponseDto> responseDtos =
//                employeeService.getAllEmployees(page, size);
//
//        return ResponseEntity.ok(
//                ApiResponse.success("Employees retrieved successfully", responseDtos)
//        );
//    }


    @PreAuthorize("hasRole('USER','ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeResponseDto>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "employeeId") String sortBy,
           @RequestParam(defaultValue = "asc") String direction)
    {
        Page<EmployeeResponseDto> employees=employeeService.getAllEmployees(page,size,sortBy,direction);

        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",employees));
    }



    @GetMapping("/{id}")
    public  ResponseEntity<ApiResponse<EmployeeResponseDto>> getById(@PathVariable long id){

        EmployeeResponseDto responseDto =employeeService.getById(id);
        //ApiResponse<EmployeeResponseDto>response=new ApiResponse<>(true,"Employee retrieved successfully",responseDto);
        return  ResponseEntity.ok().body(ApiResponse.success("Employee Retrieved successfully",responseDto));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDto>> updateEmployee(@PathVariable long id, @Valid @RequestBody EmployeeRequestDto dto){
        EmployeeResponseDto responseDto=  employeeService.updateEmployee(id, dto);
        //ApiResponse<EmployeeResponseDto> response =new ApiResponse<>(true, "Employee updated succesfully",responseDto);
        return  ResponseEntity.ok().body(ApiResponse.success("Employee updated successfully",responseDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable long id){
        employeeService.deleteEmployee(id);
       // ApiResponse<String> response = new ApiResponse<>(true,"data deleted successfully",null);
        return  ResponseEntity.ok().body(ApiResponse.success("Employee deleted succesfully",null));
    }

    //custom methods

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeeName(@RequestParam("name") String employeeName){

        List<EmployeeResponseDto> employees=  employeeService.searchEmployeeByName(employeeName);
        return ResponseEntity.ok(ApiResponse.success("Employees found successfully",employees));
    }
    //find department wise

    @GetMapping("/department")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesByDeparment(@RequestParam("department") String employeeDepartment){
       List<EmployeeResponseDto> responseDtos= employeeService.searchEmployeesByDepartment(employeeDepartment);
       return  ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));

    }
    @GetMapping("/salary")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesBySalary(
            @RequestParam double minSalary , @RequestParam double maxSalary
    ) {
        List<EmployeeResponseDto> responseDtos = employeeService.searchEmployeesBySalary(minSalary, maxSalary);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", responseDtos));
    }
        @GetMapping("/age")
        public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesByAge (
                @RequestParam Integer minAge, @RequestParam Integer maxAge
                ){
            List<EmployeeResponseDto> responsedtos = employeeService.searchEmployeesByAge(minAge ,maxAge);
            return  ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responsedtos));
        }

        @GetMapping("/salary/GreaterThan")
        public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeeBySalaryGreaterThan(@RequestParam double salary)
        {
           List<EmployeeResponseDto> responseDtos = employeeService.searchEmployeeBySalaryGreaterThan(salary);
           return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
        }

        @GetMapping("/salary/less-than")
            public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesWithSalaryLessThan(@RequestParam double salary){

                List<EmployeeResponseDto> responseDtos =employeeService.findEmployeesWithSalaryLessThan(salary);
                return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
            }

        @GetMapping("/salary/greater-than-or-equal")
    public  ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeeWithSalaryGreaterThanEqual(@RequestParam double salary){

            List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesWithGreaterThanEqual(salary);
            return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
        }

    @GetMapping("/salary/less-than-or-equal")
    public  ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeeWithSalaryLessThanEqual(@RequestParam double salary){

        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesWithLessThanEqual(salary);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
    }

    @GetMapping("/name/starts-with")
    public  ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByNameStartsWith(@RequestParam String prefix){
        List<EmployeeResponseDto> responseDtos   = employeeService.findEmployeesByNameStartsWith(prefix);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
    }

    @GetMapping("/name/ends-with")
    public  ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByNameEndsWith(@RequestParam String suffix){
        List<EmployeeResponseDto> responseDtos   = employeeService.findEmployeesByNameEndsWith(suffix);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
    }

    @GetMapping("/departments")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByDepartments(
            @RequestParam List<String> departments) {

        List<EmployeeResponseDto> responseDtos =
                employeeService.findEmployeesByDepartments(departments);

        return ResponseEntity.ok(
                ApiResponse.success("Employees retrieved successfully", responseDtos));
    }

    @GetMapping("/department/not")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesNotInDepartment(
            @RequestParam String department) {

        List<EmployeeResponseDto> responseDtos =
                employeeService.findEmployeesNotInDepartment(department);

        return ResponseEntity.ok(
                ApiResponse.success("Employees retrieved successfully", responseDtos));
    }

    @GetMapping("departments/jpql")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByDepartments(@RequestParam String department){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesByDepartment(department);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));

    }

    @GetMapping("department/salary/jpql")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByDepartments(@RequestParam String department , @RequestParam double salary){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesByDepartmentAndSalary(department,salary);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));

    }


    @GetMapping("/department1/department2")
    public  ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesDepartmentOrDepartment(
            @RequestParam String department1 ,
            @RequestParam String department2
    ){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesByDepartmentOrDepartment(department1, department2);
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));

    }

    @GetMapping("/search/jpql")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesByName(
            @RequestParam String name) {

        List<EmployeeResponseDto> responseDtos =
                employeeService.findEmployeesByNameContaining(name);

        return ResponseEntity.ok(
                ApiResponse.success("Employees retrieved successfully", responseDtos));
    }

    @GetMapping("/search/startswith")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesStartingWith(
            @RequestParam String name) {

        List<EmployeeResponseDto> responseDtos =
                employeeService.findEmployeesByNameStartingWith(name);

        return ResponseEntity.ok(
                ApiResponse.success("Employees retrieved successfully", responseDtos));
    }


    @GetMapping("/search/endswith")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> searchEmployeesEndingWith(
            @RequestParam String name) {

        List<EmployeeResponseDto> responseDtos =
                employeeService.findEmployeesByNameEndingWith(name);

        return ResponseEntity.ok(
                ApiResponse.success("Employees retrieved successfully", responseDtos));
    }

    @GetMapping("salary/desc")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesOrderBySalaryDesc(

    ){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesOrderBySalaryDesc();
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
    }

    @GetMapping("salary/asc")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesOrderBySalaryAsc(

    ){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesOrderBySalaryAsc();
        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully",responseDtos));
    }

    @GetMapping("name/asc")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesOrderByNameAsc(){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesOrderByNameAsc();
        return ResponseEntity.ok(ApiResponse.success("Employees Retrived Successfully",responseDtos));
    }

    @GetMapping("name/desc")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesOrderByNameDesc(){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesOrderByNameDesc();
        return ResponseEntity.ok(ApiResponse.success("Employees Retrived Successfully",responseDtos));
    }

    @GetMapping("total/count")
    public ResponseEntity<ApiResponse<Long>> findTotalCountOfEmployees(){
        Long count = employeeService.findTotalCountOfEmployees();
        return ResponseEntity.ok(ApiResponse.success("successfully counted employees",count));
    }

    @GetMapping("/salary/max")
    public ResponseEntity<ApiResponse<Double>> findEmployeeMaximumSalary(){
        Double maximumSalary = employeeService.findEmployeeMaximumSalary();
        return  ResponseEntity.ok(ApiResponse.success(" maximum salary retrieved successfully",maximumSalary));
    }

    @GetMapping("/salary/min")
    public ResponseEntity<ApiResponse<Double>> findEmployeeMinimumSalary(){
        Double minimumSalary = employeeService.findEmployeeMinimumSalary();
        return  ResponseEntity.ok(ApiResponse.success(" Minimum salary retrieved successfully",minimumSalary));
    }

    @GetMapping("/salary/avg")
    public ResponseEntity<ApiResponse<Double>> findEmployeeAverageSalary(){
        Double averageSalary = employeeService.findEmployeeAverageSalary();
        return  ResponseEntity.ok(ApiResponse.success(" average salary retrieved successfully",averageSalary));
    }

    @GetMapping("/salary/sum")
    public ResponseEntity<ApiResponse<Double>> findEmployeeSumOfSalary(){
        Double sumOfSalary = employeeService.findEmployeesSumOfSalary();
        return  ResponseEntity.ok(ApiResponse.success(" sum of salary retrieved successfully",sumOfSalary));
    }

    @GetMapping("/department/count")
    public ResponseEntity<ApiResponse<List<DepartmentCountResponseDto>>> getEmployeesCountByDepartment(){
        List<DepartmentCountResponseDto> responseDtos = employeeService.getEmployeeCountByDepartment();

        return ResponseEntity.ok(ApiResponse.success("department wise employee count retrieved successfully",responseDtos));
    }

    @GetMapping("/department/count/greater-than")
    public ResponseEntity<ApiResponse<List<DepartmentCountResponseDto>>> findDepartmentHavingEmployeeCountGreaterThan(@RequestParam Long count){
        List<DepartmentCountResponseDto> responseDtos = employeeService.findDepartmentEmployeesCountGreaterThan(count);
        return ResponseEntity.ok(ApiResponse.success("department having employee count retrieved successfully",responseDtos));
    }

    @GetMapping("/department/distinct")
    public ResponseEntity<ApiResponse<List<String>>> getDistinctDepartments(){

        List<String> distinctDepartments = employeeService.getDistinctDepartments();
        return ResponseEntity.ok(ApiResponse.success("distinct departments retrived successfully",distinctDepartments));
    }

    @GetMapping("/department/in")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByDepartmentIn(@RequestParam List<String> departments){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeeByDepartmentIn(departments);
        return ResponseEntity.ok(ApiResponse.success("Employees Retrieved department in",responseDtos));

    }

    @GetMapping("/department/not-in")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesByDepartmentNotIn(
            @RequestParam List<String> departments) {

        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesByDepartmentNotIn(departments);

        return ResponseEntity.ok(ApiResponse.success("Employees retrieved successfully", responseDtos));
    }

    @GetMapping("/salary/status")
    public ResponseEntity<ApiResponse<List<EmployeeSalaryStatusDto>>> findEmployeeSalaryStatus(){
        List<EmployeeSalaryStatusDto> responseDto = employeeService.findEmployeeSalaryStatus();
        return ResponseEntity.ok(ApiResponse.success("Employee salary status retrieved successfully ", responseDto));
    }

    @GetMapping("/salary/native")
    public ResponseEntity<ApiResponse<List<EmployeeResponseDto>>> findEmployeesBySalaryGreaterThanNative(@RequestParam double salary){
        List<EmployeeResponseDto> responseDtos = employeeService.findEmployeesBySalaryGreaterThanNative(salary);
        return ResponseEntity.ok(ApiResponse.success("employees Retrieved successfully",responseDtos));
    }
}
