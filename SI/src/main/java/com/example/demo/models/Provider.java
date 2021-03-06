package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(name = "Provider")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_id", unique = true,nullable = false)
    private Integer id;
    @Column(name = "name")
    @NotNull(message = "El proveedor debe tener un nombre que lo identifique")
    private String name;
    @Column(name = "email")
    @NotNull(message = "email requerido")
    private String email;
    @NotNull
    @Min(value = 1, message = "El Lead Time debe ser igual o mayor a 1(medido en días)")
    @Column(name = "leadtime")
    private Integer leadTime;
}
