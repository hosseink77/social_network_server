package com.socialhk.social_network.model;

import com.socialhk.social_network.model.entity.UserEntity;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Permission {

    @Autowired
    private  UserRepository userRepository;

    public  Boolean PermissionLogin (String id){
//        return userRepository.findById(user.getId()).isPresent();
        return userRepository.existsById(id);
    }
}
