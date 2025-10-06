package com.example.registerapp.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.registerapp.Requests.Request;
import com.example.registerapp.UserData.Users;
import com.example.registerapp.UserRepository.UserRepos;

@Service
public class UserService {

    @Autowired
    UserRepos userRepos;

    public Users addUser(@RequestBody Users user)
    {
        Optional<Users> existingByEmail=userRepos.findByEmail(user.getEmail());
        if(existingByEmail.isPresent())
        {
            throw new RuntimeException("Email is already Registered");
        }
        Optional<Users> existingByPhone=userRepos.findByPhone(user.getPhone());
        if(existingByPhone.isPresent())
        {
            throw new RuntimeException("Phone No. is already Registered");
        }

        return userRepos.save(user);
    }


    public Users loginUser(Request loginRequest)
    {
        Optional<Users> user = userRepos.findByEmail(loginRequest.getEmail());
    
        // Pehle ye line galat thi: if (user == null)
        if (!user.isPresent()) {
            System.out.println("User not found with email: " + loginRequest.getEmail());
            return null;
        }
    
        Users userLogin = user.get();
    
        if (!userLogin.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("Invalid password for email: " + loginRequest.getEmail());
            return null;
        }
    
        System.out.println("User logged in successfully: " + userLogin.getEmail());
        return userLogin;
    }
    
    public Boolean checkUserIfExistByEmail(Request loginRequest)
    {
        Optional<Users> user=userRepos.findByEmail(loginRequest.getEmail());
        System.out.println(user);
        System.out.println(loginRequest.getEmail());
            if(user.isEmpty())
        {
            return false;
        }
        return true;
        
        

    }



    

}
