package uk.lset.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}

