package fapfood.trzebinski_jpa_practice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
public class Customer extends Company {

    private Double discount;

    @Override
    public String toString() {
        return super.toString();
    }
}
