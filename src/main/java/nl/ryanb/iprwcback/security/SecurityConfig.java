package nl.ryanb.iprwcback.security;

import lombok.RequiredArgsConstructor;
import nl.ryanb.iprwcback.filter.AuthenticationFilter;
import nl.ryanb.iprwcback.filter.AuthorizationFilter;
import nl.ryanb.iprwcback.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepo userRepo;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/user/register").permitAll();
        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers( "/user/refresh/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/product").permitAll();
        http.authorizeRequests().antMatchers(GET,"/order/**").permitAll();
        http.authorizeRequests().antMatchers(GET,"/order/getorders").hasAnyAuthority("ROLE_ADMIN");


        http.authorizeRequests().antMatchers(POST,"/order/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");
        http.authorizeRequests().antMatchers("/role/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/**/create").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/**/delete").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/**/update").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();

        http.addFilter(new AuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new AuthorizationFilter(userRepo), UsernamePasswordAuthenticationFilter.class);
    }


}
