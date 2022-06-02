package ch.heigvd.dil.project.commands;

import org.codehaus.plexus.util.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CleanCommandTest {

    private static final String TEST_FOLDER = "./website";

    @BeforeAll
    static void initMockProject() {
        // Here we use another command (init)
        String[] args = new String[]{TEST_FOLDER};
        CommandLine cmd = new CommandLine(new InitCommand());
        cmd.execute(args);
    }

    @BeforeAll
    @AfterAll
    static void clearProject() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_FOLDER));
    }

    @Test
    public void shouldCleanBuildFolder() {
        String[] args = new String[]{TEST_FOLDER};
        CommandLine cmd = new CommandLine(new CleanCommand());
        cmd.execute(args);

        assertFalse(new File(TEST_FOLDER, "build").exists());
    }

    @Test
    public void shouldIgnoreIfNoBuildFolder() {
        String[] args = new String[]{TEST_FOLDER};
        CommandLine cmd = new CommandLine(new CleanCommand());
        cmd.execute(args);

        assertFalse(new File(TEST_FOLDER, "build").exists());
    }
}
