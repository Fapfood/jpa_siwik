package fapfood.trzebinski_jpa_practice.repository;

import fapfood.trzebinski_jpa_practice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
