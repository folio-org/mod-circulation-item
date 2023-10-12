package org.folio.circulation.item.domain.mapper;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.circulation.item.domain.dto.CirculationItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CirculationItemMapper {

  CirculationItem mapEntityToDto(Item circulationItem);

  Item mapDtoToEntity(CirculationItem circulationItem);

}
