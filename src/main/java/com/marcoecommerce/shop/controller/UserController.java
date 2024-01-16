package com.marcoecommerce.shop.controller;

import com.marcoecommerce.shop.mapper.impl.UserMapper;
import com.marcoecommerce.shop.model.user.UserDto;
import com.marcoecommerce.shop.model.user.UserEntity;
import com.marcoecommerce.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping(path = "/users")
    public ResponseEntity<UserDto> createUsers(@RequestBody UserDto userDto) {
        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity savedUserEntity = userService.create(userEntity);

        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserEntity> userEntities = userService.findAll();
        return new ResponseEntity<>(userEntities.stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> foundUser = userService.findById(id);
        return foundUser.map(userEntity -> new ResponseEntity<>(userMapper.mapTo(userEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserDto userDto
    ) {
        if(!userService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserEntity userEntity = userMapper.mapFrom(userDto);
        UserEntity updatedUser = userService.update(id, userEntity);
        return new ResponseEntity<>(
                userMapper.mapTo(updatedUser),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        if(!userService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
