package nl.ryanb.iprwcback.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.ryanb.iprwcback.model.Orders;
import nl.ryanb.iprwcback.repo.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderDAO {

    private final OrderRepo orderRepo;

    public List<Orders> getAllOrders() {
        log.info("Getting all orders.");

        return this.orderRepo.findAll();
    }

    public Orders createOrder(Orders order) {
        log.info("creating order for {} to db.", order.getUserId());

        return this.orderRepo.save(order);
    }

    public List<Orders> getOrdersByUserId(Long id) {
        log.info("getting orders for {} to db.", id);
        return this.orderRepo.findByUserId(id);
    }


}
