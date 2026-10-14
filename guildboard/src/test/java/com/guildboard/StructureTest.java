package com.guildboard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * StructureTest
 */
public class StructureTest {

    private static final Path SERVICES = Path.of("services");
    private static List<String> MODULES = 
        List.of("domain", "application", "adapters", "bootstrap");

    @Test
    void everyServiceHasItsFourModules() throws IOException {
        assertTrue(Files.isDirectory(SERVICES), 
                "no existe la carpeta " + SERVICES.toAbsolutePath());

        List<String> problems = new ArrayList<>();

        try(Stream<Path> children = Files.list(SERVICES)) {
            List<Path> services = children
                .filter(Files::isDirectory)
                .filter(p -> !p.getFileName().toString().startsWith("."))
                .sorted()
                .toList();

            if (services.isEmpty()) {
                problems.add("services/ no contiene ningún servicio");
            }

            for (Path service: services) {
                for (String module: MODULES) {
                    Path buildFile = service.resolve(module).resolve("build.gradle");
                    if (!Files.exists(buildFile)) {
                        problems.add("falta " + buildFile);
                    }
                }
            }
        }

        assertTrue(problems.isEmpty(), () -> 
                "La estructura de servicios no se cumple:\n "
                + String.join("\n ", problems));

    }

    
}
