package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.FriendsEntity;
import com.socialhk.social_network.model.entity.UserEntity;
import com.socialhk.social_network.model.repository.FriendRepository;
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

    @GetMapping(path = "/{id}",produces = "application/json")
    @ResponseStatus(code = HttpStatus.FOUND)
    public List<UserEntity> getFriends (@PathVariable String id){
        List<FriendsEntity> listFriend = friendsRepository.findByUser(id);
        List<String> listUser = new ArrayList<>();
        for (FriendsEntity f : listFriend){
            listUser.add(f.getFriend());
        }
        return (List<UserEntity>) userRepository.findAllById(listUser);
    }

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    public FriendsEntity addFriend (@RequestBody FriendsEntity entity){
        return friendsRepository.save(entity);
    }

}
