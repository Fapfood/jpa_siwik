package fapfood.trzebinski_jpa_practice;

import fapfood.trzebinski_jpa_practice.model.Product;
import fapfood.trzebinski_jpa_practice.model.Supplier;
import fapfood.trzebinski_jpa_practice.repository.ProductRepository;
import fapfood.trzebinski_jpa_practice.repository.SupplierRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TrzebinskiJpaPracticeApplication {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;

    public static void main(String[] args) {
        SpringApplication.run(TrzebinskiJpaPracticeApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner runner() {
        return args -> {

            Product p = new Product();
            p.setProductName("woda");
            p.setUnitsInStock(2);

            Supplier s = new Supplier();
            s.setCompanyName("firma");
            s.setCity("miasto");
            s.setStreet("ulica");

            supplierRepository.save(s);

            p.setIsSuppliedBy(s);

            productRepository.save(p);
            Set<Product> set = new HashSet<>();
            set.add(p);
            s.setSupplies(set);

            supplierRepository.save(s);

            productRepository.findAll().forEach(product -> System.out.println(product.getIsSuppliedBy().getCompanyName()));
            supplierRepository.findAll().forEach(supplier -> supplier.getSupplies()
                    .forEach(product -> System.out.println(product.getProductName())));
        };
    }

}
