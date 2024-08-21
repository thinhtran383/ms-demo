package org.example.productservice.controllers;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${prefix}/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(
            @RequestBody ProductDTO productDTO
    ) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }


}
