package nl.ryanb.iprwcback.model.req;

import lombok.Data;

@Data
public class UpdateProduct {
    private String name;
    private double price;
    private String description;
    private String imageURL;
}
