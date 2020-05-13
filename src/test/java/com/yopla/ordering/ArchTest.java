package com.yopla.ordering;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.yopla.ordering");

        noClasses()
            .that()
                .resideInAnyPackage("com.yopla.ordering.service..")
            .or()
                .resideInAnyPackage("com.yopla.ordering.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.yopla.ordering.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
