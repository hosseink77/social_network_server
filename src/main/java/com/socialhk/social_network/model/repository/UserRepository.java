package com.socialhk.social_network.model.repository;

import com.socialhk.social_network.model.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
//@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface UserRepository extends CrudRepository<UserEntity,String> {
    UserEntity findByUserName(@Param("id") String id);
    boolean existsByUserName(@Param("id") String id);
}

