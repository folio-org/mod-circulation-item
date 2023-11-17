package org.folio.circulation.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.domain.dto.CirculationItems;
import org.folio.circulation.item.service.CirculationItemService;
import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.circulation.item.rest.resource.CirculationItemApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CirculationItemController implements CirculationItemApi {

  private final CirculationItemService circulationItemServiceImpl;

  @Override
  public ResponseEntity<CirculationItem> createCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("createCirculationItem:: creating CirculationItem with Id {}", circulationItemId);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(circulationItemServiceImpl.createCirculationItem(circulationItemId, circulationItem));
  }

  @Override
  public ResponseEntity<CirculationItem> retrieveCirculationItemById(String circulationItemId) {
    log.info("getCirculationItemById:: by id= {}", circulationItemId);
    var circulationItem = circulationItemServiceImpl.getCirculationItemById(circulationItemId);
    return isNull(circulationItem) ?
            ResponseEntity.notFound().build() :
            ResponseEntity.status(HttpStatus.OK)
                    .body(circulationItem);
  }

  @Override
  public ResponseEntity<CirculationItems> getCirculationItemsByQuery(String query, Integer offset, Integer limit) {
    return ResponseEntity.status(HttpStatus.OK)
      .body(circulationItemServiceImpl.getCirculationItems(query, offset, limit));
  }

  @Override
  public ResponseEntity<CirculationItem> getCirculationItemByBarcode(String barcode) {
    log.info("getCirculationItemByBarcode:: get circulationItem by barcode = {}", barcode);
    var circulationItem = circulationItemServiceImpl.getCirculationItemByBarcode(barcode);
    return isNull(circulationItem) ?
      ResponseEntity.notFound().build() :
      ResponseEntity.status(HttpStatus.OK)
        .body(circulationItem);
  }

  @Override
  public ResponseEntity<CirculationItem> updateCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("updateCirculationItem:: updating circulationItem by Request id= {} with entity id= {}", circulationItemId, circulationItem.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(circulationItemServiceImpl.updateCirculationItem(circulationItemId, circulationItem));
  }
}
