package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    private String categoryName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "category")
//    @JoinColumn(name = "CATEGORY")
    private Set<Product> products;

    @Override
    public String toString() {
        return id.toString();
    }
}
