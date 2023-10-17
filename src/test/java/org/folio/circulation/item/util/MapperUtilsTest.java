package org.folio.circulation.item.util;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MapperUtilsTest {

  @Test
  void stringToUUIDSafeSuccess() {
    String uuid = "698aa428-4e7c-45e5-81a6-992a256e88fd";
    assertEquals(MapperUtils.stringToUUIDSafe(uuid).toString(), uuid);
  }

  @Test
  void uuidToStringSafe() {
    String uuid = "698aa428-4e7c-45e5-81a6-992a256e88fd";
    assertEquals(MapperUtils.uuidToStringSafe(UUID.fromString(uuid)), uuid);
  }
}
