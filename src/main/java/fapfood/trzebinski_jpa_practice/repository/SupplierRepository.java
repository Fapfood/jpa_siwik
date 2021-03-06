package fapfood.trzebinski_jpa_practice.repository;

import fapfood.trzebinski_jpa_practice.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Set<Supplier> findByCompanyNameStartsWithIgnoreCase(String filterText);
}
