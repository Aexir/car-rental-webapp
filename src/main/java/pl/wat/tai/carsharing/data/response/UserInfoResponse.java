package pl.wat.tai.carsharing.data.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private final List<String> roles;
    private Long id;
    private String username;
    private String email;

}