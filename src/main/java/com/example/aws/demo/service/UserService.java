package com.example.aws.demo.service;

import com.example.aws.demo.entities.User;
import com.example.aws.demo.repositories.rds.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {
 @Autowired
 private UserRepository userRepository;
 public User saveUser(User user){
     user.setCreatedAt(new Date());
     user.setUpdatedAt(new Date());
     return userRepository.save(user);
 }
 public User getById(Long userId){
     return userRepository.findById(userId).orElse(null);
 }
 public List<User> getAllUsers(){
     return userRepository.findAll();
 }
}
