package org.folio.circulation.item.context;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.testcontainers.containers.PostgreSQLContainer;
import java.util.Objects;

import org.testcontainers.junit.jupiter.Container;


@Component
@Profile("testcontainers-pg")
public class PostgresTestContainersBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

  @Container
  static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>(
    Objects.toString(System.getenv("TESTCONTAINERS_POSTGRES_IMAGE"), "postgres:16-alpine"));

  @Override
  public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    postgreDBContainer.start();

    System.setProperty("DB_URL", postgreDBContainer.getJdbcUrl());
    System.setProperty("DB_USERNAME", postgreDBContainer.getUsername());
    System.setProperty("DB_PASSWORD", postgreDBContainer.getPassword());
  }
}
