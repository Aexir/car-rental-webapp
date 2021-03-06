package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.requests.ShowroomRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.mappers.ShowroomMapper;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.repositories.LocationRepository;
import pl.wat.tai.carsharing.repositories.ShowroomRepository;
import pl.wat.tai.carsharing.services.interfaces.ShowroomService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {

    private final ShowroomRepository showroomRepository;
    private final LocationRepository locationRepository;
    private final CarRepository carRepository;
    private final ShowroomMapper showroomMapper;

    @Override
    public void addShowroom(ShowroomRequest showroomRequest) {
        if (showroomRepository.findByName(showroomRequest.getName()) == null) {
            showroomRepository.save(showroomMapper.requestToShowroom(showroomRequest));
        } else {
            throw new IllegalArgumentException("Showroom named " + showroomRequest.getName() + " already exists.");
        }
    }

    @Override
    @Transactional
    public void removeShowroom(String name) {
        Showroom showroom = showroomRepository.findByName(name);
        showroomRepository.delete(showroom);
    }

    @Override
    @Transactional
    public void removeCarFromShowroom(long id) {
        List<Showroom> showrooms = showroomRepository.findAll();
        String s = "";
        for (Showroom showroom : showrooms) {
            if (showroom.getCars().contains(carRepository.getReferenceById(id))) {
                s = showroom.getName();
            }
        }
        removeCarFromShowroom(s, id);
    }

    @Override
    @Transactional
    public void addCarToShowroom(String name, long id) {
        if (showroomRepository.findByName(name) != null) {

            Showroom showroom = showroomRepository.findByName(name);
            Car car = carRepository.getReferenceById(id);
            showroom.getCars().add(car);
            showroomRepository.save(showroom);
        }
    }

    @Override
    @Transactional
    public void removeCarFromShowroom(String name, long id) {
        if (showroomRepository.findByName(name) != null) {
            Showroom showroom = showroomRepository.findByName(name);
            showroom.getCars().removeIf(car -> car.getId() == id);
            showroomRepository.save(showroom);
        }
    }

    @Override
    @Transactional
    public List<ShowroomResponse> getAll() {
        return showroomRepository.findAll().stream().map(showroomMapper::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShowroomResponse get(String name) {
        return showroomMapper.mapToResponse(showroomRepository.findByName(name));
    }

    @Override
    @Transactional
    public List<String> getShowroomNames() {
        List<String> newList = new ArrayList<>();
        for (Showroom showroom : showroomRepository.findAll()) {
            newList.add(showroom.getName());
        }
        return newList;
    }
}
