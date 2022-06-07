package pl.wat.tai.carsharing.mappers;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.wat.tai.carsharing.data.CarImageResponse;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.repositories.CarImageRepository;

public class CarImageMapper {


    public CarImageResponse mapToFileResponse(CarImage fileEntity) {
        String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileEntity.getId())
                .toUriString();
        CarImageResponse fileResponse = new CarImageResponse();
        fileResponse.setId(fileEntity.getId());
        fileResponse.setName(fileEntity.getName());
        fileResponse.setContentType(fileEntity.getContentType());
        fileResponse.setSize(fileEntity.getSize());
        fileResponse.setUrl(downloadURL);

        return fileResponse;
    }
}
