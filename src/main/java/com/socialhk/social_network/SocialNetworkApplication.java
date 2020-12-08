package com.socialhk.social_network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SocialNetworkApplication {

//    @Autowired
//    static User user;
    //static UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(SocialNetworkApplication.class, args);

        // System.out.println("******************"+repository.findById("hossein").get().getPhoneNumber());
//        System.out.println("salllllllllllllllaaaaaaaaaaaaaaaaaaaaammmm");
//        User user = new User();
//        user.setId("hossein");
//        System.out.println(new Permission().PermissionLogin(user));
    }

}
