package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Company {

    @Id
//    @GeneratedValue
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    private String companyName;

    private String street;

    private String city;

    @Override
    public String toString() {
        return id.toString();
    }
}
