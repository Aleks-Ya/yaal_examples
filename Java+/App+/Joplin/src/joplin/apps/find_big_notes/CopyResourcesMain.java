package joplin.apps.find_big_notes;

import joplin.common.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Print copy Bash command for resources.
 */
public class CopyResourcesMain {
    private static final Logger log = LoggerFactory.getLogger(CopyResourcesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        var destDir = "/home/aleks/JoplinAttacheResourcesForReplace";
        var extensions = List.of("png");
        try (var facade = Factory.createFacadeProd(true)) {
            var commands = facade.fetchAllResources().stream()
                    .filter(resource -> resource.hasExtension(extensions))
                    .map(resource -> String.format("cp '%s' %s", resource.resourceFile().getAbsolutePath(), destDir))
                    .toList();
            System.out.println("Command number: " + commands.size());
            System.out.println(String.join("\n", commands));
        }
        log.info("Finished");
    }
}
