package edu.miu.cse.drivewise;

import edu.miu.cse.drivewise.service.MakeService;
import edu.miu.cse.drivewise.service.CarModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class DrivewiseApplication {
    private final MakeService makeService;
    private final CarModelService modelService;

    public static void main(String[] args) {
        SpringApplication.run(DrivewiseApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner() {
//        return args -> {
//
////            List<String> modelNames = Arrays.asList("Camry","Corolla","RAV4", "RAV4 Hybrid");
//            MakeRequestDto makeRequestDto = new MakeRequestDto("Toyota");
//            Optional<MakeResponseDto> makeResponseDtoOptional = makeService.saveMake(makeRequestDto);
//
//
////            List<CarModelRequestDto> modelRequestDtos = modelNames.stream()
////                    .map(modelName->new CarModelRequestDto(modelName, makeRequestDto))
////                    .collect(Collectors.toList());
//            CarModelRequestDto modelRequestDto1 = new CarModelRequestDto("Camry", makeResponseDtoOptional);
//            modelService.saveModel(modelRequestDto1);
//            CarModelRequestDto modelRequestDto2 = new CarModelRequestDto("Corolla", makeRequestDto);
//            modelService.saveModel(modelRequestDto2);
//            CarModelRequestDto modelRequestDto3 = new CarModelRequestDto("RAV4", makeRequestDto);
//            modelService.saveModel(modelRequestDto2);
//
//
////            List<String> bmwmodelNames = Arrays.asList("1 Series","2 Series","3 Series", "3 Series Gran Turismo");
////            List<CarModelRequestDto> bmwmodelRequestDtos = bmwmodelNames.stream()
////                    .map(CarModelRequestDto::new)
////                    .collect(Collectors.toList());
////            MakeRequestDto bmwmakeRequestDto = new MakeRequestDto("BMW",bmwmodelRequestDtos);
////            System.out.println("Make request: " + bmwmakeRequestDto);
////            makeService.saveMake(bmwmakeRequestDto);
//
//        };
  //  }

}
