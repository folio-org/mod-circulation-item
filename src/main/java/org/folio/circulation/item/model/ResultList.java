package org.folio.circulation.item.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@JsonIgnoreProperties("resultInfo")
public class ResultList<T> {

  /**
   * Page number.
   */
  @JsonAlias("total_records")
  private int totalRecords = 0;

  /**
   * Paged result data.
   */
  private List<T> result = Collections.emptyList();
}