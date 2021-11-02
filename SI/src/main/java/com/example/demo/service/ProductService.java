package com.example.demo.service;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;


    public Product add(Product product){
        return productRepository.save(product);
    }
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Product getById(Integer id){
       return productRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void delete(Integer id){
        Product product = getById(id);
        if(product != null){
            productRepository.deleteById(id);
        }else{
            System.out.println("El producto no existe");
        }
    }

    public Product getByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }
}
