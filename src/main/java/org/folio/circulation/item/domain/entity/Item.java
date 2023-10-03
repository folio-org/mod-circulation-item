package org.folio.circulation.item.domain.entity;

import java.util.UUID;


import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.folio.circulation.item.domain.converter.UUIDConverter;
import org.folio.circulation.item.domain.entity.base.AuditableEntity;

@Entity
@Table(name = "circulation_item")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item extends AuditableEntity {

  @Id
  @Convert(converter = UUIDConverter.class)
  private UUID id;

  private  UUID holdingsRecordId;
  private String status;
  private String materialTypeId;
  private String permanentLoanTypeId;
  private String instanceTitle;
  private String itemBarcode;
  private String pickupLocation;

}
