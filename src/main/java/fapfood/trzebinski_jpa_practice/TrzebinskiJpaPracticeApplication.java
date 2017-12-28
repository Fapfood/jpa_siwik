package fapfood.trzebinski_jpa_practice;

import fapfood.trzebinski_jpa_practice.model.Category;
import fapfood.trzebinski_jpa_practice.model.Product;
import fapfood.trzebinski_jpa_practice.model.Supplier;
import fapfood.trzebinski_jpa_practice.model.BusinessTransaction;
import fapfood.trzebinski_jpa_practice.repository.CategoryRepository;
import fapfood.trzebinski_jpa_practice.repository.ProductRepository;
import fapfood.trzebinski_jpa_practice.repository.SupplierRepository;
import fapfood.trzebinski_jpa_practice.repository.BusinessTransactionRepository;
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
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BusinessTransactionRepository transactionRepository;

    public static void main(String[] args) {
        SpringApplication.run(TrzebinskiJpaPracticeApplication.class, args);
    }

    @Bean
    @Transactional
    CommandLineRunner runner() {
        return args -> {

            Supplier s = new Supplier();
            s.setCompanyName("firma");
            s.setCity("miasto");
            s.setStreet("ulica");

            supplierRepository.save(s);

            Product p1 = new Product();
            p1.setProductName("woda");
            p1.setUnitsInStock(2);

            Product p2 = new Product();
            p2.setProductName("szafa");
            p2.setUnitsInStock(3);

            p1.setIsSuppliedBy(s);
            p2.setIsSuppliedBy(s);

            productRepository.save(p1);
            productRepository.save(p2);

            Set<Product> productSet = new HashSet<>();
            productSet.add(p1);
            productSet.add(p2);
            s.setSupplies(productSet);

//            supplierRepository.save(s);

            Category c = new Category();
            c.setCategoryName("różne");
            c.setProducts(productSet);

            categoryRepository.save(c);

            BusinessTransaction t1 = new BusinessTransaction();
            BusinessTransaction t2 = new BusinessTransaction();

            t1.setSales(productSet);
            t2.setSales(productSet);

            Set<BusinessTransaction> businessTransactionSet = new HashSet<>();
            businessTransactionSet.add(t1);
            businessTransactionSet.add(t2);

            transactionRepository.save(t1);
            transactionRepository.save(t2);

            p1.setCanBeSoldOn(businessTransactionSet);
            p2.setCanBeSoldOn(businessTransactionSet);


//            productRepository.save(p2);
//            supplierRepository.save(s);

            productRepository.findAll().forEach(product -> System.out.println(product.getIsSuppliedBy().getCompanyName()));
            supplierRepository.findAll().forEach(supplier -> supplier.getSupplies()
                    .forEach(product -> System.out.println(product.getProductName())));
            categoryRepository.findAll().forEach(category -> category.getProducts()
                    .forEach(product -> System.out.println(product.getProductName())));
            transactionRepository.findAll().forEach(businessTransaction -> businessTransaction.getSales()
                    .forEach(product -> System.out.println(product.getProductName())));
            productRepository.findAll().forEach(product -> product.getCanBeSoldOn()
                    .forEach(businessTransaction -> System.out.println(businessTransaction.getId())));

        };
    }

}
