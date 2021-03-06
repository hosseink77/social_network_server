package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.TokensEntity;
import com.socialhk.social_network.model.entity.UserEntity;
import com.socialhk.social_network.model.repository.TokensRepository;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
//@RequestMapping(path = "/User")
public class UserController {
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokensRepository tokensRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @GetMapping(path = "/getAll", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.FOUND)
//    public List<UserEntity> getUser() {
//        return (List<UserEntity>) repository.findAll();
//    }

    @GetMapping(path = "/", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.FOUND)
    public UserEntity getUser(@RequestParam String id) {
        try {
            return repository.findByUserName(id);
        }catch (NoSuchElementException ex){
            return null;
        }

    }

    @GetMapping(path = "/logIn/{token}", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.FOUND)
    public UserEntity logIn(@PathVariable String token) {
        try {
            TokensEntity tokensEntity = tokensRepository.findByToken(token);
            if(tokensEntity != null && TokensController.isValid(tokensEntity) ) {
                return repository.findByUserName(tokensEntity.getUserId());
            }else {
                return null;
            }
        }catch (NoSuchElementException | NullPointerException ex){
            return null;
        }

    }

//    @GetMapping(path = "/")
//    public String get() {
//
//        String message = "Hello World";
//        return message;
//    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserEntity addUser(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @PostMapping(path = "/edit/{token}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserEntity editUser(@RequestBody UserEntity user , @PathVariable String token) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if(tokensEntity != null && TokensController.isValid(tokensEntity) &&
                repository.findByUserName(tokensEntity.getUserId()).getUuid().equals(user.getUuid())) {
            if (user.getPassword() == null) {
                user.setPassword(repository.findById(user.getUuid()).get().getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return repository.save(user);
        }
        return null;
    }

//    @PostMapping(path = "/add", consumes = "application/json", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.CREATED)
//    public boolean add(@RequestBody int num) {
//        System.out.println("poooooost int  :  "+num);
//        return true;
//    }


    @GetMapping(path = "/isConnect")
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isConnect(){
        return true;
    }

    @GetMapping(path = "/isExist")
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isExist(@RequestParam String id){
        if( repository.existsByUserName(id) ) return true;
        return false;

    }

}
