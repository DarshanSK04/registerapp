package com.example.registerapp.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.registerapp.Requests.Request;
import com.example.registerapp.Services.UserService;
import com.example.registerapp.UserData.Users;
import com.example.registerapp.UserRepository.UserRepos;




@RestController
@CrossOrigin(origins = {
    "http://localhost:4200",
    "https://expense-frontend-mrqp.onrender.com"
})

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
    
    @PostMapping("/adduser")
    public Users addUser(@RequestBody Users user)
    {
        return userservice.addUser(user);
    }

@PostMapping("/loginUser")
public ResponseEntity<?> loginUser(@RequestBody Request loginRequest) {
    try {
        Users user = userservice.loginUser(loginRequest);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid email or password ❌");
        }

        return ResponseEntity.ok(user);
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Something went wrong on the server 🚨");
    }
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


    //User Acccount Details

    //Account Details.
    @GetMapping("/api/user/{email}")
public Users getUserByEmail(@PathVariable String email) {
    return userRepos.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("User not found"));
}

//Change password API..
@PutMapping("/api/user/change-password")
public ResponseEntity<?> changePassword(@RequestBody Map<String, String> payload) {
    Users user = userRepos.findByEmail(payload.get("email"))
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getPassword().equals(payload.get("oldPassword"))) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid old password");
    }

    user.setPassword(payload.get("newPassword"));
    userRepos.save(user);
    return ResponseEntity.ok("Password changed successfully");
}


//Delete Account ...
@PostMapping("/api/user/delete")
public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> payload) {
    Users user = userRepos.findByEmail(payload.get("email"))
        .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getPassword().equals(payload.get("password"))) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect password");
    }

    userRepos.delete(user);
    return ResponseEntity.ok("User deleted successfully");
}



                                                                                                                                                                                                                                                                 
}
    
    
    

