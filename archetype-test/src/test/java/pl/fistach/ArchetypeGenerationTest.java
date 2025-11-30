package pl.fistach;

import static org.junit.jupiter.api.Assertions.*;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

class ArchetypeGenerationTest {

    private static final Path ROOT =
            Path.of("target", "it", "generate-archetype-test", "calculator");

    @Test
    void rootPomShouldHavePackagingPom() throws Exception {
        Path pomPath = ROOT.resolve("pom.xml");
        assertTrue(Files.exists(pomPath), "Brak pliku root pom.xml");

        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (FileReader fr = new FileReader(pomPath.toFile())) {
            Model model = reader.read(fr);

            assertEquals("pom", model.getPackaging(),
                    "Root POM powinien mieć packaging=pom");
        }
    }

    @Test
    void projectShouldContainModules() throws Exception {
        Path pomPath = ROOT.resolve("pom.xml");
        assertTrue(Files.exists(pomPath), "Brak pliku root pom.xml");

        MavenXpp3Reader reader = new MavenXpp3Reader();
        try (FileReader fr = new FileReader(pomPath.toFile())) {
            Model model = reader.read(fr);

            assertFalse(model.getModules().isEmpty(),
                    "Projekt powinien zawierać moduły, ale lista <modules> jest pusta");

            // Możesz też zweryfikować, że moduły fizycznie istnieją:
            for (String module : model.getModules()) {
                Path moduleDir = ROOT.resolve(module);
                assertTrue(Files.exists(moduleDir),
                        "Katalog modułu nie istnieje: " + moduleDir);
            }
        }
    }
}
