package pl.wat.tai.carsharing.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.wat.tai.carsharing.data.entities.CarImage;
import pl.wat.tai.carsharing.data.response.CarImageResponse;
import pl.wat.tai.carsharing.data.response.CarSliderResponse;
import pl.wat.tai.carsharing.services.interfaces.CarImageService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/files")
@CrossOrigin
@RequiredArgsConstructor
public class CarImageController {
    private final CarImageService carImageService;

    @GetMapping
    public List<CarImageResponse> getAllCarImages() {
        return carImageService.getAllFiles();
    }

    @PostMapping
    public void uploadCarImage(@RequestParam("file") MultipartFile file) {
        carImageService.save(file);
    }

    @PostMapping("/named")
    public void uploadCarImage(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        carImageService.save(file, name);
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        Optional<CarImage> fileEntityOptional = carImageService.getFile(id);

        if (fileEntityOptional.isEmpty()) {
            return ResponseEntity.notFound()
                    .build();
        }

        CarImage fileEntity = fileEntityOptional.get();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getName() + "\"")
                .contentType(MediaType.valueOf(fileEntity.getContentType()))
                .body(fileEntity.getData());
    }

    @GetMapping("/slider")
    public List<CarSliderResponse> getCarSliderImages() {
        return carImageService.getCarSliderImages();
    }

    @DeleteMapping("{id}")
    public void removeFile(@PathVariable String id) {
        carImageService.removeFile(id);
    }
}