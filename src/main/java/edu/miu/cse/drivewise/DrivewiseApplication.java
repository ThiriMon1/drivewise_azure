package edu.miu.cse.drivewise;

import edu.miu.cse.drivewise.service.MakeService;
import edu.miu.cse.drivewise.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DrivewiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DrivewiseApplication.class, args);
    }

}
