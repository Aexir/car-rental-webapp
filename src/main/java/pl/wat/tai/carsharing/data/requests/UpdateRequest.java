package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateRequest {

    private String currentUsername;
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private String password;
}
