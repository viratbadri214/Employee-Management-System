package com.badri.employee_management_system.Service;

import com.badri.employee_management_system.Dto.RegisterRequestDto;

public interface AuthService {
    String login (String username , String password);
    void register (RegisterRequestDto request);
}
