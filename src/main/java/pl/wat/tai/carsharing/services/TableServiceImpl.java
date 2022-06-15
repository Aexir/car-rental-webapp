package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.entities.Showroom;
import pl.wat.tai.carsharing.data.entities.enums.ECarStatus;
import pl.wat.tai.carsharing.data.requests.TableRequest;
import pl.wat.tai.carsharing.data.response.TableResponse;
import pl.wat.tai.carsharing.mappers.CarImageMapper;
import pl.wat.tai.carsharing.repositories.CarImageRepository;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.repositories.ShowroomRepository;
import pl.wat.tai.carsharing.services.interfaces.CarService;
import pl.wat.tai.carsharing.services.interfaces.TableService;

import javax.transaction.Transactional;
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


    @Transactional
    public List<TableResponse> getAll(TableRequest tableRequest){
        List<TableResponse> tableResponses = new ArrayList<>();

        Showroom showroom = showroomRepository.findByName(tableRequest.getShowroom());
        for (Car car : showroom.getCars()){
            if (car.getCarStatus().getName() == ECarStatus.FREE)
            {
                TableResponse tableResponse = new TableResponse();
                CarImage carImage = car.getCarImage();
                if (carImage != null){
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/files/")
                            .path(carImage.getId())
                            .toUriString();
                    tableResponse.setUrl(downloadURL);
                } else {
                    String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/files/")
                            .path(carImageRepository.findAll().get(0).getId())
                            .toUriString();
                    tableResponse.setUrl(downloadURL);
                }


                tableResponse.setBrand(car.getBrand());
                tableResponse.setModel(car.getModel());
                tableResponse.setFuel(car.getFuel().getName().toString());
                tableResponse.setSeats(car.getSeats());
                tableResponse.setShowroom(showroom.getName());
                tableResponse.setTransmission(car.getTransmission());
                tableResponse.setType(car.getCarType().getName().toString());
                tableResponse.setId(car.getId());
                tableResponses.add(tableResponse);
            }
        }
        return tableResponses;
    }
}
