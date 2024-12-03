package edu.miu.cse.drivewise.mapper;

import edu.miu.cse.drivewise.dto.request.InventoryRequestDto;
import edu.miu.cse.drivewise.dto.response.InventoryResponseDto;
import edu.miu.cse.drivewise.model.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    Inventory inventoryRequestDtoToInventory(InventoryRequestDto inventoryRequestDto);

    InventoryResponseDto inventoryToInventoryResponseDto(Inventory inventory);
}
