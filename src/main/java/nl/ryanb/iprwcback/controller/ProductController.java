package nl.ryanb.iprwcback.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.dao.ProductDAO;
import nl.ryanb.iprwcback.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductDAO productDAO;

    @GetMapping(value = "/getproducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("getting all products");

        return ResponseEntity.ok().body(productDAO.getAllProducts());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product){
        product = productDAO.addProduct(product);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/create").toUriString());

        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}/delete")
    public void deleteProduct(@PathVariable("id") Long id){

        productDAO.deleteProduct(id);
    }


//    @PutMapping(value = "/update")
//    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
//
//    }

}
