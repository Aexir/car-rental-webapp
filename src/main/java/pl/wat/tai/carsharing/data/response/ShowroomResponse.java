package pl.wat.tai.carsharing.data.response;

import lombok.Data;

import java.util.List;

@Data
public class ShowroomResponse {
    private long id;
    private String name;
    private float latitude;
    private float longitude;
    private List<CarResponse> carList;
}
