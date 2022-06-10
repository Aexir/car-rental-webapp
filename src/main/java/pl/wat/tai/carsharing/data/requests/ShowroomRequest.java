package pl.wat.tai.carsharing.data.requests;

import lombok.Data;

@Data
public class ShowroomRequest {
    private long id;
    private String name;
    private float latitude;
    private float longitude;
}
