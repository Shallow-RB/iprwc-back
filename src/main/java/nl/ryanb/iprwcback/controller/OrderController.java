package nl.ryanb.iprwcback.controller;

import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.dao.OrderDAO;
import nl.ryanb.iprwcback.dto.OrderDTO;
import nl.ryanb.iprwcback.model.Orders;
import nl.ryanb.iprwcback.model.Product;
import nl.ryanb.iprwcback.model.User;
import nl.ryanb.iprwcback.repo.ProductRepo;
import nl.ryanb.iprwcback.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
@CrossOrigin
public class OrderController {


    OrderDAO orderDAO;
    UserRepo userRepo;
    ProductRepo productRepo;

    @Autowired
    public OrderController(OrderDAO orderDAO, UserRepo userRepo, ProductRepo productRepo) {
        this.orderDAO = orderDAO;
        this.userRepo = userRepo;
        this.productRepo = productRepo;
    }

    @GetMapping(value = "/getorders")
    public ResponseEntity<List<Orders>> getAllOrders() {

        return ResponseEntity.ok().body(orderDAO.getAllOrders());
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrdersById() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        User user = this.userRepo.findByUsername(username);

        List<Orders> testlist = orderDAO.getOrdersByUserId(user.getId());
        System.out.println(testlist);
        return ResponseEntity.ok().body(testlist);
    }


    @PostMapping()
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/order/create").toUriString());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        User user = this.userRepo.findByUsername(username);

        Orders order = new Orders();

        order.setUserId(user.getId());

        Collection<Product> productList = new ArrayList<>();

        for (Long productid : orderDTO.getProducts()) {
            Product i = productRepo.findById(productid).get();
            productList.add(i);
        }
        System.out.println(productList);

        order.setProducts(productList);

        return ResponseEntity.created(uri).body(this.orderDAO.createOrder(order));
    }


}
