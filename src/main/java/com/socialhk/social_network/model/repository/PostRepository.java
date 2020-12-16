package com.socialhk.social_network.model.repository;

import com.socialhk.social_network.model.entity.PostEntity;
import com.socialhk.social_network.model.entity.PostId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, PostId> {
    List<PostEntity> findByOwnerId (@Param("id") String id);

//    @Query(value = "SELECT * FROM POST where owner_id = :id ",nativeQuery = true)
    List<PostEntity> findAll(Pageable pageable);

    List<PostEntity> findByOwnerIdIn(@Param("id") List<String> id , Pageable pageable);
}
