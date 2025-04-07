package uk.lset.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.lset.entities.Orders;
import uk.lset.request.OrderRequest;
import uk.lset.service.OrdersService;

import java.util.List;

@RestController
@RequestMapping()
public class OrdersController {


    private OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/orders/addNewOrder/{productId}")
    public ResponseEntity<Orders> addNewOrder(@RequestBody OrderRequest orderRequest, @PathVariable String productId, @RequestParam int quantity) {
        Orders newOrder = ordersService.addNewOrder(orderRequest, productId, quantity);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("/orders/allOrders")
    public ResponseEntity<List<Orders>> getAllOrders() {
        List<Orders> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable String id) {
        Orders orders = ordersService.getOrderById(id);
        return ResponseEntity.ok(orders);

    }
}

