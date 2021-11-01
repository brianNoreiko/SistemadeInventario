package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.models.responses.Response;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utils.EntityResponse;
import utils.EntityURLBuilder;

import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

    @Autowired
    ProductService productService;


    @PostMapping
    public ResponseEntity<Response> addProduct(@RequestBody Product product) {
        Product productAdded = productService.add(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .location(EntityURLBuilder.buildURL("products",productAdded.getId()))
                .body(EntityResponse.messageResponse("Product created successfully"));
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@RequestParam(value = "id") Integer id){
        Product product = productService.getById(id);
        return ResponseEntity.ok(product);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteById(@RequestParam(value = "id") Integer id){
        productService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
