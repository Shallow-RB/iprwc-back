package nl.ryanb.iprwcback.model.request;

import lombok.Data;

@Data
public class UpdateUser {
    private String username;
    private String name;
}
