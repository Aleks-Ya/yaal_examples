package joplin.apps.find_big_notes;

import joplin.common.Factory;
import joplin.common.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Tuple3;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Count resources (attachments) of specific type.
 */
public class CountResourcesMain {
    private static final Logger log = LoggerFactory.getLogger(CountResourcesMain.class);

    public static void main(String[] args) {
        log.info("Started");
        try (var facade = Factory.createFacadeProd(true)) {
            var allResources = facade.fetchAllResources();

            var totalCount = allResources.size();
            var totalSize = allResources.stream().mapToLong(Resource::getSize).sum();
            log.info("Total resource count: {}", totalCount);
            log.info("Total resource size: {}", totalSize);

            allResources.stream()
                    .collect(Collectors.groupingBy(Resource::getExtension)).entrySet().stream()
                    .map(entry -> Tuple3.of(
                            entry.getKey(),
                            entry.getValue().size(),
                            entry.getValue().stream().mapToLong(Resource::getSize).sum()
                    ))
                    .sorted(Comparator.<Tuple3<String, Integer, Long>, Long>comparing(Tuple3::element3).reversed())
                    .forEach(tuple3 -> log.info(String.format("Extension='%s', Count=%d, Size=%,d",
                            tuple3.element1(), tuple3.element2(), tuple3.element3())));
            log.info("Finished");
        }
    }
}
