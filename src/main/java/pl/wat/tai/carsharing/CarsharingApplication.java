package pl.wat.tai.carsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.wat.tai.carsharing.data.entities.User;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CarsharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarsharingApplication.class, args);
    }

}
