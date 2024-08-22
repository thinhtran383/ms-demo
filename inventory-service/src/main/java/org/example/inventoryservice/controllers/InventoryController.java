package org.example.inventoryservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.inventoryservice.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("%{prefix}/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public ResponseEntity<?> isInStock(@PathVariable String skuCode) {
        return ResponseEntity.ok().body(inventoryService.isInStock(skuCode));
    }
}
