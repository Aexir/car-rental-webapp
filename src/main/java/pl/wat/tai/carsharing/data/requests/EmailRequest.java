package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

@Data
public class EmailRequest {
    private String email;
    private String name;
    private String message;
}
