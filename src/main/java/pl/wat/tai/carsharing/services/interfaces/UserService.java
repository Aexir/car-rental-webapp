package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.UserRespone;

import java.util.List;

public interface UserService {
    List<UserRespone> getAllUsers();
    void saveUser(UserRequest userRequest);
}
