package edu.miu.cse.drivewise.service;

import edu.miu.cse.drivewise.dto.response.FavoriteResponse;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    String addToFavorites(Long userId, Long inventoryId);
    String removeFromFavorites(Long userId, Long inventoryId);
    List<FavoriteResponse> getFavorites(Long userId);

}
