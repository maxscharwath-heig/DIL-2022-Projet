package ch.heigvd.dil.project.commands;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import picocli.CommandLine;

public class ServerCommandTest {
    static final String TEST_FOLDER = "./website";

    private static final String[] args = new String[] {TEST_FOLDER};

    @Before
    public void initAndBuild() {
        CommandLine cmd1 = new CommandLine(new InitCommand());
        CommandLine cmd2 = new CommandLine(new BuildCommand());
        cmd1.execute(args);
        cmd2.execute(args);
    }

    @Test
    public void shouldAnswerOK() throws IOException {
        CommandLine cmd = new CommandLine(new ServeCommand());
        cmd.execute(args);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("http://localhost:8080").build();

        try (Response response = client.newCall(request).execute()) {
            assert (response.code() == 200);
            assert (Objects.requireNonNull(response.body()).toString().length() > 0);
        }
    }

    @After
    public void clean() throws IOException {
        FileUtils.deleteDirectory(new File(TEST_FOLDER));
    }
}
