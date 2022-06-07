package pl.wat.tai.carsharing.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long userId;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
}
