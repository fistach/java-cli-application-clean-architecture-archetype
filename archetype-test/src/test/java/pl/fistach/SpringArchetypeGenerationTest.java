package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import org.apache.maven.model.Model;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

class SpringArchetypeGenerationTest {

    public static final String ARTIFACT_ID = "calculator";
    private static final Path ROOT =
            Path.of("target", "it", "generate-spring-archetype-test", ARTIFACT_ID);

    private static Model model;

    @Test
    void mainJavaShouldHaveSpringBootApplicationAnnotation() throws Exception {
        //given
        Path mainClassPath = ROOT.resolve("calculator-main/src/main/java/com/example/main/Application.java");

        //when
        var compilationUnit = StaticJavaParser.parse(mainClassPath);

        //then
        boolean hasSpringAnnotation = compilationUnit
                .findAll(ClassOrInterfaceDeclaration.class)
                .stream()
                .anyMatch(clazz -> clazz.getAnnotationByName("SpringBootApplication").isPresent());

        assertTrue(hasSpringAnnotation);
    }

}
