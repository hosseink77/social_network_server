package com.socialhk.social_network.model.repository;

import com.socialhk.social_network.model.entity.TokensEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TokensRepository extends CrudRepository<TokensEntity,String> {
    TokensEntity findByToken(@Param("token") String token);
    boolean existsByToken(@Param("token") String token);
}
