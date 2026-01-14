package org.folio.circulation.item.domain.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import org.folio.circulation.item.domain.dto.CirculationItem;
import org.folio.circulation.item.domain.dto.ItemStatus;
import org.folio.circulation.item.domain.dto.ItemStatus.NameEnum;
import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.utils.DCBConstants;

@ExtendWith(MockitoExtension.class)
class CirculationItemMapperTest {

  @InjectMocks private CirculationItemMapper mapper;

  @Test
  void mapEntityToDto_positive_effectiveLocationIdIsPopulatedIfNull() {
    var effectiveLocationId = UUID.randomUUID().toString();
    var item = Item.builder()
      .status(NameEnum.AVAILABLE.getValue())
      .effectiveLocationId(effectiveLocationId)
      .build();

    var result = mapper.mapEntityToDto(item);
    assertThat(result.getEffectiveLocationId()).isEqualTo(effectiveLocationId);
  }


  @Test
  void mapEntityToDto_positive_effectiveLocationIdIsPopulatedIfNotNull() {
    var item = Item.builder()
      .status(NameEnum.AVAILABLE.getValue())
      .effectiveLocationId(null)
      .build();

    var result = mapper.mapEntityToDto(item);
    assertThat(result.getEffectiveLocationId()).isEqualTo(DCBConstants.LOCATION_ID);
  }

  @Test
  void mapDtoToEntity_positive_effectiveLocationIdIsPopulatedIfNull() {
    var effectiveLocationId = UUID.randomUUID().toString();
    var circItem = new CirculationItem()
      .status(ItemStatus.builder().name(NameEnum.AVAILABLE).build())
      .effectiveLocationId(effectiveLocationId);

    var result = mapper.mapDtoToEntity(circItem);
    assertThat(result.getEffectiveLocationId()).isEqualTo(effectiveLocationId);
  }

  @Test
  void mapDtoToEntity_positive_effectiveLocationIdIsPopulatedIfNotNull() {
    var circItem = new CirculationItem()
      .status(ItemStatus.builder().name(NameEnum.AVAILABLE).build())
      .effectiveLocationId(null);

    var result = mapper.mapDtoToEntity(circItem);
    assertThat(result.getEffectiveLocationId()).isEqualTo(DCBConstants.LOCATION_ID);
  }
}
