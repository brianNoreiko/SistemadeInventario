package com.example.demo.models;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor

public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id")
    private Integer id;

    @NotNull(message = "Se necesita al menos un producto en el movimiento")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "product_id")
    private Product product;
    @NotNull
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "date")
    private LocalDateTime date;
}
