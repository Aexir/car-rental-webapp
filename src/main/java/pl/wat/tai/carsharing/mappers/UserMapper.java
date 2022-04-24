package pl.wat.tai.carsharing.mappers;

import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.entities.User;
import pl.wat.tai.carsharing.data.UserRespone;

@Component
public class UserMapper {

    public UserRespone userToUserRespone(User user){
        UserRespone userRespone = new UserRespone();
        userRespone.setUserId(user.getUserId());
        userRespone.setEmail(user.getEmail());
        userRespone.setName(user.getName());
        userRespone.setSurname(user.getSurname());
        userRespone.setPhoneNumber(user.getPhoneNumber());
        return userRespone;
    }

    public User userRequestToUser(UserRequest userRequest){
        User user = new User();
        user.setUserId(userRequest.getUserId());
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        return user;
    }
}
