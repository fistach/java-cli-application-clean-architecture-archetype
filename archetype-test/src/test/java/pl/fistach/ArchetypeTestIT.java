package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.maven.shared.invoker.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;

public class ArchetypeTestIT
{
    @Test
    void generatedProjectShouldBuildSuccessfully() throws Exception {
        File generatedDir = new File("target/project/test-project2");
        assertTrue(generatedDir.exists(), "Generated project not found!");

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(generatedDir, "pom.xml"));
        request.setGoals(Collections.singletonList("verify"));
        request.setBatchMode(true);

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
        invoker.setOutputHandler(System.out::println);

        InvocationResult result = invoker.execute(request);
        assertEquals(0, result.getExitCode(), "Generated project build failed!");
    }

    @AfterEach
    public void teardown() {
        Path folder = Paths.get("target/project/test-project2");

        try {
            if (Files.exists(folder)) {
                Files.walk(folder)
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException ignored) {
                            }
                        });
                System.out.println("Folder usunięty.");
            } else {
                System.out.println("Folder nie istnieje – nic do usunięcia.");
            }
        } catch (IOException e) {
        }
    }
}
