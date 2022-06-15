package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.Car;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.response.CarImageResponse;
import pl.wat.tai.carsharing.data.response.CarSliderResponse;
import pl.wat.tai.carsharing.mappers.CarImageMapper;
import pl.wat.tai.carsharing.repositories.CarImageRepository;
import pl.wat.tai.carsharing.repositories.CarRepository;
import pl.wat.tai.carsharing.services.interfaces.CarImageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService {

    private final CarImageRepository carImageRepository;
    private final CarImageMapper carImageMapper;
    private final CarRepository carRepository;


    @Override
    public void save(MultipartFile file) {
        try {
            CarImage carImage = new CarImage();
            carImage.setName(StringUtils.cleanPath(file.getOriginalFilename()));
            carImage.setContentType(file.getContentType());
            carImage.setData(file.getBytes());
            carImage.setSize(file.getSize());
            carImageRepository.save(carImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(MultipartFile file, String name) {
        try {
            CarImage carImage = new CarImage();
            carImage.setName(StringUtils.cleanPath(name));
            carImage.setContentType(file.getContentType());
            carImage.setData(file.getBytes());
            carImage.setSize(file.getSize());
            carImageRepository.save(carImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<CarImage> getFile(String id) {
        return carImageRepository.findById(id);
    }

    @Override
    public List<CarImageResponse> getAllFiles() {
        return carImageRepository.findAll().stream()
                .map(carImageMapper::mapToFileResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CarSliderResponse> getCarSliderImages() {
        List<CarSliderResponse> images = new ArrayList<>();


        List<Car> cars = carRepository.findAll().stream().toList();
        for (Car car: cars){
            if (car.getCarImage() != null){
                CarSliderResponse carSliderResponse = new CarSliderResponse();
                carSliderResponse.setCarId(car.getId());
                carSliderResponse.setBrand(car.getBrand());
                carSliderResponse.setEngine(car.getEngine());
                carSliderResponse.setModel(car.getModel());
                carSliderResponse.setUrl(carImageMapper.mapToFileResponse(car.getCarImage()).getUrl());
                images.add(carSliderResponse);
            }
        }
        return images;
    }

    @Override
    public void removeFile(String id) {

    }
}
