package uk.lset.controller;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.lset.entities.Products;
import uk.lset.service.ProductService;
import uk.lset.service.QRCodeService;

import java.io.IOException;
import java.util.Optional;

@RestController
public class QRCodeController {

    private final QRCodeService qrCodeService;
    private final ProductService productService;

    @Autowired
    public QRCodeController(QRCodeService qrCodeService, ProductService productService) {
        this.qrCodeService = qrCodeService;
        this.productService = productService;
    }

    @GetMapping(path = "/generateQRCode")
    public ResponseEntity<byte[]> generateQRCode(@RequestParam String text) throws IOException, WriterException {
        Optional<Products> product = productService.getProductByProductId(text);
        if(product.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        System.out.println("Product found: " + product.get());
        byte[] qrCode = qrCodeService.generateQRCode(product.get());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(qrCode);
    }

}
