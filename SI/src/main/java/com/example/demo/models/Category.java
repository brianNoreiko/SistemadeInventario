package com.example.demo.models;

public enum Category {
    A("A"),
    B("B"),
    C("C");

    private String description;

    Category(String description){
        this.description = description;
    }

    public static Category find (String value){
        for ( Category p : values()){
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
