package org.folio.circulation.item.client;

import java.util.Optional;

import org.folio.circulation.item.client.model.Location;
import org.folio.circulation.item.model.ResultList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(url = "locations")
public interface LocationsClient {

  @GetExchange
  ResultList<Location> findLocationByQuery(
    @RequestParam("query") String query,
    @RequestParam("includeShadowLocations") Boolean includeShadowLocations,
    @RequestParam("limit") int limit,
    @RequestParam("offset") int offset);

  @GetExchange("/{locationId}")
  Optional<Location> findLocationById(@PathVariable("locationId") String locationId);
}
