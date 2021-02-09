package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.TokensEntity;
import com.socialhk.social_network.model.entity.UserEntity;
import com.socialhk.social_network.model.repository.TokensRepository;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/token")
public class TokensController {

    @Autowired
    private TokensRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}/{pass}/{bool}" )
    public String getToken (@PathVariable String id , @PathVariable String pass ,@PathVariable String bool){
        boolean b = Boolean.parseBoolean(bool);
        try {
            if (!canLogIn(id, pass)) {
                return null;
            }
            if (repository.existsById(id) && !b) {
                TokensEntity entity = repository.findById(id).get();
                if( isValid(entity) ) {
                    return entity.getToken();
                }
            }
            return repository.save( b? new TokensEntity( id , UUID.randomUUID().toString() , new Date(System.currentTimeMillis()+(30*24*60*60*1000L)) ) :
                            new TokensEntity( id , UUID.randomUUID().toString() , new Date(System.currentTimeMillis()+(24*60*60*1000L)) )
                    ).getToken();

        }catch (Exception ex){
            System.out.println("exception from getToken and Exception class: "+ex.getClass().getName());
            return null;
        }
    }

    @DeleteMapping("/delete/{token}/{id}")
    public void deleteToken(@PathVariable String token , @PathVariable String id){
        try {
            String tokenUserId=null;
            if (repository.existsById(id)) {
                tokenUserId = repository.findById(id).get().getToken();
            }

            if(tokenUserId != null && tokenUserId.equals(token)){
                repository.deleteById(id);
            }

        }catch (Exception ex){
            System.out.println("exception from deleteToken and Exception class: "+ex.getClass().getName());
        }
    }

    public static boolean isValid(TokensEntity token){
        return token.getExpiration().compareTo(new Date()) ==1;
    }

    @GetMapping("/validation/{user}/{token}")
    public boolean validationToken(@PathVariable String user , @PathVariable String token){
        TokensEntity tokensEntity = repository.findByToken(token);
        if(user.equals(tokensEntity.getUserId())){
            return isValid(tokensEntity);
        }
        return false;
    }

    public boolean canLogIn( String id ,  String pass) {
        try {
            UserEntity user = userRepository.findByUserName(id);
            if(passwordEncoder.matches(pass, user.getPassword())){
                return true;
            }
            else {
                return false;
            }
        }catch (NoSuchElementException | NullPointerException ex){
            return false;
        }

    }

}
