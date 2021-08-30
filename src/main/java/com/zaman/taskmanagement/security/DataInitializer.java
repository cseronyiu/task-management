package com.zaman.taskmanagement.security;//package com.jonak.rnd.security;


import com.zaman.taskmanagement.entity.Authority;
import com.zaman.taskmanagement.entity.Role;
import com.zaman.taskmanagement.entity.User;
import com.zaman.taskmanagement.repository.AuthorityRepository;
import com.zaman.taskmanagement.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Log4j2
public class DataInitializer implements CommandLineRunner {
    //...
    @Autowired
    UserRepository users;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (users.findAll().isEmpty()) {

            Role role1 = new Role("USER", "user role");
            Role role2 = new Role("ADMIN", "admin role");

            Authority userAuth1 = new Authority("CREATE_PROJECT", role1);
            Authority userAuth2 = new Authority("GET_ALL_PROJECT", role1);
            Authority userAuth3 = new Authority("DELETE_PROJECT", role1);
            Authority userAuth4 = new Authority("CREATE_TASK", role1);
            Authority userAuth5 = new Authority("EDIT_TASK", role1);
            Authority userAuth10 = new Authority("GET_TASK_LIST", role1);
            Authority userAuth6 = new Authority("GET_TASK", role1);
            Authority userAuth7 = new Authority("TASKS_BY_PROJECT", role1);
            Authority userAuth8 = new Authority("EXPIRED_TASKS", role1);
            Authority userAuth9 = new Authority("TASKS_BY_STATUS", role1);

            Authority adminAuth1 = new Authority("CREATE_PROJECT", role2);
            Authority adminAuth2 = new Authority("GET_ALL_PROJECT", role2);
            Authority adminAuth3 = new Authority("DELETE_PROJECT", role2);
            Authority adminAuth4 = new Authority("CREATE_TASK", role2);
            Authority adminAuth5 = new Authority("EDIT_TASK", role2);
            Authority adminAuth6 = new Authority("GET_TASK", role2);
            Authority adminAuth12 = new Authority("GET_TASK_LIST", role2);
            Authority adminAuth7 = new Authority("TASKS_BY_PROJECT", role2);
            Authority adminAuth8 = new Authority("EXPIRED_TASKS", role2);
            Authority adminAuth9 = new Authority("TASKS_BY_STATUS", role2);
            Authority adminAuth10 = new Authority("ALL_TASKS_BY_USER", role2);
            Authority adminAuth11 = new Authority("ALL_PROJECTS_BY_USER", role2);



            this.users.save(new User("user", this.passwordEncoder.encode("password"), role1));
            this.users.save(new User("admin", this.passwordEncoder.encode("password"), role2));

            authorityRepository.save(userAuth1 );
            authorityRepository.save(userAuth2 );
            authorityRepository.save(userAuth3 );
            authorityRepository.save(userAuth4 );
            authorityRepository.save(userAuth5 );
            authorityRepository.save(userAuth6 );
            authorityRepository.save(userAuth7 );
            authorityRepository.save(userAuth8 );
            authorityRepository.save(userAuth9 );
            authorityRepository.save(userAuth10 );
            authorityRepository.save(adminAuth1 );
            authorityRepository.save(adminAuth2 );
            authorityRepository.save(adminAuth3 );
            authorityRepository.save(adminAuth4 );
            authorityRepository.save(adminAuth5 );
            authorityRepository.save(adminAuth6 );
            authorityRepository.save(adminAuth7 );
            authorityRepository.save(adminAuth8 );
            authorityRepository.save(adminAuth9 );
            authorityRepository.save(adminAuth10);
            authorityRepository.save(adminAuth11);
            authorityRepository.save(adminAuth12);

        }
    }
}
