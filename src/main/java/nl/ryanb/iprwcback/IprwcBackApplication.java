package nl.ryanb.iprwcback;

import nl.ryanb.iprwcback.model.Role;
import nl.ryanb.iprwcback.model.User;
import nl.ryanb.iprwcback.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class IprwcBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(IprwcBackApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));

            userService.saveUser(new User(null, "Ryan Bhola", "Ryan", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Jan Jan", "Jan1", "1234", new ArrayList<>()));
            userService.saveUser(new User(null, "Bob Bob", "Bob1", "1234", new ArrayList<>()));

            userService.addRoleToUser("Ryan", "ROLE_ADMIN");
            userService.addRoleToUser("Ryan", "ROLE_USER");
            userService.addRoleToUser("Jan1", "ROLE_USER");
            userService.addRoleToUser("Bob1", "ROLE_USER");
        };
    }
}
