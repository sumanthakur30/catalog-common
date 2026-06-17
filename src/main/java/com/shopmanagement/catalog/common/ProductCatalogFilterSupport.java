package com.shopmanagement.catalog.common;

import java.util.Locale;
import java.util.Set;

/**
 * Resolves legacy {@code product_type} / category values to normalized catalog categories
 * and enforces module-specific allowlists.
 */
public final class ProductCatalogFilterSupport {

    private static final Set<String> LAB_TYPE_TOKENS = Set.of(
            "LAB", "DIAGNOSTIC", "PATHOLOGY", "RADIOLOGY", "LAB_TEST");
    private static final Set<String> MEDICINE_TYPE_TOKENS = Set.of(
            "MEDICINE", "MEDICAL", "PHARMACY", "DRUG");

    private ProductCatalogFilterSupport() {
    }

    public static ProductCatalogCategory resolveCategory(CatalogProductView product) {
        if (product == null) {
            return ProductCatalogCategory.SERVICE;
        }
        String type = normalize(product.getProductType());
        String category = normalize(product.getCategory());
        String name = normalize(product.getName());
        String code = normalize(product.getCode());

        if ("CONSUMABLE".equals(type)) {
            return ProductCatalogCategory.CONSUMABLE;
        }
        if ("OTC".equals(type)) {
            return ProductCatalogCategory.OTC;
        }
        String schedule = normalize(product.getMedicineScheduleType());
        if ("OTC".equals(schedule)) {
            return ProductCatalogCategory.OTC;
        }
        if (product.getMedicineScheduleType() != null && !schedule.isBlank() && !isLabLike(type, category, name, code)) {
            return ProductCatalogCategory.MEDICINE;
        }
        if (MEDICINE_TYPE_TOKENS.contains(type)) {
            return ProductCatalogCategory.MEDICINE;
        }
        if ("LAB_PACKAGE".equals(type) || containsAny(category, "PACKAGE") || containsAny(name, "PACKAGE")) {
            return ProductCatalogCategory.LAB_PACKAGE;
        }
        if (isLabLike(type, category, name, code)) {
            return ProductCatalogCategory.LAB_TEST;
        }
        if ("CONSULTATION".equals(type) || containsAny(category, "CONSULTATION") || containsAny(name, "CONSULTATION")) {
            return ProductCatalogCategory.CONSULTATION;
        }
        if ("SERVICE".equals(type)) {
            return ProductCatalogCategory.SERVICE;
        }
        return ProductCatalogCategory.SERVICE;
    }

    public static boolean matchesContext(CatalogProductView product, ProductCatalogContext context) {
        if (context == null) {
            return true;
        }
        if (product == null) {
            return false;
        }
        return context.allowedCategories().contains(resolveCategory(product));
    }

    private static boolean isLabLike(String type, String category, String name, String code) {
        if (LAB_TYPE_TOKENS.stream().anyMatch(type::contains)) {
            return true;
        }
        if (containsAny(category, "LAB", "DIAGNOSTIC", "PATHOLOGY", "TEST", "BLOOD")) {
            return true;
        }
        if (containsAny(name, "BLOOD TEST", "CBC", "LFT", "TEST")) {
            return true;
        }
        return code.contains("LFT")
                || code.contains("CBC")
                || code.endsWith("-TEST")
                || code.contains("-LAB-")
                || code.startsWith("TPLAB-");
    }

    private static boolean containsAny(String value, String... tokens) {
        if (value == null || value.isBlank()) {
            return false;
        }
        for (String token : tokens) {
            if (value.contains(token)) {
                return true;
            }
        }
        return false;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
