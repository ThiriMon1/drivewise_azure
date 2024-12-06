package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.client.ImageStorageClient;
import edu.miu.cse.drivewise.dto.request.InventoryRequestDto;
import edu.miu.cse.drivewise.dto.response.InventoryResponseDto;
import edu.miu.cse.drivewise.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InventoryController {
    private final InventoryService inventoryService;
    private final ImageStorageClient imageStorageClient;

    //for local
    @Value("${uploaddirectory}")
    private String dir;
    @Value("${prefixdir}")
    private String prefix;

    // Register inventory and save photo in azure blob storage
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(@RequestPart("car") @Valid InventoryRequestDto requestDto,
                                                                @RequestPart("photos") List<MultipartFile> photos) throws IOException {
        List<String> imageUrls=new ArrayList<>();
        for (MultipartFile photo : photos) {
            try(InputStream inputStream = photo.getInputStream()) {
                imageUrls.add(this.imageStorageClient.uploadImage(
                        photo.getOriginalFilename(),
                        inputStream,photo.getSize()
                ));
            }
        }
        Optional<InventoryResponseDto> inventoryResponseDtos=inventoryService.registerInventory(requestDto,imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryResponseDtos.get());
    }

    // Update an existing inventory
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @PathVariable Long inventoryId,
            @RequestPart("car") @Valid InventoryRequestDto requestDto,
            @RequestPart("photos") List<MultipartFile> photos) throws IOException {
        List<String> imageUrls=new ArrayList<>();
        for (MultipartFile photo : photos) {
            try(InputStream inputStream = photo.getInputStream()) {
                imageUrls.add(this.imageStorageClient.uploadImage(
                        photo.getOriginalFilename(),
                        inputStream,photo.getSize()
                ));
            }
        }
        Optional<InventoryResponseDto> inventoryResponseDtos=inventoryService.updateInventory(inventoryId,requestDto,imageUrls);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponseDtos.get());
    }

    // (optional) Register inventory and save photo in local (if you don't use Azure storage blob, photos will be saved in local directory)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping({"/local"})
    public ResponseEntity<InventoryResponseDto> createInventoryLocal(@RequestPart("car") @Valid InventoryRequestDto requestDto,
                                                                @RequestPart("photos") List<MultipartFile> photos) throws IOException {
        System.out.println("inside createInventoryLocal");
        System.out.println("Received Request DTO: " + requestDto);
        Path directory = Paths.get(System.getProperty(this.prefix), this.dir);
        List<String> imageUrls=new ArrayList<>();
        for (MultipartFile photo : photos) {

            String filename= UUID.randomUUID().toString()+photo.getOriginalFilename();
//            Path destinationPath = Paths.get(directory.toString(), fileName);
            // Copy file to destination
            Path destinationPath = Paths.get(directory.toString(), filename);
            // Copy file to destination
            Files.copy(photo.getInputStream(), destinationPath);
            imageUrls.add(filename);
        }

        Optional<InventoryResponseDto> inventoryResponseDtos=inventoryService.registerInventory(requestDto,imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryResponseDtos.get());
    }

    // (optional) Update an existing inventory and save photo in local (if you don't use Azure storage blob, photos will be saved in local directory)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping({"/local/{inventoryId}"})
    public ResponseEntity<InventoryResponseDto> updateInventoryLocal(
            @PathVariable Long inventoryId,
            @RequestPart("car") @Valid InventoryRequestDto requestDto,
            @RequestPart("photos") List<MultipartFile> photos) throws IOException {
        System.out.println("Received Request DTO: " + requestDto);
        Path directory = Paths.get(System.getProperty(this.prefix), this.dir);
        List<String> imageUrls=new ArrayList<>();
        for (MultipartFile photo : photos) {

            String filename= UUID.randomUUID().toString()+photo.getOriginalFilename();
//            Path destinationPath = Paths.get(directory.toString(), fileName);
            // Copy file to destination
            Path destinationPath = Paths.get(directory.toString(), filename);
            // Copy file to destination
            Files.copy(photo.getInputStream(), destinationPath);
            imageUrls.add(filename);
        }

        Optional<InventoryResponseDto> inventoryResponseDtos=inventoryService.updateInventory(inventoryId,requestDto,imageUrls);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryResponseDtos.get());
    }

    // Get all inventories
    @GetMapping
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories(){
        List<InventoryResponseDto> inventoryResponseDtos =inventoryService.getAllInventories();
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponseDtos);
    }

    // Get inventories by filter
    @GetMapping("/filter-car")
    public ResponseEntity<List<InventoryResponseDto>> getAllInventoriesWithFilterCar(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String stateName,
            @RequestParam(required = false) String carCondition,
            @RequestParam(required = false) String style,
            @RequestParam(required = false) Integer minMileage,
            @RequestParam(required = false) Integer maxMileage,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String fuelType

    ){
            List<InventoryResponseDto> inventoryResponseDtos = inventoryService.getAllInventoriesWithFilterCar(make,model
                    ,stateName,carCondition,style,minMileage,maxMileage,minPrice,maxPrice,minYear,maxYear,fuelType);
            return ResponseEntity.status(HttpStatus.OK).body(inventoryResponseDtos);
    }

    // Get inventories by car condition
    @GetMapping("/{car-condition}")
    public ResponseEntity<List<InventoryResponseDto>> getInventoriesByCarCondition(
            @PathVariable(name = "car-condition") String carCondition){
        List<InventoryResponseDto> inventoryResponseDtos = inventoryService.getInventoriesByCarCondition(carCondition);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponseDtos);
    }

    // Get inventories by inventory id
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long inventoryId){
        inventoryService.deleteInventory(inventoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
