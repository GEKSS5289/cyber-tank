package com.cyber.tank.auth.dto;


import lombok.Data;

@Data
/**
 * UserLoginDTO 的核心定义。
 */
public class UserLoginDTO {
    private String username;
    private String password;
}
