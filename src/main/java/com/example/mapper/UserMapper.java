package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findByUsername(String username);
    int insert(User user);
    int existsByUsername(String username);
    int existsByEmail(String email);
    int updateLastLoginTime(Long id, LocalDateTime lastLoginTime);
}    