package org.fasttrackit.onlineshop.persistance;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    mai jos este un expemplu de "Find by nested properties" -->
//    --> adica proprietatile Review-ului sunt in properietatile clasei Product
    Page<Review> findByProductId(long productId, Pageable pageable);
}
