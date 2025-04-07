package uk.lset.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private String clientName;
    private String email;
    private String deliveryAddress;
    private String status;
}
