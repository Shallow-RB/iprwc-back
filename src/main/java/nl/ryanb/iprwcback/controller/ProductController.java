package nl.ryanb.iprwcback.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.dao.ProductDAO;
import nl.ryanb.iprwcback.model.Product;
import nl.ryanb.iprwcback.model.request.UpdateProduct;
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

        return ResponseEntity.ok().body(productDAO.getAllProducts());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Product> addProduct(@ModelAttribute Product product) {
        product = productDAO.addProduct(product);

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/create").toUriString());

        return ResponseEntity.created(uri).body(product);
    }

    @DeleteMapping(value = "/{id}/delete")
    public void deleteProduct(@PathVariable("id") Long id) {

        productDAO.deleteProduct(id);
    }


//    @PutMapping(value = "/{id}/update")
//    public ResponseEntity<Product> updateProduct (@PathVariable("id") Long id, @RequestBody Product product) {
//        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/update").toUriString());
//
//        return ResponseEntity.created(uri).body(this.productDAO.updateProduct(id, product));
//    }

    @PutMapping(value = "/{id}/update")
    public ResponseEntity<UpdateProduct> updateProduct(@PathVariable("id") Long id, @RequestBody UpdateProduct product) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/product/update").toUriString());

        Product storedProduct = productDAO.getProductById(id);
        storedProduct.setName(product.getName());
        storedProduct.setPrice(product.getPrice());
        storedProduct.setDescription(product.getDescription());
        storedProduct.setImageURL(product.getImageURL());

        productDAO.updateProduct(id, storedProduct);

        return ResponseEntity.created(uri).body(product);
    }

}
