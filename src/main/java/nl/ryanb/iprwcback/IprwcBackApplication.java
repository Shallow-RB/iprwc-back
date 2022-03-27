package nl.ryanb.iprwcback;

import nl.ryanb.iprwcback.dao.ProductDAO;
import nl.ryanb.iprwcback.dao.UserDAO;
import nl.ryanb.iprwcback.model.Product;
import nl.ryanb.iprwcback.model.Role;
import nl.ryanb.iprwcback.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200")
public class IprwcBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(IprwcBackApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer getCorsConfiguration(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }
        };
    }

    @Bean
    CommandLineRunner run(UserDAO userDAO, ProductDAO productDAO) {
        return args -> {
            userDAO.saveRole(new Role(null, "ROLE_USER"));
            userDAO.saveRole(new Role(null, "ROLE_ADMIN"));

            userDAO.saveUser(new User(null, "Ryan Bhola", "pewpew", "1234", new ArrayList<>()));
            userDAO.saveUser(new User(null, "Jan Jan", "Jan1", "1234", new ArrayList<>()));
            userDAO.saveUser(new User(null, "Bob Bob", "Bob1", "1234", new ArrayList<>()));

            userDAO.addRoleToUser("pewpew", "ROLE_ADMIN");
            userDAO.addRoleToUser("pewpew", "ROLE_USER");
            userDAO.addRoleToUser("Bob1", "ROLE_USER");
            userDAO.addRoleToUser("Jan1", "ROLE_USER");

            productDAO.addProduct(new Product(null, "shoes", 100.0, "size 41 shoes", "https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/d5f168f9-f953-4419-ac7a-f0def128176e/renew-run-2-mens-road-running-shoe-8WSLZf.png"));
            productDAO.addProduct(new Product(null, "hoodie", 50.0, "very comfy hoodie yes oui", "https://img01.ztat.net/article/spp-media-p1/200ef2de50de3345a22d435e87119f17/fe0e96c6eabb4c51841f82342129aa1d.jpg?imwidth=1800&filter=packshot"));
            productDAO.addProduct(new Product(null, "pants", 40.0, "jogger pants yes blabla", "https://www.helikon-tex.com/media/catalog/product/cache/6/image/9df78eab33525d08d6e5fb8d27136e95/s/p/sp-pgm-dc-11.jpg"));

        };
    }
}
