package nl.ryanb.iprwcback.model.request;

import lombok.Data;

@Data
public class UpdateProduct {
    private String name;
    private double price;
    private String description;
    private String imageURL;
}
