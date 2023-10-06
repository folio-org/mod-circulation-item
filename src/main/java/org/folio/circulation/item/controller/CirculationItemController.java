package org.folio.circulation.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.service.CirculationItemService;
import org.folio.rs.domain.dto.CirculationItem;
import org.folio.rs.rest.resource.CirculationItemApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CirculationItemController implements CirculationItemApi {

  private final CirculationItemService circulationItemService;

  @Override
  public ResponseEntity<CirculationItem> createCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("createCirculationItem:: creating CirculationItem with Id {}", circulationItemId);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(circulationItemService.createCirculationItem(circulationItemId, circulationItem));
  }

  @Override
  public ResponseEntity<CirculationItem> retrieveCirculationItemById(String circulationItemId) {
    log.info("getCirculationItemById:: by id= {}", circulationItemId);
    var circulationItem = circulationItemService.getCirculationItemById(circulationItemId);
    return isNull(circulationItem) ?
            ResponseEntity.notFound().build() :
            ResponseEntity.status(HttpStatus.OK)
                    .body(circulationItem);
  }

  @Override
  public ResponseEntity<CirculationItem> updateCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("updateCirculationItem:: updating circulationItem by Request id= {} with entity id= {}", circulationItemId, circulationItem.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(circulationItemService.updateCirculationItem(circulationItemId, circulationItem));
  }
}
