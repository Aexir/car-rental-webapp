package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    void saveUser(UserRequest userRequest);
}
