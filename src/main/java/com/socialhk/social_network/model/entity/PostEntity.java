package com.socialhk.social_network.model.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Component
@Data
@Table(name = "post" , schema = "public")
@IdClass(value = PostId.class)
public class PostEntity implements Serializable {
    @Id
    private String ownerId;
    @Id
    private String title;
    private String tags;
    private String postText;
    private byte[] image;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date date;


    @PrePersist
    public void prePersist() {
        if(date == null)
            System.out.println("prePersist");
            date = new Date();
    }

}
