package pl.wat.tai.carsharing.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private long id;
    private long altitude;
    private long latitude;
}
