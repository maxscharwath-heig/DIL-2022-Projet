package ch.heigvd.dil.project.commands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * This class represents the command line interface for the clean command.
 *
 * @author Akoumba Ludivine
 * @author Crausaz Nicolas
 * @author Scharwath Maxime
 */
@Command(
        name = "clean",
        description = "Clean the project, removing all build files.",
        version = "1.0")
public class CleanCommand extends BaseCommand {
    private static final Logger LOG = Logger.getLogger(CleanCommand.class.getName());

    @CommandLine.Parameters(index = "0", description = "Path to project to clean")
    String deletionPath;

    @Override
    protected String getRootPath() {
        return deletionPath;
    }

    @Override
    public void execute() {
        // If no build folder, skip command
        if (!new File(deletionPath, "build").exists()) {
            LOG.severe("No build folder found, skipping clean command");
            return;
        }

        // If we find no configuration file in the folder, we assume that this is not a project,
        // so we do not delete it to avoid problems with other programs.
        if (!new File(deletionPath, "config.yml").exists()) {
            LOG.severe(
                    "No configuration file found or it is not a valid project, skipping clean"
                            + " command");
            return;
        }

        // Folder deletion
        try {
            FileUtils.deleteDirectory(new File(deletionPath, "build"));
        } catch (IOException e) {
            LOG.severe("Error while deleting build folder");
            LOG.severe(e.getMessage());
        }
    }
}
