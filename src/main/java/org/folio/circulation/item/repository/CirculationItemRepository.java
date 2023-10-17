package org.folio.circulation.item.repository;

import java.util.Optional;
import java.util.UUID;

import org.folio.circulation.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirculationItemRepository extends JpaRepository<Item, UUID> {
  Optional<Item> findByItemBarcode(String barcode);

}
