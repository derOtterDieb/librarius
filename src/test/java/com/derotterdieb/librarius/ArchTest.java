package com.derotterdieb.librarius;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.derotterdieb.librarius");

        noClasses()
            .that()
            .resideInAnyPackage("com.derotterdieb.librarius.service..")
            .or()
            .resideInAnyPackage("com.derotterdieb.librarius.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.derotterdieb.librarius.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
