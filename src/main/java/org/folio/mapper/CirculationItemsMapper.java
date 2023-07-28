package org.folio.mapper;

import java.util.List;

import org.folio.domain.entity.Item;
import org.folio.rs.domain.dto.CirculationItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CirculationItemsMapper {

  @Mappings({ @Mapping(target = "id", source = "id"),
      @Mapping(target = "holdingsRecordId", source = "holdingsRecordId"),
      @Mapping(target = "status", source = "status"),
      @Mapping(target = "materialTypeId", source = "materialTypeId"),
      @Mapping(target = "permanentLoanTypeId", source = "permanentLoanTypeId"),
      @Mapping(target = "instanceTitle", source = "instanceTitle"),
      @Mapping(target = "itemBarcode", source = "itemBarcode"),
      @Mapping(target = "pickupLocation", source = "pickupLocation"),
      @Mapping(target = "metadata.createdDate", source = "createdDate"),
      @Mapping(target = "metadata.updatedDate", source = "updatedDate"),
      @Mapping(target = "metadata.createdByUserId", source = "createdByUserId"),
      @Mapping(target = "metadata.updatedByUserId", source = "updatedByUserId"),
      @Mapping(target = "metadata.createdByUsername", source = "createdByUsername"),
      @Mapping(target = "metadata.updatedByUsername", source = "updatedByUsername") })
  CirculationItem mapEntityToDto(Item circulationItem);

  @Mappings({
      @Mapping(target = "id", source = "id")})
  @InheritInverseConfiguration
  Item mapDtoToEntity(CirculationItem circulationItem);

  @Mappings({})
  List<CirculationItem> mapEntitiesToDtos(Iterable<Item> itemList);

}
