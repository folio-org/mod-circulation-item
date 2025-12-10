package org.folio.circulation.item.client.feign;

import java.util.List;

import org.folio.circulation.item.model.ResultList;
import org.folio.spring.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@FeignClient(name = "locations", configuration = FeignClientConfiguration.class)
public interface LocationsClient {

  @GetMapping
  ResultList<LocationDTO> findLocationByQuery(
    @RequestParam("query") String query,
    @RequestParam("includeShadowLocations") Boolean includeShadowLocations,
    @RequestParam("limit") int limit,
    @RequestParam("offset") int offset);

  @GetMapping("/{locationId}")
  LocationDTO findLocationById(@PathVariable("locationId") String locationId);


  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  class LocationDTO {
    private String id;
    private String name;
    private String code;
    private String institutionId;
    private String campusId;
    private String libraryId;
    private String primaryServicePoint;
    private List<String> servicePointIds;
    @Builder.Default
    @JsonProperty("isShadow")
    private boolean isShadow = false;
  }

}
