package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.maven.shared.invoker.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collections;

public class ArchetypeTest
{
    @Test
    void generatedProjectShouldBuildSuccessfully() throws Exception {
        File generatedDir = new File("target/it/basic/generated-sample");
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
}
