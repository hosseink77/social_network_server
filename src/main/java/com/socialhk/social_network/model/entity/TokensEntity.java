package com.socialhk.social_network.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Component
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens", schema = "public")
public class TokensEntity implements Serializable {
    @Id
    private String userId;
    private String token;
}
