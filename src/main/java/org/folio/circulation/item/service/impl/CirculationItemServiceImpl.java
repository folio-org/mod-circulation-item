package org.folio.circulation.item.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.folio.circulation.item.invstorage.LocationsClient;
import org.folio.circulation.item.domain.dto.CirculationItems;
import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.domain.mapper.CirculationItemMapper;
import org.folio.circulation.item.exception.IdMismatchException;
import org.folio.circulation.item.repository.CirculationItemRepository;
import org.folio.circulation.item.service.CirculationItemService;
import org.folio.circulation.item.exception.ResourceAlreadyExistException;
import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.spring.data.OffsetRequest;
import org.folio.spring.exception.NotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CirculationItemServiceImpl implements CirculationItemService {

  private final CirculationItemRepository circulationItemRepository;

  private final CirculationItemMapper circulationItemMapper;
  private final LocationsClient locationsClient;

  @Override
  public CirculationItem getCirculationItemById(String id) {
    var item = getCirculationItemEntityOrNull(id);
    return circulationItemMapper.mapEntityToDto(item);
  }

  @Override
  public CirculationItem getCirculationItemByBarcode(String barcode) {
    var item = getCirculationItemEntityByBarcodeOrNull(barcode);
    return circulationItemMapper.mapEntityToDto(item);
  }

  @Override
  public CirculationItems getCirculationItems(String query, Integer offset, Integer limit) {
    var result = new CirculationItems();
    List<CirculationItem> circulationItems = circulationItemRepository.findByCql(query, OffsetRequest.of(offset, limit)).stream().map(circulationItemMapper::mapEntityToDto).toList();
    result.setItems(circulationItems);
    result.setTotalRecords(circulationItems.size());
    return result;
  }

  private Item getCirculationItemEntityOrNull(String id) {
    return circulationItemRepository.findById(UUID.fromString(id))
           .orElse(null);
  }

  private Item getCirculationItemEntityByBarcodeOrNull(String barcode) {
    return circulationItemRepository.findByBarcode(barcode)
      .orElse(null);
  }


  @Override
  public CirculationItem createCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    checkForIdMismatch(circulationItemId, circulationItem);
    validateEffectiveLocationId(circulationItem.getEffectiveLocationId());

    if(Objects.isNull(circulationItem.getId())){
      circulationItem.setId(UUID.fromString(circulationItemId));
    }

    if(checkIfItemExists(circulationItemId)){
      throw new ResourceAlreadyExistException(
        String.format("Unable to create circulation item with id %s as it already exists", circulationItemId));
    }

    if(checkIfItemExistsByBarcode(circulationItem.getBarcode())){
      throw new ResourceAlreadyExistException(
        String.format("Unable to create circulation item with barcode %s as it already exists", circulationItem.getBarcode()));
    }

    var circulationItemEntity = circulationItemMapper.mapDtoToEntity(circulationItem);
    circulationItemEntity.setCreatedDate(LocalDateTime.now());

    return circulationItemMapper.mapEntityToDto(circulationItemRepository.save(circulationItemEntity));
  }

  @Override
  public CirculationItem updateCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    checkForIdMismatch(circulationItemId, circulationItem);
    validateEffectiveLocationId(circulationItem.getEffectiveLocationId());

    if(!checkIfItemExists(circulationItemId)){
      throw new NotFoundException(
              String.format("Circulation item with id %s doesn't exist", circulationItemId));
    }

    var circulationItemEntity = circulationItemMapper.mapDtoToEntity(circulationItem);
    circulationItemEntity.setUpdatedDate(LocalDateTime.now());

    var updated = circulationItemRepository.save(circulationItemEntity);
    return circulationItemMapper.mapEntityToDto(updated);
  }

  private boolean checkIfItemExists(String circulationItemId) {
    return circulationItemRepository.existsById(UUID.fromString(circulationItemId));
  }

  private void validateEffectiveLocationId(String effectiveLocationId) {
    if(Objects.nonNull(effectiveLocationId)) {
      var location = locationsClient.findLocationById(effectiveLocationId);
      if (location.isEmpty()) {
        log.error("isEffectiveLocationIdNotExist:: Location Id does not exist: {}", effectiveLocationId);
        throw new IllegalArgumentException(
          String.format("EffectiveLocationId does not exist: %s", effectiveLocationId));
      }
    }
  }

  private boolean checkIfItemExistsByBarcode(String barcode) {
    return circulationItemRepository.findByBarcode(barcode).isPresent();
  }

  private static void checkForIdMismatch(String circulationItemId, CirculationItem circulationItem) {
    if(Objects.nonNull(circulationItem.getId()) &&
            !Objects.equals(circulationItemId, String.valueOf(circulationItem.getId()))) {
      throw new IdMismatchException(
              String.format("Request id= %s and entity id= %s are not equal", circulationItemId, circulationItem.getId())
      );
    }
  }
}
