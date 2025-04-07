package uk.lset.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import uk.lset.entities.Orders;
import uk.lset.entities.Products;
import uk.lset.exception.InsufficientStockException;
import uk.lset.exception.ItemNotFoundException;
import uk.lset.exception.QuantityBadRequestException;
import uk.lset.repository.OrdersRepository;
import uk.lset.repository.ProductRepository;
import uk.lset.request.OrderRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersService {


    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, ProductService productService, ProductRepository productRepository) {
        this.ordersRepository = ordersRepository;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    private final ProductService productService;
    private final ProductRepository productRepository;


    public Orders addNewOrder(@RequestBody OrderRequest orderRequest, String productId, int quantity) {
        if(!productRepository.existsById(productId)) {
            throw new ItemNotFoundException("Product with id " + productId + " does not exists." );
        } else if (quantity <= 1) {
            throw new QuantityBadRequestException("Quantity must be greater than zero.");
        }
        Products orderProducts = productService.getProductById(productId);

        if(orderProducts.getProductQuantity() < quantity) {
            throw new InsufficientStockException("Not enough stock available." + orderProducts.getProductQuantity() + " items left.");
        }

        Orders order = new Orders();
        order.setProductInventoryId(orderProducts.getInventoryId());
        order.setProductPrice(orderProducts.getPrice());
        order.setQuantity(quantity);
        order.setOrderValue(quantity * orderProducts.getPrice());
        order.setCoder(generateCoder());
        order.setClientName(orderRequest.getClientName());
        order.setEmail(orderRequest.getEmail());
        order.setDeliveryAddress(orderRequest.getDeliveryAddress());
        order.setStatus(orderRequest.getStatus());
        order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return ordersRepository.save(order);
    }

    private String generateCoder(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        return "O-" + timeStamp + "-" + UUID.randomUUID().toString().substring(0, 10);
    }


    @Transactional(readOnly = true)
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Orders getOrderById(String id){
        return ordersRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Order with id " + id + " does not exists."));
    }
}
