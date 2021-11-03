package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Stat {
    private Category optimalCategory;
    private Integer optimalQuantity;
    private Integer reorder;
    private Integer dailyDemand;
    private Double annualConsumption;
    private Double demandDispersion;
}
