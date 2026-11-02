package com.badri.employee_management_system.Controller;

import com.badri.employee_management_system.Dto.ApiResponse;
import com.badri.employee_management_system.Dto.LoginRequestDto;
import com.badri.employee_management_system.Dto.RegisterRequestDto;
import com.badri.employee_management_system.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService ;

    public AuthController (AuthService authService){
        this.authService =authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequestDto request){

       String token= authService.login(request.getUsername(),request.getPassword());

        return ResponseEntity.ok(ApiResponse.success("login successfully",token));

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register (@RequestBody RegisterRequestDto request){

        authService.register(request);
        return  ResponseEntity.ok(ApiResponse.success("registration successfully",null));

    }
}
