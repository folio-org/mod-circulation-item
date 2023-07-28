package org.folio.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "circulation_item")
@Entity
@NoArgsConstructor
public class Item {

  @Id
  private UUID id;

  @Column(name = "holdings_record_id")
  private  UUID holdingsRecordId;

  @Column(name="status")
  private String status;

  @Column(name="material_type_id")
  private String materialTypeId;

  @Column(name="permanent_loan_type_id")
  private String permanentLoanTypeId;

  @Column(name = "instance_title")
  private String instanceTitle;

  @Column(name = "item_barcode")
  private String itemBarcode;

  @Column(name = "pickup_location")
  private String pickupLocation;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "updated_date")
  private LocalDateTime updatedDate;

  @Column(name = "created_by_user_id")
  private UUID createdByUserId;

  @Column(name = "updated_by_user_id")
  private UUID updatedByUserId;

  @Column(name = "created_by_username")
  private String createdByUsername;

  @Column(name = "updated_by_username")
  private String updatedByUsername;

}
