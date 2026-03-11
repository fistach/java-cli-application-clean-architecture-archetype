package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.stream.Stream;

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

    private static Stream<Arguments> dependentModules() {
        return Stream.of(
                Arguments.of("main", "controller"),
                Arguments.of("controller", "service"),
                Arguments.of("service", "repository-api"),
                Arguments.of("repository-api", "domain")
        );
    }

    @ParameterizedTest
    @MethodSource("dependentModules")
    void mainModuleAShouldDependOnModuleB(String base, String dependent) throws Exception {
        //given
        String rootArtifactId = model.getArtifactId();
        String dependentArtifact = rootArtifactId + "-"+dependent;

        String baseArtifact = rootArtifactId + "-"+base;
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
