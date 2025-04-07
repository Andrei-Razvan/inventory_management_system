package uk.lset.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Validated
public class Orders {

    @Id
    @UuidGenerator()
    @Column(name = "order_id",updatable = false, nullable = false)
    private String orderId;

    @Column(name = "client_name")
    @NotBlank(message = "Name is required")
    private String clientName;

    @Column(name = "email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    private String email;

    @Column(name = "inventory_id")
    @NotBlank(message  = "Inventory id is required")
    private String productInventoryId;

    @Column(name = "quantity")
    @Min(value = 1, message = "Order quantity must be 1")
    private int quantity;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "order_value")
    private double orderValue;

    @Column(name = "delivery_address")
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @Column(name = "status")
    private String status;

    @Column(name = "order_date")
    private String orderDate;

    @Column(name = "coder")
    private String coder;


}
