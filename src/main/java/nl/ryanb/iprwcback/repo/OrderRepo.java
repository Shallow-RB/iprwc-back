package nl.ryanb.iprwcback.repo;

import nl.ryanb.iprwcback.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {

    @Query(value = "SELECT * FROM orders WHERE user_id = :id ;", nativeQuery = true)
    List<Orders> findByUserId(@Param("id")Long id);
}
