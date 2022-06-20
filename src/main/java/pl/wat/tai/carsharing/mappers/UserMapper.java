package pl.wat.tai.carsharing.mappers;

import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.data.response.UserResponse;

@Component
public class UserMapper {
    public UserResponse toResponseMapper(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setRoles(user.getRoles());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
