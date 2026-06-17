package com.shopmanagement.catalog.common;

import java.util.EnumSet;
import java.util.Set;

/** Billing / ordering module contexts with allowed normalized product categories. */
public enum ProductCatalogContext {
    PHARMACY(EnumSet.of(
            ProductCatalogCategory.MEDICINE,
            ProductCatalogCategory.OTC,
            ProductCatalogCategory.CONSUMABLE)),
    PATHOLOGY(EnumSet.of(
            ProductCatalogCategory.LAB_TEST,
            ProductCatalogCategory.LAB_PACKAGE)),
    RECEPTION(EnumSet.of(
            ProductCatalogCategory.CONSULTATION,
            ProductCatalogCategory.SERVICE));

    private final Set<ProductCatalogCategory> allowedCategories;

    ProductCatalogContext(Set<ProductCatalogCategory> allowedCategories) {
        this.allowedCategories = allowedCategories;
    }

    public Set<ProductCatalogCategory> allowedCategories() {
        return allowedCategories;
    }

    public static ProductCatalogContext parse(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return ProductCatalogContext.valueOf(raw.trim().toUpperCase());
    }

    public static ProductCatalogContext fromOrderChannel(String billType, String orderChannel) {
        String raw = billType != null && !billType.isBlank() ? billType : orderChannel;
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return switch (raw.trim().toUpperCase()) {
            case "LAB" -> ProductCatalogContext.PATHOLOGY;
            case "OPD" -> ProductCatalogContext.RECEPTION;
            case "PHARMACY" -> ProductCatalogContext.PHARMACY;
            default -> null;
        };
    }
}
