package ch.heigvd.dil.project.commands;

import ch.heigvd.dil.project.factories.SiteStructureFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/** This class represents the command line interface for the new command. */
@Command(name = "init", description = "Init ", version = "1.0")
public class InitCommand implements Runnable {

    @CommandLine.Parameters(
            index = "0",
            description = "Path to new site",
            defaultValue = "./newsite")
    String creationPath;

    String configurationFile = "config.yml";
    String indexFile = "index.md";
    String examplePageFolder = "/page";

    @CommandLine.Parameters(index = "1", description = "Skip user interaction", defaultValue = "")
    String shouldSkip;

    @Override
    public void run() {
        Path pathToNewSite = Paths.get(creationPath);

        try {
            // Create main directories
            Files.createDirectories(pathToNewSite);

            // Create configuration file
            SiteStructureFactory cf = new SiteStructureFactory();
            ObjectMapper om =
                    new ObjectMapper(
                            new YAMLFactory()
                                    .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
            om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            om.writeValue(
                    new File(creationPath + "/" + configurationFile),
                    cf.generateBaseConfiguration());

            // Create index page file
            FileWriter fw = new FileWriter(creationPath + "/" + indexFile);
            fw.write("# This is the homepage content");
            fw.close();

            // Create example
            Files.createDirectory(Paths.get(creationPath + "/" + examplePageFolder));
            FileWriter fw2 = new FileWriter(creationPath + "/" + examplePageFolder + "/page.md");
            fw2.write("# This is the page content");
            fw2.close();

            // Create layouts
            Files.createDirectory(Paths.get(creationPath + "/layouts"));
            // Navbar
            FileWriter fw3 = new FileWriter(creationPath + "/layouts/navbar.html");
            fw3.write(
                    "<nav>"
                            + "    <ol>\n"
                            + "        <li><a href=\"/index.html\">Home</a></li>\n"
                            + "        <li><a href=\"/page/page.html\">Page</a></li>\n"
                            + "    </ol>\n"
                            + "</nav>\n");
            fw3.close();

            // Layout
            FileWriter fw4 = new FileWriter(creationPath + "/layouts/layout.html");
            fw4.write(
                    "<html lang=\"{{ site.language }}\">\n"
                            + "<head>\n"
                            + "<meta charset=\"utf-8\">\n"
                            + "<title>{{ site.title }} | {{ page.title }}</title>\n"
                            + "</head>\n"
                            + "<body>\n"
                            + "{% include menu.html }\n"
                            + "{{ content }}\n"
                            + "</body>\n"
                            + "</html>\n");
            fw4.close();

            // Footer
            FileWriter fw5 = new FileWriter(creationPath + "/layouts/footer.html");
            fw5.write("<footer><p>This is the footer | Copyright 2022</p></footer>");
            fw5.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
