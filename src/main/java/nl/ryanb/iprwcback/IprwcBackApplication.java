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

            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://images-ext-1.discordapp.net/external/4HzkzeyTSi7WeBO3CXvnNC4nhj4Fj8C7n-jc8cT-0VY/https/images.all-free-download.com/images/graphicwebp/x_foil_attack_craft_514618.webp"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://images-ext-2.discordapp.net/external/uT32oGnE4EuXHrmK1LFjif3tYaQB3nKhgWDhkXJg-9Y/https/images.all-free-download.com/images/graphicwebp/1_x_coffee_515935.webp"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://images-ext-2.discordapp.net/external/NSiWIz3oUOx5i2dqe7vmMHS5bisu10D6mbaIyyXgWCE/https/images.all-free-download.com/images/graphicwebp/x_shepherd_565662.webp"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
            productDAO.addProduct(new Product(null, "SPACE", 500.0, "SPACE", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));

        };
    }
}
