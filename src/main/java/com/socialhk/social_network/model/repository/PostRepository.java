package com.socialhk.social_network.model.repository;

import com.socialhk.social_network.model.entity.PostEntity;
import com.socialhk.social_network.model.entity.PostId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, PostId> {
    List<PostEntity> findByOwnerId (@Param("id") String id);

    @Query(value = "SELECT TOP 3 * FROM POST",nativeQuery = true)
    List<PostEntity> findTop10 ();
}
