package com.badri.employee_management_system.Repository;

import com.badri.employee_management_system.Dto.DepartmentCountResponseDto;
import com.badri.employee_management_system.Dto.EmployeeSalaryStatusDto;
import com.badri.employee_management_system.Entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByEmployeeNameContainingIgnoreCase(String employeeName);
    List<Employee> findByEmployeeDepartment(String employeeDepartment);
    List<Employee> findByEmployeeSalaryBetween(double minSalary, double maxSalary);
    List<Employee> findByEmployeeAgeBetween(Integer minAge , Integer maxAge);
    List<Employee> findByEmployeeSalaryGreaterThan(double salary);
    List<Employee> findByEmployeeSalaryLessThan(double salary);
    List<Employee> findByEmployeeSalaryGreaterThanEqual (double salary);
    List<Employee> findByEmployeeSalaryLessThanEqual (double salary);
    List<Employee> findByEmployeeNameStartsWith(String prefix);
    List<Employee> findByEmployeeNameEndsWith(String prefix);
    List<Employee> findByEmployeeDepartmentIn(List<String> departments);
    List<Employee> findByEmployeeDepartmentNot(String department);

    @Query("select e FROM Employee e WHERE e.employeeDepartment = :department" )
    List<Employee> findEmployeesByDepartment(@Param("department") String department);

    @Query("select e From Employee e WHERE e.employeeDepartment =:department AND e.employeeSalary >=:salary")
    List<Employee> findEmployeesByDepartmentAndSalary(
            @Param("department") String department,
            @Param("salary") double salary
    );


    @Query("select e FROM Employee e WHERE e.employeeDepartment =:department1 OR e.employeeDepartment = :department2")
    List<Employee> findEmployeesByDepartmentOrDepartment(
            @Param("department1") String department1,
            @Param("department2") String department2
    );


    //LIKE operations

    @Query("""
SELECT e
FROM Employee e
WHERE e.employeeName LIKE CONCAT('%', :name, '%')
""")
    List<Employee> findEmployeesByNameContaining(
            @Param("name") String name
    );


    @Query("SELECT e FROM Employee e WHERE e.employeeName LIKE CONCAT(:name, '%')")
    List<Employee> findEmployeesByNameStartingWith(
            @Param("name") String name

           // GET /employees/search/startswith?name=Vir
           // Expected:Virat
               //     Virendra


    );

    @Query("""
SELECT e
FROM Employee e
WHERE e.employeeName LIKE CONCAT('%', :name)
""")
    List<Employee> findEmployeesByNameEndingWith(
            @Param("name") String name
//            API
//                    GET /employees/search/endswith?name=Vir
//
//                    Expected:
//
//                    SuperVir
    );

    //Order By operations
    //salary desc order
    @Query("SELECT e FROM Employee e ORDER BY e.employeeSalary Desc")
    List<Employee> findEmployeesOrderBySalaryDesc();

    //salary asc order

    @Query("SELECT e FROM Employee e ORDER BY e.employeeSalary Asc")
    List<Employee> findEmployeesOrderBySalaryAsc();

    @Query("SELECT e FROM Employee e ORDER BY e.employeeName Asc")
    List<Employee> findEmployeesOrderByNameAsc();

    @Query("SELECT e FROM Employee e ORDER BY e.employeeName Desc")
    List<Employee> findEmployeesOrderByNameDesc();

    @Query("SELECT COUNT(e) FROM Employee e ")
    public Long findTotalCountOfEmployees();

    @Query("SELECT MAX(e.employeeSalary) FROM Employee e")
    public Double findMaximumSalary();

    @Query("SELECT MIN(e.employeeSalary) FROM Employee e")
    public Double findMinimumSalary();

    @Query("SELECT AVG(e.employeeSalary) FROM Employee e")
    public Double findAverageSalary();

    @Query("SELECT SUM(e.employeeSalary) FROM Employee e")
    public Double findSumOfSalary();

    @Query("SELECT new com.badri.employee_management_system.Dto.DepartmentCountResponseDto(e.employeeDepartment ,COUNT(e)) FROM  Employee e GROUP BY e.employeeDepartment" )
    List<DepartmentCountResponseDto> getEmployeeCountByDepartment();


    @Query("SELECT new com.badri.employee_management_system.Dto.DepartmentCountResponseDto(e.employeeDepartment ,COUNT(e))  FROM Employee e GROUP BY e.employeeDepartment HAVING COUNT(e) > :count ")
    List<DepartmentCountResponseDto> findDepartmentHavingEmployeesCountGreaterThan(@Param("count") Long count);

    @Query("SELECT DISTINCT e.employeeDepartment FROM Employee e")
    public List<String> getDistinctDepartments();

    @Query("SELECT e FROM Employee e WHERE e.employeeDepartment IN :departments ")
    public List<Employee> findEmployeesByDepartmentIn(@Param("departments") List<String> departments);

    @Query("""
SELECT e
FROM Employee e
WHERE e.employeeDepartment NOT IN :departments
""")
    List<Employee> findEmployeesByDepartmentNotIn(
            @Param("departments") List<String> departments
    );


    @Query("SELECT new com.badri.employee_management_system.Dto.EmployeeSalaryStatusDto(e.employeeName, CASE WHEN e.employeeSalary >=80000 THEN 'High Salary' WHEN e.employeeSalary >=50000 THEN 'Medium Salary' Else 'Low Salary' END )FROM Employee e")

    List<EmployeeSalaryStatusDto> findEmployeeSalaryStatus();

    @Query(value = "select * from employees where employee_salary > :salary",nativeQuery = true)
    List<Employee> findEmployeesBySalaryGreaterThanNative(@Param("salary") double salary);
}

