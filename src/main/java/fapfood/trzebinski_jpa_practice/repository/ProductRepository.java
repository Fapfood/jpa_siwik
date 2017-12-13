package fapfood.trzebinski_jpa_practice.repository;

import fapfood.trzebinski_jpa_practice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
