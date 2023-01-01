package joplin.apps.find_big_notes;

import joplin.common.Factory;
import joplin.common.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.stream.Collectors;

/**
 * Find resources not used in notes.
 */
public class FindUnusedResourcesMain {
    private static final Logger log = LoggerFactory.getLogger(FindUnusedResourcesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(true)) {
            var allResources = facade.fetchAllResources().stream()
                    .map(Resource::resourceFile)
                    .toList();
            var allFiles = facade.findAllFilesInResourceDir();
            var unusedResources = allFiles.stream()
                    .filter(file -> !allResources.contains(file))
                    .toList();
            log.info("Total resource count: {}", allResources.size());
            log.info("Total files in resource dir: {}", allFiles.size());
            log.info("Total unused files in resource dir: {}", unusedResources.size());
            var unusedResourcesStr = unusedResources.stream().map(File::getName).collect(Collectors.joining("\n"));
            log.info("Unused files in resource dir:\n{}", unusedResourcesStr);
        }
    }
}
