package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Integer id;

    @NotNull(message = "Se necesita al menos un producto en el movimiento")
    @OneToOne
    @JoinColumn (name = "procut_id")
    private Product product;
    @NotNull
    @NotEmpty(message = "La cantidad debe ser igual o mayor a 1")
    @Size(min = 1)
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "date")
    private LocalDate date;
}
