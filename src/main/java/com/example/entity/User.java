package com.example.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String userType;
    private Boolean enabled;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}    