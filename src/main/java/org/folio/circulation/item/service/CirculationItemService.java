package org.folio.circulation.item.service;

import org.folio.circulation.item.domain.dto.CirculationItem;

public interface CirculationItemService {
    CirculationItem getCirculationItemById(String id);

    CirculationItem getCirculationItemByBarcode(String barcode);

    CirculationItem createCirculationItem(String circulationItemId, org.folio.circulation.item.domain.dto.CirculationItem circulationItem);

    CirculationItem updateCirculationItem(String circulationItemId, org.folio.circulation.item.domain.dto.CirculationItem circulationItem);


}
