package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.CarModelRequestDto;
import edu.miu.cse.drivewise.dto.response.CarModelResponseDto;
import edu.miu.cse.drivewise.service.CarModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/models")
@RequiredArgsConstructor
public class ModelController {
    private final CarModelService modelService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{makename}")
    public ResponseEntity<CarModelResponseDto> createModel(@PathVariable String makename,
                                                          @Valid
            @RequestBody CarModelRequestDto modelRequestDto) {
        Optional<CarModelResponseDto> modelResponseDto = modelService.saveModel(makename, modelRequestDto);
        if (modelResponseDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(modelResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping("/{makename}")
    public ResponseEntity<List<CarModelResponseDto>> getModelsByMakeName(@PathVariable String makename) {
        List<CarModelResponseDto> carModelResponseDtos=modelService.getCarModelsByMakeName(makename);
        return ResponseEntity.status(HttpStatus.OK).body(carModelResponseDtos);
    }
}
