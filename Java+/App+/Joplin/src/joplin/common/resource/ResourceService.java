package joplin.common.resource;

import joplin.common.link.Link;
import joplin.common.link.LinkType;
import joplin.common.note.Note;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class ResourceService {
    private final File resourcesDir;

    public ResourceService(String joplinDir) {
        this.resourcesDir = new File(joplinDir, "resources");
    }

    private Optional<Resource> getDecryptedJoplinResource(Link link) {
        if (link == null || link.type() != LinkType.JOPLIN) {
            return Optional.empty();
        }
        var resourceId = link.url().replaceFirst(":/", "");
        var files = resourcesDir.listFiles((dir, name) -> name.startsWith(resourceId) && !name.endsWith(".crypted"));
        if (files == null || files.length == 0) {
            return Optional.empty();
        }
        if (files.length > 1) {
            throw new IllegalStateException("Found " + files.length + " resources with resourceId " + resourceId);
        }
        return Optional.of(new Resource(new ResourceId(resourceId), files[0]));
    }

    public Note addLinkResources(Note note) {
        if (note == null || note.links() == null) {
            return note;
        }
        var links = note.links().stream()
                .map(link -> link.withResource(getDecryptedJoplinResource(link).orElse(null)))
                .toList();
        return note.withLinks(links);
    }

    public List<Note> addLinkResources(List<Note> notes) {
        return notes.stream().map(this::addLinkResources).toList();
    }

    public List<File> findAllFilesInResourceDir() {
        var files = resourcesDir.listFiles();
        return files != null ? Arrays.stream(files).toList() : List.of();
    }

}
