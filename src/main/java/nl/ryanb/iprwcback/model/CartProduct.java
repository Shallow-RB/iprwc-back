package nl.ryanb.iprwcback.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class CartProduct {

    private Long id;
    private Product product;
    private Integer quantity;
}
