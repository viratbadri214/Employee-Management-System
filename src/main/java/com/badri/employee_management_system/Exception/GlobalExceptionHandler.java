package com.badri.employee_management_system.Exception;

import com.badri.employee_management_system.Dto.ApiResponse;

import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleInvalidEmployee(EmployeeNotFoundException ex){
       // ApiResponse<String> response = new ApiResponse<>(false,ex.getMessage(),null);
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.failure(ex.getMessage(),null));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidJson(HttpMessageNotReadableException ex){
        //ApiResponse<Object> response = new ApiResponse<>(false,"Invalid Json Request",null);
        return   ResponseEntity.badRequest().body(ApiResponse.failure("Invalid Json request",null));
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleParameterMissing(MissingServletRequestParameterException ex){

       // ApiResponse<Object>response = new ApiResponse<>(false,"Required parameter '" + ex.getParameterName() + "' is missing",null);

        return  ResponseEntity.badRequest().body(ApiResponse.failure("Required parameter "+ex.getParameterName()+" is missing",null));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String,String>>> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String,String> errors= new HashMap<>();
         ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),error.getDefaultMessage()));
        // ApiResponse<Map<String,String>> response= new ApiResponse<>(false,"Validation failed. Please check the input fields.",errors);
         return  ResponseEntity.badRequest().body(ApiResponse.failure("validations failed .please check the input fields",errors));
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex){
       // ApiResponse<Object> response = new ApiResponse<>(false,"Parameter '" + ex.getName() + "' has an invalid type",null);
        return  ResponseEntity.badRequest().body(ApiResponse.failure("parameter "+ex.getName()+"has invalid type",null));
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex){
       // ApiResponse<Object> response =new ApiResponse<>(false,"HTTP method " + ex.getMethod() + " is not supported",null);
        return  ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ApiResponse.failure("Http method"+ex.getMethod()+"is not supported",null));
    }
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiResponse<Object>> handlePropertyReferenceException(
            PropertyReferenceException ex){

        return ResponseEntity.badRequest()
                .body(ApiResponse.failure("Invalid sort field", null));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception ex){
       // ApiResponse<Object> response = new ApiResponse<>(false,"Internal Server Error",null);
        ex.printStackTrace();
        return  ResponseEntity.internalServerError().body(ApiResponse.failure("something went wrong please check",null));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(
            BadCredentialsException ex) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid username or password");
    }
}
