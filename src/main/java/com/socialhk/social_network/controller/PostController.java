package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.*;
import com.socialhk.social_network.model.repository.FriendRepository;
import com.socialhk.social_network.model.repository.PostRepository;
import com.socialhk.social_network.model.repository.TokensRepository;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/post")
public class PostController {
    @Autowired
    private PostRepository repository;
    @Autowired
    private FriendRepository friendsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokensRepository tokensRepository;


    @GetMapping(path ={"/getAll" ,"/getAll/{user}/{token}" , "/getAll/{user}/{page}"}, produces = "application/json")
    @ResponseStatus(code = HttpStatus.FOUND)
    public List<PostEntity> getAllPost(@PathVariable(required = false) Integer page , @PathVariable String user,@PathVariable String token ) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(user) ) {
            page = (page == null) ? 1 : page;
            int last = page * 10;
            Pageable pageable = PageRequest.of(last - 10, last, Sort.Direction.DESC, "date");

            List<String> friends = getFriends(user);
            System.out.println(friends);
            return repository.findByOwnerIdIn(friends, pageable);
        }else{
            return null;
        }
    }


    public List<String> getFriends (String id){
        List<FriendsEntity> listFriend = friendsRepository.findByUserId(id);
        List<String> listUser = new ArrayList<>();
        for (FriendsEntity f : listFriend){
            listUser.add(f.getFriendId());
        }

        List<String> friendsId = new ArrayList<>();
        List<UserEntity> friendsEntity = userRepository.findByUserNameIn(listUser);

        for (UserEntity user : friendsEntity){
            friendsId.add(user.getUserName());
        }

        return friendsId;
    }



    @GetMapping(path = "/{id}/{title}", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.FOUND)
    public PostEntity getPost(@PathVariable String id,@PathVariable String title) {
        try {
            return repository.findById(new PostId(id,title)).get();
        }catch (NoSuchElementException ex){
            return null;
        }

    }

    @GetMapping(path = "/{id}", produces = "application/json")
//    @ResponseStatus(code = HttpStatus.FOUND)
    public List<PostEntity> getPostByOwnerId(@PathVariable String id) {
        try {
            return repository.findByOwnerId(id);
        }catch (NoSuchElementException ex){
            return null;
        }

    }

    @PostMapping(path = "/{token}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostEntity addPost(@RequestBody PostEntity post , @PathVariable String token) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(post.getOwnerId()) ) {
            return repository.save(post);
        }else{
            return null;
        }
    }

    @PostMapping(path = "/edit/{oldTitle}/{token}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostEntity editPost(@RequestBody PostEntity post, @PathVariable String oldTitle , @PathVariable String token) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(post.getOwnerId()) ) {
            if (!post.getTitle().equals(oldTitle)) {
                repository.deleteById(new PostId(post.getOwnerId(), oldTitle));
            }
            return repository.save(post);
        }else{
            return null;
        }
    }

    @DeleteMapping (path = "/delete/{id}/{title}/{token}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deletePost(@PathVariable String id,@PathVariable String title , @PathVariable String token) {
        TokensEntity tokensEntity = tokensRepository.findByToken(token);
        if (tokensEntity != null && tokensEntity.getUserId().equals(id) ) {
            repository.deleteById(new PostId(id, title));
        }
    }

    @GetMapping(path = "/isExist/{id}/{title}" )
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isExist(@PathVariable String id,@PathVariable String title){
        if( repository.existsById( new PostId(id,title) ) ) return true;
        return false;

    }



}
