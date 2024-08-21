package org.example.productservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.entities.Product;
import org.example.productservice.repositories.ProductRepository;
import org.example.productservice.response.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Product createProduct(ProductDTO product) {
        log.info("Creating product: {}", product);
        return productRepository.save(modelMapper.map(product, Product.class));
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = IntStream.range(0, products.size())
                .mapToObj(i -> {
                    Product product = products.get(i);
                    ProductResponse response = modelMapper.map(product, ProductResponse.class);
                    response.setNumber((long) i + 1);
                    return response;
                })
                .collect(Collectors.toList());

        return productResponses;
    }
}
