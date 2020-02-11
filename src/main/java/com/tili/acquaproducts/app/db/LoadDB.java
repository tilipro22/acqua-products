package com.tili.acquaproducts.app.db;

import com.tili.acquaproducts.app.productManagement.Product;
import com.tili.acquaproducts.app.productManagement.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDB {

    @Bean
    CommandLineRunner initDataBase(ProductRepository productRepository) {
        return args -> {
            log.info("Preloading Products");
            log.info("Product: " + productRepository.save(new Product("Africa", "Bikini", "Africa", "Rojo", 2, 899.99, 0, 'F', "90")));
            log.info("Product: " + productRepository.save(new Product("Africa", "Bikini", "Africa", "Verde", 1, 899.99, 0, 'F', "90")));
            log.info("Product: " + productRepository.save(new Product("Africa", "Bikini", "Florida", "Negra", 4, 799.99, 10, 'F', "85", "Bikini con voladitos")));
        };
    }
}
