package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShowroomRequest {
    @NotBlank
    private String name;
    private float latitude;
    private float longitude;
}
