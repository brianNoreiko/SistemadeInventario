package com.example.demo.service;

import com.example.demo.models.*;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.math3.distribution.AbstractRealDistribution;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {
    private final MovementService movementService;
    private final ProductService productService;

    @Autowired
    public StatService(MovementService movementService, ProductService productService) {
        this.movementService = movementService;
        this.productService = productService;
    }

    //con demanda ultimo mes
    public Stat getStats(Product pr) {
        List<Movement> extractions = movementService.findAllByDateIsAfterAndProductId(LocalDateTime.now().minus(Period.ofMonths(1)), pr.getId()).stream()
                .filter(movement -> movement.getQuantity() < 0)
                .collect(Collectors.toList());
        // if (extractions.size()>0){
        int dailyDemand = calculateDailyDemand(extractions);
        int optimalQuantity = (int) calculateOptimalQuantity(pr, dailyDemand * 365);
        double demandDispersion = calculateDemandDispersion(extractions, dailyDemand );
        int reorder = calculateReorder(pr, demandDispersion, dailyDemand);
        double cav = calculateAnnualConsumption(pr.getPrice(),dailyDemand*365);
        Category optimalCat = calculateOptimalCategory(pr);
        return new Stat(optimalCat, optimalQuantity, reorder, dailyDemand, cav, demandDispersion);
       /* }
        else
            return null;*/
    }

    private Category calculateOptimalCategory(Product pr) {
        Category result = null;
        Map.Entry<Double, Product> p = null;
        List<Product> allProducts = productService.getAll();
        double cava = 0.0;
        TreeMap<Double, Product> productsCAV = new TreeMap<>();
        TreeMap<Double, Product> productsPercent = new TreeMap<>();
        for (Product product : allProducts) {
            List<Movement> exts=movementService.findAllByDateIsAfterAndProductId(LocalDateTime.now().minus(Period.ofMonths(1)), pr.getId()).stream()
                    .filter(movement -> movement.getQuantity() < 0)
                    .collect(Collectors.toList());
            double cav = calculateAnnualConsumption(product.getPrice(),calculateDailyDemand(exts));
            cava += cav;
            productsCAV.put(cav, product);
        }
        while (productsCAV.size() > 0) {
            p = productsCAV.pollLastEntry();
            productsPercent.put(p.getKey() / cava, p.getValue());
        }
        double perc = 0;
        while (productsPercent.size() > 0) {
            p = productsPercent.pollLastEntry();
            if (perc < 0.85) {        //Los productos que representan el 85% del CAVA pertenecen a la zona A
                if (p.getValue().getId().equals(pr.getId()))
                    result = Category.A;
                else
                    perc = perc + p.getKey();
            } else if (perc < 0.90) {  //%5 porciento a la zona B
                if (p.getValue().getId().equals(pr.getId()))
                    result = Category.B;
                else
                    perc = perc + p.getKey();
            } else {                 //%10 restante a la zona C
                result = Category.C;
            }
        }
        return result;
    }

    //ceil para redondear para arriba
    private double calculateOptimalQuantity(Product pr, int annualDemand) {
        return Math.ceil(Math.sqrt(((2 * annualDemand * pr.getPrepareCost()) / pr.getStorageCost())));
    }

    private int calculateReorder(Product pr, double demandDispersion, int dailyDemand) {
        double oL = Math.sqrt(pr.getProvider().getLeadTime()) * demandDispersion;
        double securityUnits = new NormalDistribution().cumulativeProbability(pr.getServiceLevel()) * oL;
        return (int) Math.ceil(securityUnits + (dailyDemand*pr.getProvider().getLeadTime()));
    }

    private int calculateDailyDemand(List<Movement> extractions) {
        int sum = 0;
        double numberOfDays = 0;
        if (extractions.size() > 0) {
            numberOfDays = ChronoUnit.DAYS.between(extractions.get(0).getDate(), extractions.get(extractions.size() - 1).getDate());
            for (Movement ext : extractions) {
                sum += ext.getQuantity() * -1;
            }
        }
        return (int) Math.ceil(sum / (numberOfDays + 1));
    }

    private double calculateAnnualConsumption(double price,int annualDemand) {
        return price * annualDemand;
    }

    private double calculateDemandDispersion(List<Movement> movements, double annualDemand) {
        double sum = 0.0;
        for (Movement ext : movements) {
            sum += Math.pow((ext.getQuantity() * -1) - annualDemand,2);
        }
        return (sum > 0) ? Math.sqrt(sum / movements.size()) : 0.0;
    }
}