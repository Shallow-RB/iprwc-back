package nl.ryanb.iprwcback.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import nl.ryanb.iprwcback.dao.UserDAO;
import nl.ryanb.iprwcback.model.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final UserDAO userDAO;

    @GetMapping("/getroles")
    public ResponseEntity<List<Role>> getRoles () {
        return ResponseEntity.ok().body(userDAO.getAllRoles());
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Role> saveRole(@ModelAttribute Role role) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/role/save").toUriString());
        return ResponseEntity.created(uri).body(userDAO.saveRole(role));
    }



    @PostMapping(value = "/addtouser")
    public ResponseEntity<?> saveRole(@RequestBody RoleToUserForm form) {

        userDAO.addRoleToUser(form.getUsername(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}

