package uk.lset.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

   @ExceptionHandler(InsufficientStockException.class)
   public ResponseEntity<String> handleInsufficientStock(InsufficientStockException exception) {
       return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
   }


   @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ItemNotFoundException exception) {
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
   }

   @ExceptionHandler(QuantityBadRequestException.class)
    public ResponseEntity<String> handleQuantityBadRequest(QuantityBadRequestException exception){
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
   }

   @ExceptionHandler (HttpMessageNotReadableException.class)
    public ResponseEntity<String>handleStatusBadRequest(HttpMessageNotReadableException exception) {
       if (exception.getMessage() != null && exception.getMessage().contains("OrderStatus")) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not one of the values accepted for Enum class: [ACCEPTED,PROCESSING,SHIPPED,DELIVERED,CANCELLED]");
       }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
   }
}

