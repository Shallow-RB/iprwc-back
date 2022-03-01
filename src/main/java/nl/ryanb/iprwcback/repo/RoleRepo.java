package nl.ryanb.iprwcback.repo;

import nl.ryanb.iprwcback.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String username);
}
