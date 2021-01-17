package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.*;
import com.socialhk.social_network.model.repository.FriendRepository;
import com.socialhk.social_network.model.repository.TokensRepository;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/friend")
public class FriendsController {
    @Autowired
    FriendRepository friendsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokensRepository tokensRepository;

    @GetMapping(path = "/{id}/{token}",produces = "application/json")
    @ResponseStatus(code = HttpStatus.FOUND)
    public List<UserEntity> getFriends (@PathVariable String id , @PathVariable String token){
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(id) ) {
            List<FriendsEntity> listFriend = friendsRepository.findByUserId(id);
            List<String> listUser = new ArrayList<>();
            for (FriendsEntity f : listFriend) {
                listUser.add(f.getFriendId());
            }
            return userRepository.findByUserNameIn(listUser);
        }else{
            return null;
        }
    }

    @PostMapping(path = "/{token}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public FriendsEntity addFriend (@RequestBody FriendsEntity entity , @PathVariable String token){
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(entity.getUserId()) ) {
            return friendsRepository.save(entity);
        }else{
            return null;
        }
    }

    @PostMapping (path = "/delete/{token}" , consumes = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteFriend(@RequestBody FriendsId entity , @PathVariable String token) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(entity.getUserId()) ) {
            friendsRepository.deleteById(entity);
        }
    }

    @GetMapping(path = "/isExist/{userId}/{friendId}" )
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isExist(@PathVariable String userId,@PathVariable String friendId){
        if( friendsRepository.existsById( new FriendsId(userId,friendId) ) ) return true;
        return false;
    }

}
