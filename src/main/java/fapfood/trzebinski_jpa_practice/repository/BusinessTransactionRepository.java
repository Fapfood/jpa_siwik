package fapfood.trzebinski_jpa_practice.repository;

import fapfood.trzebinski_jpa_practice.model.BusinessTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface BusinessTransactionRepository extends JpaRepository<BusinessTransaction, Long> {
    Set<BusinessTransaction> findByDate(Date date);
}
