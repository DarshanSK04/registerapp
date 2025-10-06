package com.example.registerapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.web.bind.annotation.RestController;

import com.example.registerapp.Requests.Request;
import com.example.registerapp.Services.UserService;
import com.example.registerapp.UserData.Users;
import com.example.registerapp.UserRepository.UserRepos;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController

public class UserController {
    

    @Autowired
    UserService userservice;

    @Autowired
    UserRepos userRepos;

    @GetMapping("/check")
    public String checkAll()
    {
        return "App Is Running";
    }
    
    
    @CrossOrigin(origins = "http://localhost:4200")

    @PostMapping("/adduser")
    public Users addUser(@RequestBody Users user)
    {
        return userservice.addUser(user);
    }

    @PostMapping("/loginUser")
    public Users loginUser(@RequestBody Request loginRequest )
    {
        return userservice.loginUser(loginRequest);
    }
    @PostMapping("/checkuserwithemail")
    public Boolean checkUserWithEmail(@RequestBody Request userExistRequest )
    {
        return userservice.checkUserIfExistByEmail(userExistRequest);
    }
    @GetMapping("/api/users/{email}")
    public Optional<Users> getMethodName(@PathVariable String email) {
        return Optional.of(userRepos.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found")));

    }
    @PutMapping("updateuserpassword/{email}")
    public Boolean requestUpdatePassword(@RequestBody Request newpassword, @PathVariable String email) {
        
        Optional<Users> user=userRepos.findByEmail(email);
        if(user.isPresent())
        {
            Users existingUser=user.get();
            System.out.println(newpassword.getPassword());
            existingUser.setPassword(newpassword.getPassword());
            userRepos.save(existingUser);
            
            return true;
        }
        return false;

    }
}
    
    
    

