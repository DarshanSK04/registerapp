package com.example.registerapp.UserRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.registerapp.UserData.Users;

@Repository
public interface UserRepos extends JpaRepository<Users,Long> {

    public Optional<Users> getByEmail(String email);

    public Optional<Users> findByEmail(String email);

    public Optional<Users> findByPhone(String phone);

}
