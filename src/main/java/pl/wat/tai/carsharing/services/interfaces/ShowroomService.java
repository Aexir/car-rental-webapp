package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.bind.annotation.PathVariable;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;

import java.util.List;

public interface ShowroomService {

    void addShowroom(ShowroomRequest showroomRequest);

    void removeShowroom(String name);

    void addCarToShowroom(String name, long id);

    void removeCarFromShowroom(String name, long id);

    List<ShowroomResponse> getAll();

    ShowroomResponse get(@PathVariable String name);

    List<String> getShowroomNames();
}
