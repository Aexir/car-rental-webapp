package pl.wat.tai.carsharing.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRespone {
    private String token;
    private String type = "Bearer";
    private long id;
    private String login;
    private String email;
    private List<String> roles;


}
