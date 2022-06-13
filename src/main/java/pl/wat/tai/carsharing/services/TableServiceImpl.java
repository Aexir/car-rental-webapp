package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.requests.TableRequest;
import pl.wat.tai.carsharing.data.response.ShowroomResponse;
import pl.wat.tai.carsharing.data.response.TableResponse;
import pl.wat.tai.carsharing.mappers.CarImageMapper;
import pl.wat.tai.carsharing.repositories.CarImageRepository;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.repositories.ShowroomRepository;
import pl.wat.tai.carsharing.services.interfaces.CarImageService;
import pl.wat.tai.carsharing.services.interfaces.CarService;
import pl.wat.tai.carsharing.services.interfaces.TableService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final CarService carService;
    private final CarRepository carRepository;
    private final CarImageRepository carImageRepository;
    private final ShowroomRepository showroomRepository;
    private final CarImageMapper carImageMapper;

    public List<TableResponse> getAll(TableRequest tableRequest){
        List<TableResponse> tableResponses = new ArrayList<>();

        Showroom showroom = showroomRepository.findByName(tableRequest.getShowroom());
        for (Car car : showroom.getCars()){
            TableResponse tableResponse = new TableResponse();
            CarImage carImage = carImageRepository.getByName(car.getBrand()+car.getModel());
            tableResponse.setUrl(carImageMapper.mapToFileResponse(carImage).getUrl());
            tableResponse.setYear(car.getYear());
            tableResponse.setBrand(car.getBrand());
            tableResponse.setModel(car.getModel());
            tableResponse.setFuel(car.getFuel().toString());
            tableResponse.setSeats(car.getSeats());
            tableResponse.setShowroom(showroom.getName());
            tableResponse.setTransmission(car.getTransmission());
            tableResponse.setType(car.getCarType().toString());
            tableResponses.add(tableResponse);
        }


        return tableResponses;
    }
}