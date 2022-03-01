package nl.ryanb.iprwcback.repo;

import nl.ryanb.iprwcback.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


}
