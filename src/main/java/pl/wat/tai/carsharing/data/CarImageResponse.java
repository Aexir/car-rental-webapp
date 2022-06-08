package pl.wat.tai.carsharing.data;

import lombok.Data;

@Data
public class CarImageResponse {
    private String id;
    private String name;
    private Long size;
    private String url;
    private String contentType;
}
