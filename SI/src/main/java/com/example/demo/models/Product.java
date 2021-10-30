package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id",unique = true,nullable = false)
    private Integer id;
    @NotNull(message = "El producto necesita tener un nombre")
    @Column(name = "name")
    private String name;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
    @AccessType(AccessType.Type.PROPERTY)
    @Column(name = "type_product")
    private ProductType productType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id")
    private Provider provider;


}
