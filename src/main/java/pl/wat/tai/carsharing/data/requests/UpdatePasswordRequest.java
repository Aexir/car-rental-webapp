package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordRequest {
    private String username;
    @NotBlank
    @Size(min = 6, max = 40)
    private String currentPassword;
    @NotBlank
    @Size(min = 6, max = 40)
    private String newPassword;
}
