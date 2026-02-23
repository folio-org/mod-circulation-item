package org.folio.circulation.item.config;

import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.invstorage.LocationsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Log4j2
@Configuration
public class HttpExchangeConfiguration {

  @Bean
  public LocationsClient locationsClient(HttpServiceProxyFactory factory) {
    return factory.createClient(LocationsClient.class);
  }
}
