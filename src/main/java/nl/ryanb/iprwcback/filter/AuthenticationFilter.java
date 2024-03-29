// package nl.ryanb.iprwcback.filter;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.bind.annotation.CrossOrigin;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.stream.Collectors;

// import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// @Slf4j
// // @CrossOrigin(origins = "http://localhost:4200")
// public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//     private final AuthenticationManager authenticationManager;

//     public AuthenticationFilter(AuthenticationManager authenticationManager) {
//         this.authenticationManager = authenticationManager;
//     }

//     @Override
//     public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//         String username = request.getParameter("username");
//         String password = request.getParameter("password");

//         log.info("Username is: {}", username);
//         log.info("Password is: {}", password);

//         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

//         return authenticationManager.authenticate(authenticationToken);
//     }

//     @Override
//     protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
//         User user = (User) authResult.getPrincipal();

//         log.info(user.toString());

//         Algorithm algorithm = Algorithm.HMAC256("DitIsNietVeilig".getBytes());
//         String access_token = JWT.create()
//                 .withSubject(user.getUsername())
//                 .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
//                 .withIssuer(request.getRequestURL().toString())
//                 .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                 .sign(algorithm);

//         String refresh_token = JWT.create()
//                 .withSubject(user.getUsername())
//                 .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
//                 .withIssuer(request.getRequestURL().toString())
//                 .sign(algorithm);

//         Map<String, String> tokens = new HashMap<>();
//         tokens.put("access_token", access_token);
//         tokens.put("refresh_token", refresh_token);

//         response.setContentType(APPLICATION_JSON_VALUE);

//         new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//     }

//     @Override
//     protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
// //        log.error(failed.getMessage());
// //        failed.printStackTrace();

//         super.unsuccessfulAuthentication(request, response, failed);
//     }


// }