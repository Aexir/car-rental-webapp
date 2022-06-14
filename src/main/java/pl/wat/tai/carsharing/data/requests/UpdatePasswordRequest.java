package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String username;
    private String currentPassword;
    private String newPassword;
}
