package org.folio.circulation.item.service;

import org.folio.rs.domain.dto.CirculationItem;

public interface CirculationItemService {
    CirculationItem getCirculationItemById(String id);

    CirculationItem createCirculationItem(String circulationItemId, org.folio.rs.domain.dto.CirculationItem circulationItem);

    CirculationItem updateCirculationItem(String circulationItemId, org.folio.rs.domain.dto.CirculationItem circulationItem);
}
