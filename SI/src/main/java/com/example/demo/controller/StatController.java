package com.example.demo.controller;

import com.example.demo.models.Product;
import com.example.demo.models.Stat;
import com.example.demo.service.ProductService;
import com.example.demo.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET})
@RestController
@RequestMapping("/products")
public class StatController {

    final ProductService productService;
    final StatService statService;

    @Autowired
    public StatController(ProductService productService, StatService statService) {
        this.productService = productService;
        this.statService = statService;
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Stat> getStats(@PathVariable Integer id){
        Product pr= productService.getById(id);
        return ResponseEntity.ok(statService.getStats(pr));
    }
}
