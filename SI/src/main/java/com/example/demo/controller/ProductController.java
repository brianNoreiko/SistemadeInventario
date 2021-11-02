package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.models.Provider;
import com.example.demo.models.dto.ProductDTO;
import com.example.demo.models.responses.Response;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EntityResponse;
import utils.EntityURLBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.POST, RequestMethod.GET})
@RestController
@RequestMapping("/products")

public class ProductController {

    final ProductService productService;
    final ProviderService providerService;

    @Autowired
    public ProductController(ProductService productService, ProviderService providerService) {
        this.productService = productService;
        this.providerService = providerService;
    }

    @PostMapping
    public ResponseEntity<Response> addProduct(@RequestBody ProductDTO product) {
        Provider prov = providerService.getById(product.getProvider());
        Product newProd = Product.builder()
                .model(product.getModel())
                .brand(product.getBrand())
                .barcode(product.getBarcode())
                .category(product.getCategory())
                .description(product.getDescription())
                .provider(prov)
                .units(0)
                .price(product.getPrice()).build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(EntityURLBuilder.buildURL("products", productService.add(newProd).getId()))
                .body(EntityResponse.messageResponse("Product created successfully"));
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Integer id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<Product> getProductByBarcode(@PathVariable(value = "barcode") String barcode) {
        Product product = productService.getByBarcode(barcode);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@PathVariable(value = "id") Integer id) {
        productService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
