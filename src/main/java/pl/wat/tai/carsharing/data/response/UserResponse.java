package pl.wat.tai.carsharing.data.response;

import lombok.Data;
import pl.wat.tai.carsharing.data.entities.Role;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;
}
