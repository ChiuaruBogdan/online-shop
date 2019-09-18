package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


//cand o clasa primitiva are litera mare devine Wrapper class exemplu  long - > Long

public interface ProductRepository extends JpaRepository<Product, Long> {
    //    mai jos e un Query custom (pt mySQL) ca se genereze automat la cautarea dupa nume
    //    "Page" era "List". dar am schimbat ca si "Page" pentru a genera interfata "Page"

    Page<Product> findByNameContaining(String partialName, Pageable pageable);

    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(
            String partialName, int minimumQuantity, Pageable pageable);

//    mai jos este un "jqpl syntax" care inseamna Java Persistance Query Language
//    iar metoda cu adnotarea Query se numeste un "Named Query"
    @Query("SELECT product FROM Product product WHERE name LIKE ' :%partialName%'")
    List<Product> findByPartialName(String partialName);
}