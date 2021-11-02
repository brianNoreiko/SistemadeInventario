package com.example.demo.models;

import lombok.*;
import org.springframework.data.annotation.AccessType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_Id",unique = true,nullable = false)
    private Integer id;
    @NotNull(message = "El producto necesita tener un nombre")
    @Column(name = "brand")
    private String brand;
    @NotNull
    @Column(name = "model")
    private String model;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "price")
    private Double price;
    @Column(name = "description")
    private String description;
    @AccessType(AccessType.Type.PROPERTY)
    @Column(name = "type_product")
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @Column
    private Integer units;

}
