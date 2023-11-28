package nl.ryanb.iprwcback.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.dao.UserDAO;
import nl.ryanb.iprwcback.model.Role;
import nl.ryanb.iprwcback.model.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserDAO userDAO;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/getusers")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getting all users");
        return ResponseEntity.ok().body(userDAO.getAllUsers());
    }


    @PostMapping(value = "/register")
    public ResponseEntity<User> saveUser(@ModelAttribute User user) {
        System.out.println("HALLLOOO");
        if (userDAO.getUser(user.getUsername()) != null){
            log.info("user gevonden");
            return ResponseEntity.status(CONFLICT).build();
        }

        log.info("user niet gevonden");
        user = userDAO.saveUser(user);

        userDAO.addRoleToUser(user.getUsername(), "ROLE_USER");

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/register").toUriString());
        return ResponseEntity.created(uri).body(user);
    }


    @GetMapping("/profile")
    public ResponseEntity<User> profile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(user);
    }


    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("DitIsNietVeilig".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                User user = userDAO.getUser(username);

                if (user.getUsername().isEmpty()) {
                    throw new RuntimeException("Subject not found");
                }

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Login Error. test1 {}", exception.getMessage());
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}


