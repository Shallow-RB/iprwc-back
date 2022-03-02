package nl.ryanb.iprwcback;

import nl.ryanb.iprwcback.dao.ProductDAO;
import nl.ryanb.iprwcback.model.Product;
import nl.ryanb.iprwcback.model.Role;
import nl.ryanb.iprwcback.model.User;
import nl.ryanb.iprwcback.dao.UserDAO;
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
    CommandLineRunner run(UserDAO userDAO, ProductDAO productDAO) {
        return args -> {
            userDAO.saveRole(new Role(null, "ROLE_USER"));
            userDAO.saveRole(new Role(null, "ROLE_ADMIN"));

            userDAO.registerUser(new User(null, "Ryan Bhola", "Ryan", "1234", new ArrayList<>()));
            userDAO.registerUser(new User(null, "Jan Jan", "Jan1", "1234", new ArrayList<>()));
            userDAO.registerUser(new User(null, "Bob Bob", "Bob1", "1234", new ArrayList<>()));

            userDAO.addRoleToUser("Ryan", "ROLE_ADMIN");
            userDAO.addRoleToUser("Ryan", "ROLE_USER");
            userDAO.addRoleToUser("Bob1", "ROLE_USER");
            userDAO.addRoleToUser("Jan1", "ROLE_USER");

            productDAO.addProduct(new Product(null, "shoes", 100.0, "size 41 shoes", "linkje"));
            productDAO.addProduct(new Product(null, "hoodie", 50.0, "very comfy hoodie yes oui", "linkje"));
            productDAO.addProduct(new Product(null, "pants", 40.0, "jogger pants yes blabla", "linkje"));

        };
    }
}
