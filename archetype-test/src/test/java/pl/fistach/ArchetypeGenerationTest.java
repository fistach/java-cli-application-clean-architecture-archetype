package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class ArchetypeGenerationTest {

    private static final Path ROOT =
            Path.of("target", "it", "generate-archetype-test", "calculator");

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
    void projectShouldContainModules() {
        assertFalse(model.getModules().isEmpty(),
                "Project should contain modules, but <modules> list is empty");

        for (String module : model.getModules()) {
            Path moduleDir = ROOT.resolve(module);
            assertTrue(Files.exists(moduleDir),
                    "module dir doesn't exist: " + moduleDir);
        }
    }

    @Test
    void projectShouldContainOneControllerModule() {
        String rootArtifactId = model.getArtifactId();
        List<String> modules = model.getModules();

        int controllerCount = 0;

        for (String module : modules) {
            if (module.equals(rootArtifactId + "-controller")) {
                controllerCount++;
            }
        }
        assertEquals(1, controllerCount, "Project should contain exactly one 'controller' module");
    }

    @Test
    void mainModuleShouldDependOnController() throws Exception {
        String rootArtifactId = model.getArtifactId();
        String expectedControllerArtifact = rootArtifactId + "-controller";

        Path mainModulePomPath = Path.of(ROOT.toString(), rootArtifactId + "-main", "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model mainModel = reader.read(new FileReader(mainModulePomPath.toFile()));

        long controllerDependenciesCount = mainModel.getDependencies()
                .stream()
                .map(Dependency::getArtifactId)
                .filter(expectedControllerArtifact::equals)
                .count();

        assertEquals(1, controllerDependenciesCount,
                "Module 'main' should depend exactly once on " + expectedControllerArtifact);
    }
}
