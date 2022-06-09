package pl.wat.tai.carsharing.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.response.CarImageResponse;

import java.util.List;
import java.util.Optional;

public interface CarImageService {

    void save(MultipartFile file);

    void save(MultipartFile file, String name);

    Optional<CarImage> getFile(String id);

    List<CarImageResponse> getAllFiles();

}
