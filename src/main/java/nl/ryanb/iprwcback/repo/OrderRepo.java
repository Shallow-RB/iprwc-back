package nl.ryanb.iprwcback.repo;

import nl.ryanb.iprwcback.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Long> {

}
