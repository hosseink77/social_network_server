package com.socialhk.social_network.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FriendsId.class)
@Table(name = "friends")
public class FriendsEntity implements Serializable {
    @Id
    private String user;

    @Id
    private String friend;
}
