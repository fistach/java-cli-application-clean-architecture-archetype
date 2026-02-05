package pl.fistach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collections;
import java.util.Set;

class ArchetypeBuildTest {

    @Test
    void generatedProjectShouldCompile() throws MavenInvocationException, IOException {
        //given
        Path ROOT = Path.of("target", "it", "generate-archetype-test", "calculator");
        File projectDir = ROOT.toFile().getAbsoluteFile();

        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(projectDir, "pom.xml"));
        request.setGoals(Collections.singletonList("install"));
        request.setBatchMode(true);
        makeMvnwExecutable(projectDir.toString());
        request.setMavenExecutable(new File(projectDir, "mvnw"));

        Invoker invoker = new DefaultInvoker();

        //when
        InvocationResult result = invoker.execute(request);

        //then
        assertEquals(0, result.getExitCode(), "Build failed");
    }

    private void makeMvnwExecutable(String mvnwDir) throws IOException {
        Path mvnw = Path.of(mvnwDir, "mvnw");
        Set<PosixFilePermission> perms = Files.getPosixFilePermissions(mvnw);
        perms.add(PosixFilePermission.OWNER_EXECUTE);
        Files.setPosixFilePermissions(mvnw, perms);
    }
}
