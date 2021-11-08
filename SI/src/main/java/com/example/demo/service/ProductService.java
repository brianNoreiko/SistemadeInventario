package com.example.demo.service;

import com.example.demo.models.Category;
import com.example.demo.models.Movement;
import com.example.demo.models.Product;
import com.example.demo.models.Stat;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final EmailService emailService;
    private final StatService statService;
    private final MovementService movementService;

    @Autowired
    public ProductService(ProductRepository productRepository, EmailService emailService, @Lazy StatService statService,@Lazy MovementService movementService) {
        this.productRepository = productRepository;
        this.emailService = emailService;
        this.statService = statService;
        this.movementService = movementService;
    }

    public Product add(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getById(Integer id) {
        return productRepository.findById(id).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    public void delete(Integer id) {
        Product product = getById(id);
        if (product != null) {
            productRepository.deleteById(id);
        } else {
            System.out.println("El producto no existe");
        }
    }

    public Product getByBarcode(String barcode) {
        return productRepository.findByBarcode(barcode);
    }

    public Product updateProduct(Product updatedProduct) {
        return productRepository.save(updatedProduct);
    }

    public void checkReorderQ(Product product, Movement newMovement) {
        if (!product.getCategory().equals(Category.C)) {
            if (product.getReorderPoint()!=null && product.getUnits() + newMovement.getQuantity() <= product.getReorderPoint()) {
                Stat stat = statService.getStats(product);
                emailService.sendEmail(
                        "Reorder point reached on product +" + product.getModel() + ", order " + stat.getOptimalQuantity() + " units to provider " + product.getProvider().getName() + " to this address: " + product.getProvider().getEmail(),
                        "Reorder point reached");
            }
        }
    }

    @Scheduled(cron = "@monthly")
    public void updateReorderQ() {
        List<Product> productsTypeAandB = getAll().stream()
                .filter(product -> !product.getCategory().equals(Category.C))
                .collect(Collectors.toList());
        for (Product product : productsTypeAandB) {
            List<Movement> extractions = movementService.findAllExtractionsByProductIdMonthly(product.getId());
            int dailyDemand = statService.calculateDailyDemand(extractions);
            double dispersion = statService.calculateDemandDispersion(extractions, dailyDemand * 365);
            int reorderCalc = statService.calculateReorder(product, dispersion, dailyDemand);
            product.setReorderPoint(reorderCalc);
            updateProduct(product);
        }
    }

    @Scheduled(cron = "@daily")
    public void checkRevisionP() {
        List<Product> productsTypeC = getAll().stream()
                .filter(product -> product.getCategory().equals(Category.C))
                .collect(Collectors.toList());
        for (Product product : productsTypeC) {
            Movement lastMovement = movementService.findLastEntranceGreaterThan(product.getId(), 0);
            if (lastMovement != null && ChronoUnit.DAYS.between(lastMovement.getDate(), LocalDateTime.now()) >= product.getRevisionPeriod()) {
                List<Movement> extractions = movementService.findAllExtractionsByProductIdMonthly(product.getId());
                int dailyDemand = statService.calculateDailyDemand(extractions);
                double demandDispersion = statService.calculateDemandDispersion(extractions, dailyDemand * 365);
                int optimalQ = statService.calculateOptimalQuantityP(product, demandDispersion, dailyDemand);
                emailService.sendEmail(
                        "Revision date for product "+product.getModel()+", "+product.getBrand()+". Order "+optimalQ+" units to the provider "+product.getProvider().getName()+" at the address: "+product.getProvider().getEmail(),
                        "Revision date"
                );

            }
        }
    }
}
