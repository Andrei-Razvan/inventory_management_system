package uk.lset.service;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.lset.entities.Products;
import uk.lset.repository.ProductRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class QRCodeService {
    private final ProductRepository productRepository;

    @Autowired
    public QRCodeService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public byte[] generateQRCode(Products products) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();//initializing QRCodeWritter . QRCodeWritter is a class from the ZXing library to generate QR codes
        products = productRepository.findById(products.getProductId()).orElse(null);
        if (products == null) {
            throw new IllegalArgumentException("Product not found");
        }
        BitMatrix bitMatrix = qrCodeWriter.encode(products.toString(), BarcodeFormat.QR_CODE,250,250);
        BufferedImage bufferedImage = new BufferedImage(250,250, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics().fillRect(0,0,250,250);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < 250; i++) {
            for (int j = 0; j < 250; j++) {
               if(bitMatrix.get(i,j)) {
                   graphics.fillRect(i,j,1,1);
               }
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", outputStream);
        return outputStream.toByteArray();
    }
}
