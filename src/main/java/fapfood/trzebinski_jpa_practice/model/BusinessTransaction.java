package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class BusinessTransaction {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> sales;

    @Override
    public String toString() {
        return id.toString();
    }
}
