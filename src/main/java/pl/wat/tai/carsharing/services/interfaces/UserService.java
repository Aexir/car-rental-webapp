package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.wat.tai.carsharing.data.requests.UpdateRequest;
import pl.wat.tai.carsharing.data.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();
    ResponseEntity<?> updateUserInfo(UpdateRequest updateRequest);
}
