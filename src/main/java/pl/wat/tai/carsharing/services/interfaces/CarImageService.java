package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.CarImageResponse;
import pl.wat.tai.carsharing.data.entities.CarImage;

import java.util.List;
import java.util.Optional;

public interface CarImageService {
    void save(MultipartFile file);

    Optional<CarImage> getFile(String id);

    List<CarImageResponse> getAllFiles();

}
