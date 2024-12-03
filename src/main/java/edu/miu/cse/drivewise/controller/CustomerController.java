package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.response.FavoriteResponse;
import edu.miu.cse.drivewise.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/{userId}/favorite-lists")
    public ResponseEntity<String> addToFavorites(
            @PathVariable Long userId,
            @RequestParam(name = "inventoryId") Long inventoryId
            ) {
        String result= customerService.addToFavorites(userId,inventoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{userId}/favorite-lists")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@PathVariable Long userId) {
        List<FavoriteResponse> response = customerService.getFavorites(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{userId}/favorite-lists")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long userId, @RequestParam(name = "inventoryId") Long inventoryId) {
        String result =customerService.removeFromFavorites(userId,inventoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
}
