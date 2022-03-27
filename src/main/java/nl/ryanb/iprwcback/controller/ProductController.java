package nl.ryanb.iprwcback.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.dao.ProductDAO;
import nl.ryanb.iprwcback.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductDAO productDAO;

    @GetMapping(value = "/getproducts")
    public ResponseEntity<List<Product>> getAllProducts() {

        return ResponseEntity.ok().body(productDAO.getAllProducts());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/create")
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/create").toUriString());

        return ResponseEntity.created(uri).body(productDAO.addProduct(product));
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {

        productDAO.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping(value = "/{id}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/update").toUriString());

        Optional<Product> optionalProduct = this.productDAO.findProductById(id);
        if (optionalProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.created(uri).body(this.productDAO.updateProduct(id, product));
    }

}
