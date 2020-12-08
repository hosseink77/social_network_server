package com.socialhk.social_network.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Component
@Table(name = "user", schema = "public")
public class UserEntity implements Serializable {
    private String userName;
    @Id
    private String uuid;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private byte[] image;
    private boolean isMan;
    private  String status;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dateJoin;

    @PrePersist
    public void prePersist() {
        if(dateJoin == null)
            System.out.println("prePersist");
        dateJoin = new Date();
    }

}
