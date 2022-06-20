package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ShowroomRequest {
    @NotBlank
    private String name;
    private float latitude;
    private float longitude;
}
