package com.guildboard.quests;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

public class ArchitectureTest {
    private static final String BASE_PACKAGE = "com.guildboard.quests";
    private static final JavaClasses CLASSES = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests())
            .importPackages(BASE_PACKAGE);

    @Test
    void domainDoesNotDependOnOuterLayers() {
        noClasses().that().resideInAPackage("..domain..")
                .should().dependOnClassesThat().resideInAnyPackage(
                        "..application..", "..adapter..", "..config..",
                        "org.springframework..", "jakarta..")
                .check(CLASSES);
    }

    @Test
    void applicationOnlyDependsOnDomain() {
        classes().that().resideInAPackage("..application..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..application..", "..domain..", "java..", "lombok..")
                .check(CLASSES);
    }

    @Test
    void applicationDoesNotUseJdbc() {
        noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("java.sql..")
                .check(CLASSES);
    }

    @Test
    void useCaseInterfacesEndWithUseCase() {
        classes().that().resideInAPackage("..application.port.in..")
                .and().areInterfaces()
                .should().haveSimpleNameEndingWith("UseCase")
                .check(CLASSES);
    }

    @Test 
    void adapterInDoesNotDependOnAdapterOut() {
        noClasses().that().resideInAPackage("..adapter.in..")
            .should().dependOnClassesThat().resideInAPackage("..adapter.out..").check(CLASSES);
    }

    @Test 
    void adapterOutDoesNotDependOnAdapterIn() {
        noClasses().that().resideInAPackage("..adapter.out..")
            .should().dependOnClassesThat().resideInAPackage("..adapter.in..").check(CLASSES);
    }

    @Test 
    void adapterInDoesNotUseJpa() {
        noClasses().that().resideInAPackage("..adapter.in..")
            .should().dependOnClassesThat().resideInAPackage("jakarta.persistence..")
            .check(CLASSES);
    }
}
