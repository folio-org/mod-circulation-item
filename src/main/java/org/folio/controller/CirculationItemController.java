package org.folio.controller;

import static java.util.Objects.isNull;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.folio.rs.domain.dto.CirculationItem;
import org.folio.rs.rest.resource.ItemIdApi;
import org.folio.service.CirculationItemsService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/circulation-item/")
public class CirculationItemController implements ItemIdApi {
  private static final String CONFIGURATION_NOT_FOUND = "Configuration not found";

  private final CirculationItemsService circulationItemsService;

  @Override
  public ResponseEntity<String> deleteCirculationItemById(String configId) {
    circulationItemsService.deleteCirculationItemById(configId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<CirculationItem> getCirculationItemById(String itemId) {
    var circulationItem = circulationItemsService.getCirculationItemById(itemId);
    return isNull(circulationItem) ? ResponseEntity.notFound().build() : new ResponseEntity<>(circulationItem, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<String> putCirculationItem(@Pattern(regexp = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[1-5][a-fA-F0-9]{3}-[89abAB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}$") String itemId,
    @Valid CirculationItem storageConfiguration) {
    circulationItemsService.updateCirculationItem(itemId, storageConfiguration);
    return ResponseEntity.noContent().build();
  }

//  @Override
//  public ResponseEntity<CirculationItem> postCirculationItem(@Valid CirculationItem circulationItem) {
//    var item = circulationItemsService.postCirculationItem(circulationItem);
//    return new ResponseEntity<>(item, HttpStatus.CREATED);
//  }

  @ExceptionHandler({EmptyResultDataAccessException.class, EntityNotFoundException.class})
  public ResponseEntity<String> handleNotFoundExceptions() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONFIGURATION_NOT_FOUND);
  }
}
