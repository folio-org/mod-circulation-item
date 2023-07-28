package org.folio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableAutoConfiguration
public class ModCirculationItemApplication {

  public static void main(String[] args) {
    SpringApplication.run(ModCirculationItemApplication.class, args);
  }
}
