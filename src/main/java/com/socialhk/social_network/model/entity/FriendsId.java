package com.socialhk.social_network.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendsId implements Serializable {
    private String userId;
    private String friendId;
}
