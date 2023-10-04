package org.folio.circulation.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.service.CirculationItemsService;
import org.folio.rs.domain.dto.CirculationItem;
import org.folio.rs.rest.resource.CirculationItemIdApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.isNull;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/circulation-item/")
public class CirculationItemController implements CirculationItemIdApi {

  private final CirculationItemsService circulationItemsService;

  @Override
  public ResponseEntity<CirculationItem> createCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("createCirculationItem:: {}", circulationItem);
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(circulationItemsService.createCirculationItem(circulationItemId, circulationItem));
  }

  @Override
  public ResponseEntity<CirculationItem> retrieveCirculationItemById(String circulationItemId) {
    log.info("getCirculationItemById:: by id= {}", circulationItemId);
    var circulationItem = circulationItemsService.getCirculationItemById(circulationItemId);
    return isNull(circulationItem) ?
            ResponseEntity.notFound().build() :
            ResponseEntity.status(HttpStatus.OK)
                    .body(circulationItem);
  }

  @Override
  public ResponseEntity<CirculationItem> updateCirculationItem(String circulationItemId, CirculationItem circulationItem) {
    log.info("updateCirculationItem:: updating circulationItem by Request id= {} with entity id= {}", circulationItemId, circulationItem.getId());
    return ResponseEntity.status(HttpStatus.OK)
            .body(circulationItemsService.updateCirculationItem(circulationItemId, circulationItem));
  }
}
