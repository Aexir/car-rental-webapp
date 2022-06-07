package pl.wat.tai.carsharing.data;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;

@Getter
@Setter
public class SignUpRequest {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 6, max = 40)
    private String password;
    @NotNull
    private String phoneNumber;
    @NotNull
    private Date birthDate;
}
