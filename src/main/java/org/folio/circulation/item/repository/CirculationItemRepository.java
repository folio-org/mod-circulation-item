package org.folio.circulation.item.repository;

import java.util.Optional;
import java.util.UUID;

import org.folio.circulation.item.domain.entity.Item;
import org.folio.spring.cql.JpaCqlRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirculationItemRepository extends JpaCqlRepository<Item, UUID> {
  Optional<Item> findByBarcode(String barcode);
}
