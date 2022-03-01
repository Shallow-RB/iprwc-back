package nl.ryanb.iprwcback.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.model.Product;
import nl.ryanb.iprwcback.repo.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductDAO {

    private final ProductRepo productRepo;

    public List<Product> getAllProducts() {
        log.info("Getting all products.");

        return this.productRepo.findAll();
    }

    public Product getProductById(Long id) {
        log.info("Getting product with id: {}", id);

        return this.productRepo.getById(id);
    }

    public Product addProduct(Product product) {
        log.info("Adding new product {} to db", product.getName());

        return this.productRepo.save(product);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: ({}) from db", id);

        boolean exists = productRepo.existsById(id);
        if (!exists){
            throw new IllegalStateException("product with id: " + id + " does not exist");
        }

        productRepo.deleteById(id);
    }

//    public void save(Product product){
//        log.info("Updating product {} with id {}", product.getName(), product.getId());
//
//        this.productRepo.save(product);
//    }

}
