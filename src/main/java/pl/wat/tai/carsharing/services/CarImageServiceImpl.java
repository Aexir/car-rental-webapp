package pl.wat.tai.carsharing.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.response.CarImageResponse;
import pl.wat.tai.carsharing.mappers.CarImageMapper;
import pl.wat.tai.carsharing.repositories.CarImageRepository;
import pl.wat.tai.carsharing.services.interfaces.CarImageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService {

    private final CarImageRepository carRepository;
    private final CarImageMapper carImageMapper;

    @Override
    public void save(MultipartFile file) {
        try {
            CarImage carImage = new CarImage();
            carImage.setName(StringUtils.cleanPath(file.getOriginalFilename()));
            carImage.setContentType(file.getContentType());
            carImage.setData(file.getBytes());
            carImage.setSize(file.getSize());
            carRepository.save(carImage);
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
            carRepository.save(carImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<CarImage> getFile(String id) {
        return carRepository.findById(id);
    }

    @Override
    public List<CarImageResponse> getAllFiles() {
        return carRepository.findAll().stream()
                .map(carImageMapper::mapToFileResponse)
                .collect(Collectors.toList());
    }
}
