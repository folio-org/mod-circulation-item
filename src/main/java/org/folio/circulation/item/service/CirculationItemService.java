package org.folio.circulation.item.service;

import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.circulation.item.domain.dto.CirculationItems;

public interface CirculationItemService {
  CirculationItem getCirculationItemById(String id);

  CirculationItem getCirculationItemByBarcode(String barcode);

  CirculationItems getCirculationItems(String query, Integer offset, Integer limit);

  CirculationItem createCirculationItem(String circulationItemId, CirculationItem circulationItem);

  CirculationItem updateCirculationItem(String circulationItemId, CirculationItem circulationItem);


}
