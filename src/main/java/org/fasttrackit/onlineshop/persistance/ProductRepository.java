package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


//cand o clasa primitiva are litera mare device Wrapper class exemplu  long - > Long

public interface ProductRepository extends JpaRepository<Product, Long> {

}