package edu.miu.cse.drivewise.controller;

import edu.miu.cse.drivewise.dto.response.FavoriteResponse;
import edu.miu.cse.drivewise.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    private CustomerController customerController;

    @Test
    void addToFavorites_success() {
        Long userId = 1L; Long inventoryId = 2L;
        Mockito.when(customerService.addToFavorites(userId, inventoryId)).thenReturn("success");
        ResponseEntity<String> res =customerController.addToFavorites(userId,inventoryId);
        assert res.getStatusCode()== HttpStatus.CREATED;
    }

    @Test
    void getFavorites_returnFavoritesList() {
        Long userId = 1L;
        List<FavoriteResponse> favoriteResponses = new ArrayList<>();
        favoriteResponses.add(new FavoriteResponse(1L,"Toyota","Corolla",2016,100000,"Chicago","Illinois",17900,18000,"AVAILABLE",List.of("picture1.png","picture2.png")));

        Mockito.when(customerService.getFavorites(userId)).thenReturn(favoriteResponses);
        ResponseEntity<List<FavoriteResponse>> res =customerController.getFavorites(userId);
        assert res.getStatusCode()== HttpStatus.OK;
    }

    @Test
    void removeFromFavorites_success() {
        Long userId = 1L; Long inventoryId = 2L;

        Mockito.when(customerService.removeFromFavorites(userId, inventoryId)).thenReturn("success");
        ResponseEntity<String> res =customerController.removeFromFavorites(userId,inventoryId);
        assert res.getStatusCode()== HttpStatus.NO_CONTENT;
    }
}