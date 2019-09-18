package org.fasttrackit.onlineshop.service;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.persistance.ProductRepository;
import org.fasttrackit.onlineshop.transfer.product.GetProductRequest;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Product createProduct(SaveProductRequest request) {
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

    public Product getProduct(Long id) {
        LOGGER.info("Retrieving product{}", id);
//        using Optional
        return productRepository.findById(id)
//                mai jos avem lambda expressions
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product " + id + " not found."));
    }


//    mai jos este metoda care returneaza mai multe produse cu posibilitatea de filtrare
//    trebuie folosita pentru ca UI sa faca display pe pagini la produse multiple
//    btw trebuie facut test pentru ea, nu am avut timp la curs. acasa la tema sa faci!
    public Page<Product> getProducts(GetProductRequest request, Pageable pageable){
    LOGGER.info("Retrieving products: {}", request);

    if (request != && request.getPartialName() != null &&
    request.getMinimumQuantity() != null) {
        return productRepository
                .findByNameContainingAndQuantityGreaterThanEqual
                        (request.getPartialName(), request.getMinimumQuantity(), pageable);
    } else if (request != null && request.getPartialName() != null) {
        return productRepository.findByNameContaining(request.getPartialName(), pageable);
    }else {return productRepository.findAll(pageable);

    }

    public Product updateProduct(long id, SaveProductRequest request) {
        LOGGER.info("Updating product{}: {}", id, request);

        Product product = getProduct(id);
//        mai jos copiem proprietati de pe "request" si le punem pe "product" cu BeanUtils.copyProperties
        BeanUtils.copyProperties(request, product);

        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        LOGGER.info("Deleting product{}", id);

        productRepository.deleteById(id);
    }
}
