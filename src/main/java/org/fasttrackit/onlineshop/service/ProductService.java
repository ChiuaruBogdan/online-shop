package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
//    logger mai jos, a fost necesara migrarea catre importul "slf4j"
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

//    cauta "Lombok" si citeste

//    mai jos este OOP design pattern - IoC = Inversion of Control
    private final ProductRepository productRepository;

//    adnotarea "@Autowired" injecteaza dependenta. OOP design pattern numit = Dependency Injection
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(SaveProductRequest request){
//apelare logger mai jos. La logger este nevoie de " {}" pentru concatenare, nu + cum eram obisnuiti
        LOGGER.info("Creating product: {}", request);

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setImagePath(request.getImagePath());

        return productRepository.save(product);
    }
}
