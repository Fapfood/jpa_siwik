package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
//@SecondaryTable(name="ADDRESS_TABLE")
public class Supplier extends Company {

//    @Id
//    @GeneratedValue
//    private Long id;

//    private String companyName;

//    @Column(table = "ADDRESS_TABLE")
//    private String street;

//    @Column(table = "ADDRESS_TABLE")
//    private String city;

//    @Embedded
//    private Address address;

    private String bankAccountNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "isSuppliedBy")
//    @JoinColumn(name="SUPPLIER")
    private Set<Product> supplies;

    @Override
    public String toString() {
        return super.toString();
    }
}
