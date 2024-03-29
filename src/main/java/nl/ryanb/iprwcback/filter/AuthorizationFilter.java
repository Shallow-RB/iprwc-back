// package nl.ryanb.iprwcback.filter;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.JWTVerifier;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.interfaces.DecodedJWT;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import nl.ryanb.iprwcback.model.User;
// import nl.ryanb.iprwcback.repo.UserRepo;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.filter.OncePerRequestFilter;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.HashMap;
// import java.util.Map;

// import static java.util.Arrays.stream;
// import static org.springframework.http.HttpHeaders.AUTHORIZATION;
// import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

// @RequiredArgsConstructor
// @Slf4j
// // @CrossOrigin(origins = "http://localhost:4200")
// public class AuthorizationFilter extends OncePerRequestFilter {

//     private final UserRepo userRepo;

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         if (request.getServletPath().equals("/login") ||
//             request.getServletPath().equals("/user/refresh")
//         ) {
//             filterChain.doFilter(request, response);
//             return;
//         }

//         String authorizationHeader = request.getHeader(AUTHORIZATION);

//         if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

//             try {
//                 String token = authorizationHeader.substring("Bearer ".length());
//                 Algorithm algorithm = Algorithm.HMAC256("DitIsNietVeilig".getBytes());

//                 JWTVerifier verifier = JWT.require(algorithm).build();
//                 DecodedJWT decodedJWT = verifier.verify(token);

//                 String username = decodedJWT.getSubject();
//                 String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

//                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                 stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

//                 User user = userRepo.findByUsername(username);

//                 if (user.getUsername().isEmpty()) {
//                     throw new BadCredentialsException("User not found in db");
//                 }

//                 UsernamePasswordAuthenticationToken authenticationToken =
//                         new UsernamePasswordAuthenticationToken(username, null, authorities);

//                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);

//             } catch (Exception exception) {
//                 log.error("Login Error. test2 {}", exception.getMessage());
//                 response.setHeader("error", exception.getMessage());
//                 response.setStatus(403);

//                 Map<String, String> error = new HashMap<>();
//                 error.put("error_message", exception.getMessage());

//                 response.setContentType(APPLICATION_JSON_VALUE);
//                 new ObjectMapper().writeValue(response.getOutputStream(), error);
//             }
//         }
//         filterChain.doFilter(request, response);

//     }
// }
