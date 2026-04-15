## v2.0.0 2026-04-16

### Breaking changes
* Upgrade module to Spring Boot v4.0.2 ([MODCITEM-45](https://folio-org.atlassian.net/browse/MODCITEM-45))

### New APIs versions
* Requires `locations v3.2`

### Features
* Accept and store effectiveLocationId from mod-dcb ([MODCITEM-41](https://folio-org.atlassian.net/browse/MODCITEM-41))
* Validate that effectiveLocationId exists ([MODCITEM-44](https://folio-org.atlassian.net/browse/MODCITEM-44))
* Always return default effective location if not set ([MODCITEM-48](https://folio-org.atlassian.net/browse/MODCITEM-48))
* Use GitHub Workflows for Maven ([MODCITEM-50](https://folio-org.atlassian.net/browse/MODCITEM-50))

### Tech Debt
* Add config file for dependabot, code owners and update pull request template ([MODCITEM-40](https://folio-org.atlassian.net/browse/MODCITEM-40))

### Dependencies
* Bump `spring-boot-starter-parent` from `3.4.3` to `4.0.5`
* Bump `folio-spring-base` from `9.0.0` to `10.0.0`
* Bump `openapi-generator` from `7.3.0` to `7.21.0`
* Bump `jsonschema2pojo-maven-plugin` from `1.2.2` to `1.3.3`
* Bump `maven-release-plugin` from `3.1.1` to `3.3.1`
* Bump `commons-lang3` from `3.17.0` to `3.20.0`
* Bump `wiremock-standalone` from `3.12.1` to `3.13.2`

---

## v1.2.0 2025-03-14

* FOLIO-4232 Update Spring support version for Sunflower

## v1.1.0 2024-10-30

* MODCITEM-30 Update Spring support version for Ramsons
* MODCITEM-32 Upgrade versions for Ramsons.

## v1.0.0 2024-03-20

* MODCITEM-4 Upgraded version for spring-boot-starter-parent and folio-spring-base.version
* MODCITEM-5 Improve API POST-method json descriptor
* MODCITEM-6 Adjustments needed cater to DCB location in this module
* CIRC-1966 Api for supporting circulation change
* CIRC-1966 Permission Fix
* MODCITEM-15 Adding barcode validation before creating item
* MODCITEM-8 Upgrade Spring Boot, Kafka, Hazelcast fixing bugs
* MODCITEM-16 Upgrade to folio-spring-base 7.2.2, bcprov-jdk18on:jar 1.73
* MODCITEM-17 Add 5 digit lending library code
* MODCITEM-21 TC module submission include: ${ACTUATOR_EXPOSURE:health,info,loggers}
* MODCITEM-17 Add 5 digit lending library code
* MODCITEM-20 Added PERSONAL_DATA_DISCLOSURE.md file for TC
* MODCITEM-22 Use query based endpoint for fetchByBarcode
* MODCITEM-18 Refactoring the permissions in module descriptor template
* TCR-37 Added MODULE_SELF_EVALUATION.md file
* MODCITEM-25 Upgrading dependencies for Quesnelia release
* TCR-37 Changes made as per TC review comments
* MODCITEM-25 Upgrading dependencies for Quesnelia release
