package edu.miu.cse.drivewise.service.impl;

import edu.miu.cse.drivewise.dto.response.FavoriteResponse;
import edu.miu.cse.drivewise.exception.resourcenotfound.ResourceNotFoundException;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.model.Inventory;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.repository.InventoryRepository;
import edu.miu.cse.drivewise.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final InventoryRepository inventoryRepository;

    @Override
    public String addToFavorites(Long userId, Long inventoryId) {
        // Fetch customer, inventory
        Customer customer = getCustomer(userId);
        Inventory inventory = getInventory(inventoryId);

        // Add a new favorite to favorite list if inventory is not yet.
        if (!customer.getFavoriteInventories().contains(inventory)) {
            customer.getFavoriteInventories().add(inventory);
            customerRepository.save(customer);
            return "Inventory added to Favorites successfully";
        } else {
            throw new ResourceNotFoundException("Inventory " + inventoryId + "is already in favorite list");
        }


    }

    @Override
    public String removeFromFavorites(Long userId, Long inventoryId) {
        // Fetch customer, inventory
        Customer customer = getCustomer(userId);
        Inventory inventory = getInventory(inventoryId);

        // Remove a new favorite from favorite list if inventory is existed
        if (customer.getFavoriteInventories().contains(inventory)) {
            customer.getFavoriteInventories().remove(inventory);
            customerRepository.save(customer);
            return "Inventory remove from Favorites successfully";
        } else {
            throw new ResourceNotFoundException("Inventory " + inventoryId + "is not in favorite list");
        }

    }

    @Override
    public List<FavoriteResponse> getFavorites(Long userId) {
        // Fetch customer
        Customer customer = getCustomer(userId);
        List<FavoriteResponse> favorites = new ArrayList<>();

        for (Inventory inventory : customer.getFavoriteInventories()) {
            favorites.add(mapToFavoriteResponse(inventory));
        }
        return favorites;
    }

    private Customer getCustomer(Long userId) {
        return customerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(userId + " is not found"));
    }

    private Inventory getInventory(Long inventoryId) {
        return inventoryRepository.findById(inventoryId).orElseThrow(() -> new ResourceNotFoundException(inventoryId + " is not found"));
    }

    private FavoriteResponse mapToFavoriteResponse(Inventory inventory) {
        return new FavoriteResponse(
                inventory.getId(), inventory.getMake().getMakeName(), inventory.getModel().getModelName(), inventory.getYear(), inventory.getMileage(),
                inventory.getCity().getCityName(), inventory.getState().getStateName(), inventory.getPriceHistory().getLast().getPrice(), inventory.getCurrentPrice(),
                inventory.getStatus().name(), inventory.getPhotos());
    }
}
