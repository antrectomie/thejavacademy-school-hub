package com.thejavacademy.userservice.controller;

import com.thejavacademy.userservice.model.dto.UserIdentity;
import com.thejavacademy.userservice.model.dto.UserResponse;
import com.thejavacademy.userservice.model.entity.User;
import com.thejavacademy.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserRestController {


    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    //TODO:delete this method after testing
    @GetMapping
    public List<UserIdentity> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUser(id));
    }


    @GetMapping("/{id}/friends")
    public ResponseEntity<List<UserIdentity>> searchUserFriends(@PathVariable String id) {
        List<UserIdentity> response = userService.getFriends(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User savedUser = userService.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<UserIdentity>> searchUsers(@PathVariable String term) {
        return ResponseEntity.ok(userService.searchUsers(term));
    }
}
