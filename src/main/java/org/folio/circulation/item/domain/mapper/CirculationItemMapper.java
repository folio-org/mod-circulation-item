package org.folio.circulation.item.domain.mapper;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.circulation.item.utils.DCBConstants;
import org.springframework.stereotype.Component;
import org.folio.circulation.item.domain.dto.ItemStatus;

import java.util.Date;
import java.util.Optional;

@Component
public class CirculationItemMapper {

  public CirculationItem mapEntityToDto(Item circulationItem) {

    if(circulationItem == null) return null;

    return CirculationItem.builder()
      .id(circulationItem.getId())
      .holdingsRecordId(circulationItem.getHoldingsRecordId())
      .status(ItemStatus.builder().name(ItemStatus.NameEnum.fromValue(circulationItem.getStatus())).date(new Date()).build())
      .materialTypeId(circulationItem.getMaterialTypeId())
      .permanentLoanTypeId(circulationItem.getPermanentLoanTypeId())
      .instanceTitle(circulationItem.getInstanceTitle())
      .barcode(circulationItem.getBarcode())
      .pickupLocation(circulationItem.getPickupLocation())
      .dcbItem(true)
      .effectiveLocationId(Optional.ofNullable(circulationItem.getEffectiveLocationId()).orElse(DCBConstants.LOCATION_ID))
      .lendingLibraryCode(circulationItem.getLendingLibraryCode())
      .build();
  }

  public Item mapDtoToEntity(CirculationItem circulationItem){
    return Item.builder()
      .id(circulationItem.getId())
      .holdingsRecordId(circulationItem.getHoldingsRecordId())
      .status(circulationItem.getStatus().getName().getValue())
      .materialTypeId(circulationItem.getMaterialTypeId())
      .permanentLoanTypeId(circulationItem.getPermanentLoanTypeId())
      .instanceTitle(circulationItem.getInstanceTitle())
      .barcode(circulationItem.getBarcode())
      .pickupLocation(circulationItem.getPickupLocation())
      .lendingLibraryCode(circulationItem.getLendingLibraryCode())
      .effectiveLocationId(circulationItem.getEffectiveLocationId())
      .build();
  }
}
