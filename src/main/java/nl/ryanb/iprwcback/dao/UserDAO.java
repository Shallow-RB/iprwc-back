package nl.ryanb.iprwcback.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.model.Role;
import nl.ryanb.iprwcback.model.User;
import nl.ryanb.iprwcback.repo.RoleRepo;
import nl.ryanb.iprwcback.repo.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserDAO implements UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepo.findByUsername(username);

        if (user == null) {
            log.error("User not found in db");
            throw new UsernameNotFoundException("User not found in db");
        } else {
            log.info("User {} found in db", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    public User getUser(String username) {
        log.info("Getting user {}", username);
        return this.userRepo.findByUsername(username);
    }

    public List<User> getAllUsers() {
        log.info("Getting all users");

        return this.userRepo.findAll();
    }

    public User saveUser(User user){
        log.info("Saving user {} to db", user.getName());

        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return this.userRepo.save(user);
    }

    public List<Role> getAllRoles() {
        log.info("Getting all roles");

        return this.roleRepo.findAll();
    }

    public Role saveRole(Role role) {
        log.info("Saving new role {} to db", role.getName());

        return this.roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}...", roleName, username);

        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);

        if (user.getUsername().isEmpty() || role.getName().isEmpty()) {
            log.warn("Either user {} or role {} is not in the database", username, roleName);
            return;
        }

        user.getRoles().add(role);
        log.info("Sucessfully added role {} to user {} !", roleName, username);
    }




}
