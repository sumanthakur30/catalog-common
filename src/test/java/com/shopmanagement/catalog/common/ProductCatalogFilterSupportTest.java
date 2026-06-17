package com.shopmanagement.catalog.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ProductCatalogFilterSupportTest {

    @Test
    void pharmacyCannotSeeLabTestsOrConsultations() {
        assertFalse(ProductCatalogFilterSupport.matchesContext(
                product("CBC", "Complete Blood Count", "LAB", "Blood Test", null),
                ProductCatalogContext.PHARMACY));
        assertFalse(ProductCatalogFilterSupport.matchesContext(
                product("OPD-1", "General OPD Consultation", "SERVICE", "Consultation", null),
                ProductCatalogContext.PHARMACY));
        assertTrue(ProductCatalogFilterSupport.matchesContext(
                product("PCM", "Paracetamol", "MEDICINE", "Analgesic", "H"),
                ProductCatalogContext.PHARMACY));
    }

    @Test
    void pathologyCannotSeeMedicines() {
        assertFalse(ProductCatalogFilterSupport.matchesContext(
                product("PCM", "Paracetamol", "MEDICINE", "Analgesic", "H"),
                ProductCatalogContext.PATHOLOGY));
        assertTrue(ProductCatalogFilterSupport.matchesContext(
                product("CBC", "Complete Blood Count", "LAB", "Blood Test", null),
                ProductCatalogContext.PATHOLOGY));
    }

    private static CatalogProductView product(
            String code, String name, String type, String category, String schedule) {
        return new CatalogProductView() {
            @Override
            public String getProductType() {
                return type;
            }

            @Override
            public String getCategory() {
                return category;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getMedicineScheduleType() {
                return schedule;
            }
        };
    }
}
