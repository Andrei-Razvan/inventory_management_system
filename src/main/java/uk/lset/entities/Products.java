package uk.lset.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;


@Entity
@Table(name = "products")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter @Setter
public class Products {

    @Id
    @UuidGenerator
    @Column(name = "product_id",updatable = false, nullable = false)
    private String productId;

    @Column(name = "product_name")
    @NotBlank(message = "Product name is required")
    private String productName;

    @Column(name = "product_description")
    @NotBlank(message = "Product description is required")
    private String productDescription;

    @Column(name = "price")
    @Min(value = 0, message = "Price must be at least 0")
    private double price;

    @Column(name = "quantity_available")
    @Min(value = 0, message = "Quantity must be at least 0")
    private int productQuantity;

    @Column(name = "inventory_id" , unique = true)
    @NotBlank(message = "Inventory id is required")
    private String inventoryId;

    @Column(name = "category")
    @NotBlank(message = "Category is required")
    private String category;

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", price=" + price +
                ", productQuantity=" + productQuantity +
                ", inventoryId='" + inventoryId + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
