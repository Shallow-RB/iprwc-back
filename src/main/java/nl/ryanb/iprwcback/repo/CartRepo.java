package nl.ryanb.iprwcback.repo;

import nl.ryanb.iprwcback.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Long> {
}
