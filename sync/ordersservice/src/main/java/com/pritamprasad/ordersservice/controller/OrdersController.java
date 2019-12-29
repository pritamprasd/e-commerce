package com.pritamprasad.ordersservice.controller;

import com.pritamprasad.ordersservice.exception.OrderNotFoundException;
import com.pritamprasad.ordersservice.models.Cart;
import com.pritamprasad.ordersservice.models.Order;
import com.pritamprasad.ordersservice.repo.OrdersRepository;
import com.pritamprasad.ordersservice.service.CartServiceConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
public class OrdersController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CartServiceConnector cartServiceConnector;

    @CrossOrigin(origins = "*")
    @GetMapping("/orders/{orderid}")
    public ResponseEntity<Order> getByOrderId(@PathVariable("orderid") UUID orderId) {
        Order o = ordersRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        return ResponseEntity.ok(o);
    }

    //TODO: Discuss API URI
    @CrossOrigin(origins = "*")
    @GetMapping("/orders/user/{userid}")
    public ResponseEntity<List<Order>> getAllOrdersByUserId(@PathVariable("userid") UUID userId) {
        List<Order> orders = ordersRepository.findAllByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order, HttpServletRequest httpServletRequest) {
        Long time = System.currentTimeMillis();
        order.setCreatedTimestamp(time);
        order.setModifiedTimestamp(time);
        Cart c = cartServiceConnector.clearCart(order.getUserId(),httpServletRequest.getHeader("token"));
        order.setOrderTotal(c.getCartTotal());
        return ResponseEntity.ok(ordersRepository.save(order));
    }
}
