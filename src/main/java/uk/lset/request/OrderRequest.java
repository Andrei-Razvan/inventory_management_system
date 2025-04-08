package uk.lset.request;


import lombok.Getter;
import lombok.Setter;
import uk.lset.entities.OrderStatus;

@Getter
@Setter
public class OrderRequest {

    private String id;
    private String clientName;
    private String email;
    private String deliveryAddress;
    private OrderStatus status;
}
