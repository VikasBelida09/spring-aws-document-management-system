package com.example.aws.demo.controller;

import com.example.aws.demo.entities.User;
import com.example.aws.demo.service.S3Service;
import com.example.aws.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {
 @Autowired
 private UserService userService;
 @Autowired
 private S3Service s3Service;

 @PostMapping("/save")
 public ResponseEntity<User> saveUser(@RequestBody User user){
     return ResponseEntity.ok(userService.saveUser(user));
 }

 @PostMapping("/{userId}/upload/")
 public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String userId){
  return ResponseEntity.ok(s3Service.uploadFile(file,userId));
 }
}
