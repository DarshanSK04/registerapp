package com.example.registerapp.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.registerapp.Services.EmailSenderService;
import com.example.registerapp.UserRepository.ExpenseRepos;
import com.example.registerapp.UserRepository.OTPRepos;
import com.example.registerapp.UserRepository.UserRepos;

@RestController
@RequestMapping("/api/sendOTP")
@CrossOrigin(origins = "http://localhost:4200")
public class OTPController {

    @Autowired
    UserRepos userRepos;

    @Autowired
    ExpenseRepos expenseRepos;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    OTPRepos otpRepos;

    Map<String,String>otpMap=new LinkedHashMap<>();
    String otpmail="";
    LocalDateTime date=null;
    @GetMapping("/send/{email}")
    public void otpGenerater(@PathVariable String email) {
        //Genearting OTP And Save Into OTPVerification Table

        otpmail=email;
        String otp=String.valueOf(new Random().nextInt(900000)+100000);

        otpMap.put(otpmail, otp);

        date=LocalDateTime.now().plusMinutes(5);

        String subject="Your OTP for Password Reset";
        String text="Hi, your OTP is: "+otp+" \n\nKindly take a note that this OTP is valid only for 5 Minutes\n\nDo not share this with anyone";

        emailSenderService.sendSimpleMEssage(email, subject, text);

        
    }
    @GetMapping("/verify/{otp}")
    public Boolean otpVerificationStatus(@PathVariable String otp) {
        //TODO: process POST request

        LocalDateTime newDate=LocalDateTime.now();
        if(otp.equals(otpMap.get(otpmail)) && newDate.isBefore(date))
        {
            
            
            return true;
        }
        return false;
    }
    
    

}
