package org.folio.circulation.item.repository;

import java.util.UUID;

import org.folio.circulation.item.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirculationItemsRepository extends JpaRepository<Item, UUID> {
}
