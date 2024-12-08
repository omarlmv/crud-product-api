
package com.example.productapi.repository;

import com.example.productapi.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByNameContaining(String name);
    Flux<Product> findAllByStatusNot(String status);
    Mono<Product> findByIdAndStatus(Integer id, String status);
    Flux<Product> findByNameContainingIgnoreCase(String name);

    //Flux<Product> findByCategoryId(Long categoryId);

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    Flux<Product> findByCategoryId(@Param("categoryId") Integer categoryId);

    /**
     * Finds all products in a specific category.
     *
     * @param categoryName the name of the category
     * @return a Flux of products in the category
     */
    //Flux<Product> findByCategoryName(String categoryName);

    /**
     * Counts the total number of products in a specific category.
     *
     * @param categoryName the name of the category
     * @return a Mono with the count of products
     */
    //Mono<Long> countByCategoryName(String categoryName);

    /**
     * Counts the number of products in a specific category with a given status.
     *
     * @param categoryName the name of the category
     * @param status the status of the products (e.g., ACTIVE)
     * @return a Mono with the count of products
     */
    //Mono<Long> countByCategoryNameAndStatus(String categoryName, String status);

    /**
     * Calculates the average price of products in a specific category.
     *
     * @param categoryName the name of the category
     * @return a Mono with the average price of products
     */
    @Query("SELECT AVG(price) FROM Product p WHERE p.category.name = :categoryName")
    Mono<Double> calculateAveragePriceByCategory(String categoryName);

    /**
     * Finds the most popular product in a specific category based on sales.
     *
     * @param categoryName the name of the category
     * @return a Mono with the most popular product
     */
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName ORDER BY p.sales DESC LIMIT 1")
    Mono<Product> findMostPopularProductByCategory(String categoryName);

}
