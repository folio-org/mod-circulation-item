package org.folio.circulation.item.service;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.domain.mapper.CirculationItemsMapper;
import org.folio.circulation.item.exception.IdMismatchException;
import org.folio.circulation.item.exception.ResourceAlreadyExistException;
import org.folio.circulation.item.repository.CirculationItemsRepository;
import org.folio.rs.domain.dto.CirculationItem;
import org.folio.spring.exception.NotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CirculationItemsService {

  private final CirculationItemsRepository circulationItemsRepository;

  private final CirculationItemsMapper circulationItemsMapper;

  public CirculationItem getCirculationItemById(String id) {
    var item = getCirculationItemEntityOrNull(id);
    return circulationItemsMapper.mapEntityToDto(item);
  }

  private Item getCirculationItemEntityOrNull(String id) {
    return circulationItemsRepository.findById(UUID.fromString(id))
           .orElse(null);
  }


    public CirculationItem createCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    if(Objects.nonNull(circulationItem.getId()) &&
            !Objects.equals(circulationItemId, String.valueOf(circulationItem.getId()))) {
      throw new IdMismatchException(
              String.format("Request id= %s and entity id= %s are not equal", circulationItemId, circulationItem.getId())
      );
    }

    if(Objects.isNull(circulationItem.getId())){
      circulationItem.setId(UUID.fromString(circulationItemId));
    }

    if(checkIfItemExists(circulationItemId)){
      throw new ResourceAlreadyExistException(
        String.format("Unable to create circulation item with id %s as it already exists", circulationItemId));
    }

    var circulationItemEntity = circulationItemsMapper.mapDtoToEntity(circulationItem);
    circulationItemEntity.setCreatedDate(LocalDateTime.now());

    return circulationItemsMapper.mapEntityToDto(circulationItemsRepository.save(circulationItemEntity));
  }

  public CirculationItem updateCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    if (!Objects.equals(circulationItemId, String.valueOf(circulationItem.getId()))) {
      throw new IdMismatchException(
              String.format("Request id= %s and entity id= %s are not equal", circulationItemId, circulationItem.getId())
      );
    }

    if(!checkIfItemExists(circulationItemId)){
      throw new NotFoundException(
              String.format("Circulation item with id %s doesn't exist", circulationItemId));
    }

    var circulationItemEntity = circulationItemsMapper.mapDtoToEntity(circulationItem);
    circulationItemEntity.setUpdatedDate(LocalDateTime.now());

    var updated = circulationItemsRepository.save(circulationItemEntity);
    return circulationItemsMapper.mapEntityToDto(updated);
  }

  private boolean checkIfItemExists(String circulationItemId) {
    return circulationItemsRepository.existsById(UUID.fromString(circulationItemId));
  }
}
