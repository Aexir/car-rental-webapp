package pl.wat.tai.carsharing.mappers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.Location;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.CarImageResponse;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;

@Component
public class ShowroomMapper {

    public Showroom requestToShowroom(ShowroomRequest showroomRequest){
        Showroom showroom = new Showroom();
        showroom.setId(showroomRequest.getId());
        showroom.setName(showroomRequest.getName());
        Location location = new Location();
        location.setLongitude(showroomRequest.getLongitude());
        location.setLatitude(showroomRequest.getLatitude());
        showroom.setLocation(location);
        return showroom;
    }

    public ShowroomResponse mapToResponse(Showroom showroom){
        ShowroomResponse showroomResponse = new ShowroomResponse();
        showroomResponse.setId(showroom.getId());
        showroomResponse.setName(showroom.getName());
        showroomResponse.setLongitude(showroom.getLocation().getLongitude());
        showroomResponse.setLatitude(showroom.getLocation().getLatitude());
        return showroomResponse;
    }
}
