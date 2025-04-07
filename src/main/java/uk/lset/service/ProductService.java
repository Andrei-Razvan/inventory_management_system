package uk.lset.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import uk.lset.dto.ProductDTO;
import uk.lset.entities.Products;
import uk.lset.exception.ItemNotFoundException;
import uk.lset.repository.ProductRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Products addNewProduct(Products products) {
        if(productRepository.existsByInventoryId(products.getInventoryId())) {
            throw new RuntimeException("Inventory ID already exists.");
        }

        return productRepository.save(products);
    }


    @Transactional(readOnly = true)
    public List<Products> getAllProducts(){

        return productRepository.findAll();
    }

	/*public Product getProductById(String id) {
		return productRepository.findById(id).orElse(null);
	}*/


    //Service for sorted products and pagination
    @Transactional(readOnly = true)
    public Page<Products> getAllProductsSorted(int page, int size, Sort sort, String category) {
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable);
    }


    public Optional<Products> getProductByProductId(String id) {
        return productRepository.findById(id);
    }

    public Products getProductById(String id) {

        return productRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Product not found! " + "Check id: " + id ));

    }
    @Transactional
    public Products updateProduct(Products products) {
        if(!productRepository.existsByProductId(products.getProductId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with id " + products.getProductId() + " does not exists." );
        }
        return productRepository.save(products);
    }

    @Transactional
    public void deleteProduct (String id) {
        if(productRepository.existsByProductId(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with id " + id + " does not exists." );
        }
        productRepository.deleteById(id);
    }


    public ProductDTO getProductStock(String id) {
        Products products = productRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with id " + id + " does not exists."));
        return new ProductDTO(
                products.getProductId(),
                products.getProductName(),
                products.getProductQuantity());
    }

    public Products updateStock(String productId, int newQuantity) {
        Products products = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with id " + productId + " does not exists."));
        products.setProductQuantity(newQuantity);
        return productRepository.save(products);
    }

    public Products addStock(String productId, int quantityToAdd) {
        Products products = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Product with id " + productId + " does not exists."));
        products.setProductQuantity(products.getProductQuantity() + quantityToAdd);
        return productRepository.save(products);
    }


}
