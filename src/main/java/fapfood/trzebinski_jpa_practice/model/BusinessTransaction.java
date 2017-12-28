package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class BusinessTransaction {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> sales;
}
