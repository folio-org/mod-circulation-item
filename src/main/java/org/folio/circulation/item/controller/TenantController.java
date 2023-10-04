package org.folio.circulation.item.controller;

import jakarta.validation.Valid;
import liquibase.exception.LiquibaseException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.folio.circulation.item.service.CirculationItemsService;
import org.folio.spring.FolioExecutionContext;
import org.folio.spring.liquibase.FolioSpringLiquibase;
import org.folio.spring.service.TenantService;
import org.folio.tenant.domain.dto.TenantAttributes;
import org.folio.tenant.rest.resource.TenantApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Log4j2
@RestController("folioTenantController")
@RequestMapping
@RequiredArgsConstructor
public class TenantController implements TenantApi {

  private final FolioSpringLiquibase folioSpringLiquibase;
  private final FolioExecutionContext context;
  private final TenantService tenantService;

  @SneakyThrows
  @Override
  public ResponseEntity<Void> postTenant(@Valid TenantAttributes tenantAttributes) {
    var tenantId = context.getTenantId();

    log.info("Here came..");
    if (folioSpringLiquibase != null) {
      log.info("folioSpringLiquibase is not null");

      var schemaName = context.getFolioModuleMetadata()
        .getDBSchemaName(tenantId);

      folioSpringLiquibase.setDefaultSchema(schemaName);
      try {
        log.info("performLiquibase");
        folioSpringLiquibase.performLiquibaseUpdate();

      } catch (LiquibaseException e) {
        log.error("Liquibase error", e);
        return ResponseEntity.internalServerError().build();
      }
    }

    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Void> deleteTenant(String operationId) {
    tenantService.deleteTenant(null);
    return ResponseEntity.noContent().build();
  }


}
