package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

class ArchetypeGenerationTest {

    public static final String ARTIFACT_ID = "calculator";
    private static final Path ROOT =
            Path.of("target", "it", "generate-archetype-test", ARTIFACT_ID);

    private static Model model;

    @BeforeAll
    static void setUp() throws Exception {
        Path rootPomPath = ROOT.resolve("pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();

        try (FileReader fr = new FileReader(rootPomPath.toFile())) {
            model = reader.read(fr);
        }
    }

    @Test
    void rootPomShouldHavePackagingPom() {
        assertEquals("pom", model.getPackaging(),
                "Root POM should have packaging=pom");
    }

    @Test
    void projectShouldContainOneControllerModule() {
        //given
        String rootArtifactId = model.getArtifactId();
        List<String> modules = model.getModules();

        int controllerCount = 0;

        //when
        for (String module : modules) {
            if (module.equals(rootArtifactId + "-controller")) {
                controllerCount++;
            }
        }

        //then
        assertEquals(1, controllerCount, "Project should contain exactly one 'controller' module");
    }

    @Test
    void rootPomShouldContainJUnit5Dependency() {
        //given
        List<Dependency> dependencies = model.getDependencies();

        //when
        boolean hasJUnit5 = dependencies.stream().anyMatch(dep ->
                "org.junit.jupiter".equals(dep.getGroupId()) &&
                        "junit-jupiter".equals(dep.getArtifactId()) &&
                        "test".equals(dep.getScope())
        );

        //then
        assertTrue(hasJUnit5, "Root pom.xml should contain JUnit 5 dependency (junit-jupiter, scope=test)");
    }

    @Test
    void controllerModuleShouldContainDefaultControllerClass() throws IOException {
        //given
        Path controllerSrcRoot = ROOT.resolve(Path.of(
                model.getArtifactId() + "-controller",
                "src",
                "main",
                "java"));

        Optional<Path> optionalController = findController(controllerSrcRoot);

        assertTrue(optionalController.isPresent(), "Controller.java should exist in controller module"
        );
    }

    private static Optional<Path> findController(Path startDir) throws IOException {
        try (Stream<Path> paths = Files.walk(startDir)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals("DefaultController.java"))
                    .findFirst();
        }
    }
}
