package com.example.demo.models;

public enum ProductType {
    A("A"),
    B("B"),
    C("C");

    private String description;

    ProductType(String description){
        this.description = description;
    }

    public static ProductType find (String value){
        for ( ProductType p : values()){
            if(p.toString().equalsIgnoreCase(value)){
                return p;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid product type %s", value));
    }

    public String getDescription(){
        return description;
    }

}
