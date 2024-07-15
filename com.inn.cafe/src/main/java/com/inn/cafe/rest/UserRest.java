package com.inn.cafe.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path="/user")
public interface UserRest {

    @PostMapping(path="/signup")
    ResponseEntity<String> signUp(@RequestBody Map<String, String> requMap);

    @PostMapping(path="/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> reqMap);

    @GetMapping(path="/get")
    ResponseEntity<List<com.inn.cafe.wrapper.UserWrapper>> getAllUser();

    @PostMapping(path="/update")
    public ResponseEntity<String> update(@RequestBody(required = true)Map<String, String> reqMap);
    
    @GetMapping(path="/checkToken")
    ResponseEntity<String> checkToken();    
    
    @PostMapping(path="/changePassword")
    ResponseEntity<String> changepassword(@RequestBody Map<String, String> reMap);

    @PostMapping(path="/forgotPassword")
    ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> reMap);





}





