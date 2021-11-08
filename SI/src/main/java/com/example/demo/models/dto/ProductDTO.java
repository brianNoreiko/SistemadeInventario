package com.example.demo.models.dto;

import com.example.demo.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
    String brand;
    String model;
    String barcode;
    Double price;
    String description;
    Category category;
    Integer provider;
    Double serviceLevel;
    Double prepareCost;
    Double storageCost;
    Integer revisionPeriod;

}
