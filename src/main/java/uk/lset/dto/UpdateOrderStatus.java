package uk.lset.dto;


import lombok.Getter;
import lombok.Setter;
import uk.lset.entities.OrderStatus;

@Getter @Setter
public class UpdateOrderStatus {

    private OrderStatus status;
}
