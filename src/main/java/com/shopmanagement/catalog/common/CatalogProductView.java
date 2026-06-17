package com.shopmanagement.catalog.common;

/**
 * Minimal product shape for catalog category resolution across services.
 */
public interface CatalogProductView {

    String getProductType();

    String getCategory();

    String getName();

    String getCode();

    /** Medicine schedule type (H, H1, X, OTC, etc.) when available. */
    String getMedicineScheduleType();
}
