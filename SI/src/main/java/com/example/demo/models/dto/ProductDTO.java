package com.example.demo.models.dto;

import com.example.demo.models.Category;

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

    public ProductDTO( String brand, String model, String barcode, Double price, String description, Category category, Integer provider,Double serviceLevel,Double prepareCost,Double storageCost) {
        this.brand = brand;
        this.model = model;
        this.barcode = barcode;
        this.price = price;
        this.description = description;
        this.category = category;
        this.provider = provider;
        this.serviceLevel = serviceLevel;
        this.prepareCost = prepareCost;
        this.storageCost = storageCost;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getProvider() {
        return provider;
    }

    public void setProvider(Integer provider) {
        this.provider = provider;
    }

    public Double getServiceLevel() {
        return serviceLevel;
    }

    public void setServiceLevel(Double serviceLevel) {
        this.serviceLevel = serviceLevel;
    }

    public Double getPrepareCost() {
        return prepareCost;
    }

    public void setPrepareCost(Double prepareCost) {
        this.prepareCost = prepareCost;
    }

    public Double getStorageCost() {
        return storageCost;
    }

    public void setStorageCost(Double storageCost) {
        this.storageCost = storageCost;
    }
}
