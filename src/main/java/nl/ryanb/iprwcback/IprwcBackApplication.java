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

            userDAO.saveUser(new User(null, "Ryan Ryan", "ryancb", "1234", new ArrayList<>()));
            userDAO.saveUser(new User(null, "Jan Jan", "Jan1", "1234", new ArrayList<>()));
            userDAO.saveUser(new User(null, "Bob Bob", "Bob1", "1234", new ArrayList<>()));

            userDAO.addRoleToUser("ryancb", "ROLE_ADMIN");
            userDAO.addRoleToUser("ryancb", "ROLE_USER");
            userDAO.addRoleToUser("Bob1", "ROLE_USER");
            userDAO.addRoleToUser("Jan1", "ROLE_USER");

           productDAO.addProduct(new Product(null, "COSMOS #1", 999.0, "COSMOS #1", "https://www.nlspacecampus.eu/cache/3/1920x1080/mob-shutterstock-481251031-20210610135721_1920x1080.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #2", 449.0, "COSMOS #2", "https://images.unsplash.com/photo-1462331940025-496dfbfc7564?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1211&q=80"));
           productDAO.addProduct(new Product(null, "COSMOS #3", 249.0, "COSMOS #3", "https://wallpaper.dog/large/20530574.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #4", 689.0, "COSMOS #4", "https://knowablemagazine.org/pb-assets/knowable-assets/article-assets/twitter/10.1146/knowable-082219-1-1200x630-1606933760223.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #5", 739.0, "COSMOS #5", "https://wallpaperaccess.com/full/238012.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #6", 199.0, "COSMOS #6", "https://wallpaper-mania.com/wp-content/uploads/2018/09/High_resolution_wallpaper_background_ID_77701906877.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #7", 99.0, "COSMOS #7", "https://wallpaperaccess.com/full/17520.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #8", 599.0, "COSMOS #8", "https://images.unsplash.com/photo-1465101162946-4377e57745c3?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1178&q=80"));
           productDAO.addProduct(new Product(null, "COSMOS #9", 459.0, "COSMOS #9", "https://images.unsplash.com/photo-1518066000714-58c45f1a2c0a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
           productDAO.addProduct(new Product(null, "COSMOS #10", 689.0, "COSMOS #10", "https://wallpapers-hub.art/wallpaper-images/44211.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #11", 349.0, "COSMOS #11", "https://wallpaperaccess.com/full/4710671.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #12", 829.0, "COSMOS #12", "https://r1.ilikewallpaper.net/ipad-pro-wallpapers/download/36470/Star-nebula-glow-ipad-pro-wallpaper-ilikewallpaper_com.jpg"));
           productDAO.addProduct(new Product(null, "COSMOS #13", 719.0, "COSMOS #13", "https://www.pixel4k.com/wp-content/uploads/2021/08/nebula-stars-space-4k_1629255910.jpg"));

        };
    }
}
