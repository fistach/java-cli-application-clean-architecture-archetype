package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

public class ArchetypeGenerationTest {

    @Test
    void shouldGeneratePomXml() {
        Path pomPath = Path.of(
                "target", "it",
                "generate-archetype-test",
                "calculator",
                "pom.xml"
        );

        assertTrue(Files.exists(pomPath),
                "Plik pom.xml nie został wygenerowany w oczekiwanym miejscu: " + pomPath);
    }
}
