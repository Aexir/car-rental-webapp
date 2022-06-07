package pl.wat.tai.carsharing.mappers;

import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.UserResponse;
import pl.wat.tai.carsharing.data.entities.User;

@Component
public class UserMapper {

    public UserResponse userToUserRespone(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setName(user.getName());
        userResponse.setSurname(user.getSurname());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        return userResponse;
    }

    public User userRequestToUser(UserRequest userRequest) {
        User user = new User();
        user.setUserId(userRequest.getUserId());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return user;
    }
}
