package com.socialhk.social_network.controller;

import com.socialhk.social_network.model.entity.PostEntity;
import com.socialhk.social_network.model.entity.PostId;
import com.socialhk.social_network.model.entity.UserEntity;
import com.socialhk.social_network.model.repository.PostRepository;
import com.socialhk.social_network.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/post")
public class PostController {
    @Autowired
    private PostRepository repository;


    @GetMapping(path = "/getAll", produces = "application/json")
    @ResponseStatus(code = HttpStatus.FOUND)
    public List<PostEntity> getAllPost() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.DESC,"date");
        return repository.findAll(pageable);
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

    @PostMapping(path = "/", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostEntity addPost(@RequestBody PostEntity post) {
        return repository.save(post);
    }

    @PostMapping(path = "/edit/{oldTitle}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PostEntity editPost(@RequestBody PostEntity post, @PathVariable String oldTitle) {
        if(!post.getTitle().equals(oldTitle)){
            repository.deleteById( new PostId(post.getOwnerId(),oldTitle) );
        }
        return repository.save(post);
    }

    @DeleteMapping (path = "/delete/{id}/{title}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deletePost(@PathVariable String id,@PathVariable String title) {
         repository.deleteById(new PostId(id,title));
    }

    @GetMapping(path = "/isExist/{id}/{title}" )
    @ResponseStatus(code = HttpStatus.OK)
    public boolean isExist(@PathVariable String id,@PathVariable String title){
        if( repository.existsById( new PostId(id,title) ) ) return true;
        return false;

    }



}
