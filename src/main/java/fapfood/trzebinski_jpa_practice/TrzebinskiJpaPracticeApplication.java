package fapfood.trzebinski_jpa_practice;

import fapfood.trzebinski_jpa_practice.model.*;
import fapfood.trzebinski_jpa_practice.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TrzebinskiJpaPracticeApplication {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CustomerRepository customerRepository;
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

//            Address a = new Address();
//            a.setCity("miasto");
//            a.setStreet("ulica");

            Supplier s = new Supplier();
            s.setCompanyName("firma");
//            s.setAddress(a);
            s.setCity("miasto");
            s.setStreet("ulica");
            s.setBankAccountNumber("1232");

            supplierRepository.save(s);

            Category c1 = new Category();
            c1.setCategoryName("różne");

            categoryRepository.save(c1);

            Product p1 = new Product();
            p1.setProductName("woda");
            p1.setUnitsInStock(2);

            Product p2 = new Product();
            p2.setProductName("szafa");
            p2.setUnitsInStock(3);

            p1.setIsSuppliedBy(s);
            p2.setIsSuppliedBy(s);

            p1.setCategory(c1);
            p2.setCategory(c1);

            productRepository.save(p1);
            productRepository.save(p2);

            Set<Product> productSet = new HashSet<>();
            productSet.add(p1);
            productSet.add(p2);

            s.setSupplies(productSet);
            c1.setProducts(productSet);

            BusinessTransaction t1 = new BusinessTransaction();
            t1.setDate(LocalDate.of(2016, 8, 12));
            BusinessTransaction t2 = new BusinessTransaction();
            t2.setDate(LocalDate.of(2016, 8, 13));

            t1.setSales(productSet);
            t2.setSales(productSet);

            transactionRepository.save(t1);
            transactionRepository.save(t2);

            Set<BusinessTransaction> businessTransactionSet = new HashSet<>();
            businessTransactionSet.add(t1);
            businessTransactionSet.add(t2);

            p1.setCanBeSoldOn(businessTransactionSet);
            p2.setCanBeSoldOn(businessTransactionSet);

            //---------------------------------------------------------------

            Category c2 = new Category();
            c2.setCategoryName("sport");
            categoryRepository.save(c2);

            Product p3 = new Product();
            p3.setProductName("piłka");
            p3.setUnitsInStock(5);
            p3.setIsSuppliedBy(s);
            p3.setCategory(c2);

            Set<Product> productSet2 = new HashSet<>();
            productSet2.add(p3);

            BusinessTransaction t3 = new BusinessTransaction();

            Set<BusinessTransaction> businessTransactionSet2 = new HashSet<>();
            businessTransactionSet2.add(t3);

            t3.setSales(productSet2);
            t3.setDate(LocalDate.of(2016, 8, 14));
            p3.setCanBeSoldOn(businessTransactionSet2);
            productRepository.save(p3);

            c2.setProducts(productSet2);

            //---------------------------------------------------------------

            Customer cust = new Customer();
            cust.setCity("miasto2");
            cust.setStreet("ulica2");
            cust.setCompanyName("klient");
            cust.setDiscount(10.0);

            customerRepository.save(cust);

            productRepository.findAll().forEach(product ->
                    System.out.println(product.getProductName() + " is supplied by " + product.getIsSuppliedBy().getCompanyName()));

            supplierRepository.findAll().forEach(supplier ->
                    supplier.getSupplies().forEach(product ->
                            System.out.println(supplier.getCompanyName() + " supplies " + product.getProductName())));

            productRepository.findAll().forEach(product ->
                    System.out.println(product.getProductName() + " have category " + product.getCategory().getCategoryName()));

            categoryRepository.findAll().forEach(category ->
                    category.getProducts().forEach(product ->
                            System.out.println(category.getCategoryName() + " have product " + product.getProductName())));

            productRepository.findAll().forEach(product ->
                    product.getCanBeSoldOn().forEach(businessTransaction ->
                            System.out.println(product.getProductName() + " can be sold on " + businessTransaction.getId())));

            transactionRepository.findAll().forEach(businessTransaction ->
                    businessTransaction.getSales().forEach(product ->
                            System.out.println(businessTransaction.getId() + " sales " + product.getProductName())));
        };
    }

}
