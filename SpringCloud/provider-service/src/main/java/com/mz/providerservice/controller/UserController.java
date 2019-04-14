package com.mz.providerservice.controller;

import com.mz.providerservice.entity.User;
import com.mz.providerservice.repository.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    UserMapper userMapper;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {

        return this.userMapper.selectById(id);

    }

}
