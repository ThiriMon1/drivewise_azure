package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.response.FavoriteResponse;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.model.*;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setUserId(1L);
        customer.setFavoriteInventories(new java.util.ArrayList<>());

        Make make = new Make();
        CarModel model=new CarModel();
        model.setModelId(1L);
        make.setMakeId(1L);
        make.setMakeName("Toyota");
        model.setMake(make);
        make.setModelList(List.of(model));

        City city = new City();
        city.setCityName("Chicago");
        State state=new State();
        state.setStateName("IL");
        List<PriceHistory> priceHistoryList = new ArrayList<>();
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setPrice(19000);
        priceHistoryList.add(priceHistory);

        inventory = new Inventory();
        inventory.setId(2L);
        inventory.setMake(make);
        inventory.setModel(model);
        inventory.setYear(2023);
        inventory.setMileage(100000);
        inventory.setCity(city);
        inventory.setState(state);
        inventory.setPriceHistory(priceHistoryList);
        inventory.setCurrentPrice(19000);
        inventory.setStatus(Status.AVAILABLE);
        inventory.setPhotos(List.of("picture1.jpg", "picture2.jpg"));
    }

    @Test
    void addToFavorites_success() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(inventoryRepository.findById(2L)).thenReturn(Optional.of(inventory));

        String response = customerService.addToFavorites(1L, 2L);

        assertEquals("Inventory added to Favorites successfully", response);
        verify(customerRepository, times(1)).save(customer);
        assertTrue(customer.getFavoriteInventories().contains(inventory));
    }

    @Test
    void addToFavorites_AlreadyExists() {
        customer.getFavoriteInventories().add(inventory);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(inventoryRepository.findById(2L)).thenReturn(Optional.of(inventory));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.addToFavorites(1L, 2L);
        });

        assertEquals("Inventory 2is already in favorite list", exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void removeFromFavorites_success() {
        customer.getFavoriteInventories().add(inventory);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(inventoryRepository.findById(2L)).thenReturn(Optional.of(inventory));

        String response = customerService.removeFromFavorites(1L, 2L);

        assertEquals("Inventory remove from Favorites successfully", response);
        verify(customerRepository, times(1)).save(customer);
        assertFalse(customer.getFavoriteInventories().contains(inventory));
    }

    @Test
    void removeFromFavorites_NotExists() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(inventoryRepository.findById(2L)).thenReturn(Optional.of(inventory));

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.removeFromFavorites(1L, 2L);
        });

        assertEquals("Inventory 2is not in favorite list", exception.getMessage());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void getFavorites() {
        customer.getFavoriteInventories().add(inventory);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        List<FavoriteResponse> favorites = customerService.getFavorites(1L);

        assertEquals(1, favorites.size());
        assertEquals(inventory.getId(), favorites.get(0).inventoryId());
    }

    @Test
    void getFavorites_UserNotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            customerService.getFavorites(1L);
        });

        assertEquals("1 is not found", exception.getMessage());
    }
}
