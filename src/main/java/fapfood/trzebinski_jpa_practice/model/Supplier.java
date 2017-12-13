package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Supplier {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;

    private String street;

    private String city;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "isSuppliedBy")
//    @JoinColumn(name="SUPPLIER")
    private Set<Product> supplies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(getCompanyName(), supplier.getCompanyName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getCompanyName());
    }
}
