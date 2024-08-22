package org.example.inventoryservice;

import org.example.inventoryservice.entities.Inventory;
import org.example.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//        return args -> {
//            inventoryRepository.save(new Inventory(null, "ABC123", 0));
//            inventoryRepository.save(new Inventory(null, "DEF456", 50));
//            inventoryRepository.save(new Inventory(null, "GHI789", 75));
//        };
//    }
}
