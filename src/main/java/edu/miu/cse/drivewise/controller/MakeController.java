package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.request.MakeRequestDto;
import edu.miu.cse.drivewise.dto.response.MakeResponseDto;
import edu.miu.cse.drivewise.service.MakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/makes")
@RequiredArgsConstructor
public class MakeController {
    private final MakeService makeService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<MakeResponseDto> createMake(
            @Valid
            @RequestBody MakeRequestDto makeRequestDto) {
        Optional<MakeResponseDto> makeResponseDto = makeService.saveMake(makeRequestDto);
        if (makeResponseDto.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(makeResponseDto.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @GetMapping
    public ResponseEntity<List<MakeResponseDto>> getAllMakes() {
        List<MakeResponseDto> makeResponseDtos = makeService.findAllMake();
        return ResponseEntity.status(HttpStatus.OK).body(makeResponseDtos);
    }

    @GetMapping("/{makename}")
    public ResponseEntity<MakeResponseDto> getMakeByName(@PathVariable String makename) {
        Optional<MakeResponseDto> makeResponseDto = makeService.findMakeByName(makename);
        return ResponseEntity.status(HttpStatus.OK).body(makeResponseDto.get());
    }
}
