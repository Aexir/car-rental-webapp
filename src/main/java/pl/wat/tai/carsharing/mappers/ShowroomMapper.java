package pl.wat.tai.carsharing.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wat.tai.carsharing.data.entities.Location;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowroomMapper {

    private final CarMapper carMapper;

    public Showroom requestToShowroom(ShowroomRequest showroomRequest) {
        Showroom showroom = new Showroom();
        showroom.setName(showroomRequest.getName());
        Location location = new Location();
        location.setLongitude(showroomRequest.getLongitude());
        location.setLatitude(showroomRequest.getLatitude());
        showroom.setLocation(location);
        return showroom;
    }

    public ShowroomResponse mapToResponse(Showroom showroom) {
        ShowroomResponse showroomResponse = new ShowroomResponse();
        showroomResponse.setId(showroom.getId());
        showroomResponse.setName(showroom.getName());
        showroomResponse.setLongitude(showroom.getLocation().getLongitude());
        showroomResponse.setLatitude(showroom.getLocation().getLatitude());
        showroomResponse.setCarList(showroom.getCars().stream().map(carMapper::carToResponse).collect(Collectors.toList()));
        return showroomResponse;
    }
}
