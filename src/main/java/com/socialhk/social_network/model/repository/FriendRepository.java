package com.socialhk.social_network.model.repository;

import com.socialhk.social_network.model.entity.FriendsEntity;
import com.socialhk.social_network.model.entity.FriendsId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends CrudRepository<FriendsEntity, FriendsId> {
    List<FriendsEntity> findByUserId (@Param("id") String id);
}
