package org.folio.service;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.UUID;

import org.folio.domain.entity.Item;
import org.folio.mapper.CirculationItemsMapper;
import org.folio.repository.CirculationItemsRepository;
import org.folio.rs.domain.dto.CirculationItem;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CirculationItemsService {

  private final CirculationItemsRepository circulationItemsRepository;

  private final CirculationItemsMapper circulationItemsMapper;

  public void deleteCirculationItemById(String id) {
    circulationItemsRepository.deleteById(UUID.fromString(id));
  }

  public CirculationItem getCirculationItemById(String id) {
    return circulationItemsRepository.findById(UUID.fromString(id))
      .map(circulationItemsMapper::mapEntityToDto)
      .orElse(null);
  }

  public CirculationItem postCirculationItem(CirculationItem circulationItem) {
    if (isNull(circulationItem.getId())) {
      circulationItem.id(UUID.randomUUID());
    }
    var configuration = circulationItemsMapper.mapDtoToEntity(circulationItem);
    configuration.setCreatedDate(LocalDateTime.now());

    return circulationItemsMapper.mapEntityToDto(circulationItemsRepository.save(configuration));
  }

  public static String randomIdAsString() {
    return UUID.randomUUID().toString();
  }


  public CirculationItem updateCirculationItem(String id, CirculationItem circulationItem) {
    Item item;
    if (id.equals(circulationItem.getId())) {
      var conf = circulationItemsMapper.mapDtoToEntity(circulationItem);
      item = circulationItemsRepository.save(copyForUpdate(circulationItemsRepository.getOne(conf.getId()), conf));
    } else {
      throw new IdMismatchException();
    }
    return circulationItemsMapper.mapEntityToDto(item);
  }

  public class IdMismatchException extends RuntimeException {
    public IdMismatchException() {
      super("request id and entity id are not equal");
    }
  }

  private Item copyForUpdate(Item dest, Item source) {
    dest.setHoldingsRecordId(source.getHoldingsRecordId());
    dest.setStatus(source.getStatus());
    dest.setInstanceTitle(source.getInstanceTitle());
    dest.setItemBarcode(source.getItemBarcode());
    dest.setMaterialTypeId(source.getMaterialTypeId());
    dest.setPickupLocation(source.getPickupLocation());
    dest.setPermanentLoanTypeId(source.getPermanentLoanTypeId());
    dest.setUpdatedByUserId(source.getUpdatedByUserId());
    dest.setUpdatedByUsername(source.getUpdatedByUsername());
    dest.setUpdatedDate(LocalDateTime.now());
    return dest;
  }
}
