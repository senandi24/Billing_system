package com.example.Billing_System.Respository;

import com.example.Billing_System.Models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsRepository extends JpaRepository<Items, Long> {

    Optional<Items> findByItemCode(String itemCode);

    List<Items> findByItemName(String itemName);

    List<Items> findByCategory(String category);

    List<Items> findByIsActive(Boolean isActive);

    @Query("SELECT i FROM Items i WHERE i.itemName LIKE %:name%")
    List<Items> findByItemNameContaining(@Param("name") String name);

    @Query("SELECT i FROM Items i WHERE i.description LIKE %:description%")
    List<Items> findByDescriptionContaining(@Param("description") String description);

    @Query("SELECT i FROM Items i WHERE i.price BETWEEN :minPrice AND :maxPrice")
    List<Items> findByPriceBetween(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    @Query("SELECT DISTINCT i.category FROM Items i WHERE i.isActive = true")
    List<String> findAllActiveCategories();

    @Query("SELECT i FROM Items i WHERE i.isActive = true ORDER BY i.itemName")
    List<Items> findAllActiveItemsOrderByName();

    @Query("SELECT COUNT(i) FROM Items i WHERE i.category = :category AND i.isActive = true")
    long countActiveByCategoryName(@Param("category") String category);

    boolean existsByItemCode(String itemCode);
}