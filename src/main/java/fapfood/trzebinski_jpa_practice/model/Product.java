package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;

    private Integer unitsInStock;

    @ManyToOne
//    @JoinColumn(name = "CATEGORY")
    private Category category;

    @ManyToOne
//    @JoinColumn(name="SUPPLIER")
    private Supplier isSuppliedBy;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "sales", cascade = CascadeType.PERSIST)
    private Set<BusinessTransaction> canBeSoldOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Product product = (Product) o;
        return Objects.equals(getProductName(), product.getProductName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getProductName());
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
