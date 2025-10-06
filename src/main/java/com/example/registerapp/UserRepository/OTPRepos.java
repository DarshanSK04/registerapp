package com.example.registerapp.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.registerapp.UserData.OTPVerification;



@Repository
public interface OTPRepos extends JpaRepository <OTPVerification,Long>{

    OTPVerification findByEmail(String email);
    OTPVerification findByEmailAndOtp(String email,String otp);

}
