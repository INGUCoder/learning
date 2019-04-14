package com.mz.consumerservice.controller;

import com.mz.consumerservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("movie")
public class MovieController {
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/{id}")
    public User selectById(@PathVariable Long id){
        return restTemplate.getForObject("http://localhost:7900/users/"+id,User.class);
    }
}
