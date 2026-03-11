package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.nio.file.Path;

class ArchetypeModuleDependenciesTest {

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
    void mainModuleShouldDependOnController() throws Exception {
        //given
        String rootArtifactId = model.getArtifactId();
        String dependentArtifact = rootArtifactId + "-controller";

        String baseArtifact = rootArtifactId + "-main";
        Path baseModulePomPath = Path.of(ROOT.toString(), baseArtifact, "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model baseModel = reader.read(new FileReader(baseModulePomPath.toFile()));

        //when
        long dependentsCount = baseModel.getDependencies()
                .stream()
                .map(Dependency::getArtifactId)
                .filter(dependentArtifact::equals)
                .count();
        //then
        assertEquals(1, dependentsCount,
                "Module " + baseArtifact + " should depend exactly once on " + dependentArtifact);
    }

    @Test
    void controllerModuleShouldDependOnServiceModule() throws Exception {
        //given
        String rootArtifactId = model.getArtifactId();
        String dependentArtifact = rootArtifactId + "-service";

        String baseArtifact = rootArtifactId + "-controller";
        Path baseModulePomPath = Path.of(ROOT.toString(), baseArtifact, "pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model baseModel = reader.read(new FileReader(baseModulePomPath.toFile()));

        //when
        long dependentsCount = baseModel.getDependencies()
                .stream()
                .map(Dependency::getArtifactId)
                .filter(dependentArtifact::equals)
                .count();
        //then
        assertEquals(1, dependentsCount,
                "Module " + baseArtifact + " should depend exactly once on " + dependentArtifact);
    }

}
