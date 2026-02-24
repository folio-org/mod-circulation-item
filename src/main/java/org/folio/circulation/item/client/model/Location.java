package org.folio.circulation.item.client.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

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
  @Getter(onMethod_ = @JsonIgnore)
  private boolean isShadow = false;
}
