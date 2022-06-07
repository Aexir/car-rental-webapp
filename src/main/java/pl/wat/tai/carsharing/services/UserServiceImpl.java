package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.UserRequest;
import pl.wat.tai.carsharing.data.UserResponse;
import pl.wat.tai.carsharing.mappers.UserMapper;
import pl.wat.tai.carsharing.repositories.UserRepository;
import pl.wat.tai.carsharing.services.interfaces.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserRespone).toList();
    }

    @Override
    public void saveUser(UserRequest userRequest) {
        userRepository.save(userMapper.userRequestToUser(userRequest));
    }
}
