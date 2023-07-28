package org.folio.repository;

import java.util.UUID;

import org.folio.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CirculationItemsRepository extends JpaRepository<Item, UUID> {
}
