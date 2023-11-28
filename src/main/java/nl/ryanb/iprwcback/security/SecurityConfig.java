package nl.ryanb.iprwcback.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        proxyTargetClass = true)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // private final UserRepo userRepo;
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
        http.cors(withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // http
        //         .authorizeRequests(withDefaults())
        //         .formLogin(withDefaults())
        //         .httpBasic(withDefaults());

        // http.authorizeRequests(requests -> requests.antMatchers("/login").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers("/register").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers("/user/register").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers("/user/refresh/**").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers(GET, "/product").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers(GET, "/order/**").permitAll());
        // http.authorizeRequests(requests -> requests.antMatchers(GET, "/order/getorders").hasAnyAuthority("ROLE_ADMIN"));


        // http.authorizeRequests(requests -> requests.antMatchers(POST, "/order/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN"));
        // http.authorizeRequests(requests -> requests.antMatchers("/role/**").hasAuthority("ROLE_ADMIN"));
        // http.authorizeRequests(requests -> requests.antMatchers(POST, "/**/create").hasAuthority("ROLE_ADMIN"));
        // http.authorizeRequests(requests -> requests.antMatchers(DELETE, "/**/delete").hasAuthority("ROLE_ADMIN"));
        // http.authorizeRequests(requests -> requests.antMatchers(PUT, "/**/update").hasAuthority("ROLE_ADMIN"));
        http.authorizeRequests(requests -> requests.anyRequest().permitAll());

        // http.addFilter(new AuthenticationFilter(authenticationManager()));
        // http.addFilterBefore(new AuthorizationFilter(userRepo), UsernamePasswordAuthenticationFilter.class);
    }


}
