# catalog-common

Shared product catalog filtering for [SugamFlow](https://github.com/sumanthakur30) microservices.

**Maven coordinates:** `com.shopmanagement:catalog-common:0.0.1-SNAPSHOT`

## Used by

- `product-service`
- `order-service`

## Build (local Maven repo)

Requires **Java 17** and Maven 3.9+.

```bash
git clone https://github.com/sumanthakur30/catalog-common.git
cd catalog-common
mvn -B install -DskipTests
```

No dependency on other SugamFlow shared libs.

## Main types

| Type | Purpose |
|------|---------|
| `ProductCatalogContext` | Tenant/shop catalog scope |
| `ProductCatalogFilterSupport` | Shared filter logic |
| `CatalogProductView` | Lightweight product view DTO |
| `ProductCatalogCategory` | Category metadata |

## Build order (shared libs)

1. [platform-common](https://github.com/sumanthakur30/platform-common) (for services that also use security-common)
2. **catalog-common** (this repo)
3. [security-common](https://github.com/sumanthakur30/security-common)

## SugamFlow monorepo

In the full SugamFlow tree, this module is built with `platform-common` and `security-common` via `docker/Dockerfile.common-libs`.
