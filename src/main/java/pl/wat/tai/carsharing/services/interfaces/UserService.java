package pl.wat.tai.carsharing.services.interfaces;

import pl.wat.tai.carsharing.data.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
}
