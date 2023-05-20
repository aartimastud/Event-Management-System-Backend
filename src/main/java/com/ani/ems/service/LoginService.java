package com.ani.ems.service;

import com.ani.ems.dto.ForgotpassDto;
import com.ani.ems.dto.LoginDto;
import com.ani.ems.dto.LoginResponseDto;
import com.ani.ems.dto.RegisterDto;

public interface LoginService {
    Integer registerUser(RegisterDto dto);

    String loginUser(LoginDto dto);

    String forgotpass(ForgotpassDto dto);

    LoginResponseDto loginUserForResponse(LoginDto dto);
}
