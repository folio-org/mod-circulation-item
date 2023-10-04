package org.folio.circulation.item.utils;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.rs.domain.dto.CirculationItem;

import java.util.UUID;

public class EntityUtils {

  public static UUID HOLDINGS_RECORD_ID = UUID.fromString("698aa428-4e7c-45e5-81a6-992a256e88fd");
  public static CirculationItem createCirculationItem(UUID id) {
    return CirculationItem.builder()
            .id(id)
            .holdingsRecordId(HOLDINGS_RECORD_ID)
            .status("TEST")
            .materialTypeId("materialTypeId_TEST")
            .permanentLoanTypeId("permanentLoanTypeId_TEST")
            .instanceTitle("instanceTitle_TEST")
            .itemBarcode("itemBarcode_TEST")
            .pickupLocation("pickupLocation_TEST")
            .build();
  }

  public static CirculationItem createCirculationItemForUpdate(UUID id) {
    return CirculationItem.builder()
            .id(id)
            .holdingsRecordId(HOLDINGS_RECORD_ID)
            .status("TEST_UPD")
            .materialTypeId("materialTypeId_TEST_UPD")
            .permanentLoanTypeId("permanentLoanTypeId_TEST_UPD")
            .instanceTitle("instanceTitle_TEST_UPD")
            .itemBarcode("itemBarcode_TEST_UPD")
            .pickupLocation("pickupLocation_TEST_UPD")
            .build();
  }

  public static Item createCirculationEntityItemForUpdate(UUID id) {
    return Item.builder()
            .id(id)
            .holdingsRecordId(HOLDINGS_RECORD_ID)
            .status("TEST_UPD")
            .materialTypeId("materialTypeId_TEST_UPD")
            .permanentLoanTypeId("permanentLoanTypeId_TEST_UPD")
            .instanceTitle("instanceTitle_TEST_UPD")
            .itemBarcode("itemBarcode_TEST_UPD")
            .pickupLocation("pickupLocation_TEST_UPD")
            .build();
  }  public static Item createCirculationEntityItem(UUID id) {
    return Item.builder()
            .id(id)
            .holdingsRecordId(HOLDINGS_RECORD_ID)
            .status("TEST")
            .materialTypeId("materialTypeId_TEST")
            .permanentLoanTypeId("permanentLoanTypeId_TEST")
            .instanceTitle("instanceTitle_TEST")
            .itemBarcode("itemBarcode_TEST")
            .pickupLocation("pickupLocation_TEST")
            .build();
  }


  /*public static TransactionStatus createTransactionStatus(TransactionStatus.StatusEnum statusEnum){
    return TransactionStatus.builder().status(statusEnum).build();
  }*/

/*  public static TransactionEntity createTransactionEntity() {
    return TransactionEntity.builder()
      .id(DCB_TRANSACTION_ID)
      .itemId(ITEM_ID)
      .itemTitle("ITEM TITLE")
      .itemBarcode("DCB_ITEM")
      .patronId(PATRON_ID)
      .patronBarcode("DCB_PATRON")
      .patronGroup("staff")
      .pickupLocation("PICKUP LOCATION")
      .materialType("book")
      .lendingLibraryCode("LEN")
      .borrowingLibraryCode("BOR")
      .build();
  }*/


/*  @SneakyThrows
  public static String getMockDataAsString(String path) {

    try (InputStream resourceAsStream = DcbApplication.class.getClassLoader().getResourceAsStream(path)) {
      if (resourceAsStream != null) {
        return IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
      } else {
        StringBuilder sb = new StringBuilder();
        try (Stream<String> lines = Files.lines(Paths.get(path))) {
          lines.forEach(sb::append);
        }
        return sb.toString();
      }
    }
  }*/

}
