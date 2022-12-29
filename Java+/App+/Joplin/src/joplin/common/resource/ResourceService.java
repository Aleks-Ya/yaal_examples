package joplin.common.resource;

import joplin.common.link.Link;
import joplin.common.link.LinkType;
import joplin.common.note.Note;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResourceService {
    private final File resourcesDir;

    public ResourceService(String joplinDir) {
        this.resourcesDir = new File(joplinDir, "resources");
    }

    private static boolean isExtensionInList(Resource resource, List<String> extensions) {
        if (resource == null || resource.resourceFile() == null) {
            return false;
        }
        return extensions.stream()
                .anyMatch(extension -> resource.resourceFile().getName().toLowerCase()
                        .endsWith("." + extension.toLowerCase()));
    }

    public Optional<Resource> getDecryptedJoplinResource(Link link) {
        if (link == null || link.type() != LinkType.JOPLIN) {
            return Optional.empty();
        }
        var resourceId = link.url().replaceFirst(":/", "");
        var files = resourcesDir.listFiles((dir, name) -> name.startsWith(resourceId) && !name.endsWith(".crypted"));
        if (files == null || files.length == 0) {
            return Optional.empty();
        }
        if (files.length > 1) {
            throw new IllegalStateException("Found " + files.length + " resources with id " + resourceId);
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

    public Long noteSizeWithResources(Note note) {
        var bodySize = note.body().length();
        var resourceSize = note.links().stream()
                .filter(link -> link.resource() != null)
                .mapToLong(link -> link.resource().resourceFile().length())
                .sum();
        return bodySize + resourceSize;
    }

    public Long noteResourceNumber(Note note, LinkType linkType) {
        return note.links().stream()
                .filter(link -> link.type() == linkType)
                .count();
    }

    public Optional<Resource> biggestResource(Note note) {
        if (note.links() == null) {
            return Optional.empty();
        }
        return note.links().stream()
                .map(Link::resource)
                .filter(Objects::nonNull)
                .min((r1, r2) -> Long.compare(r2.resourceFile().length(), r1.resourceFile().length()));
    }

    public Optional<Resource> biggestResource(Note note, List<String> extensions) {
        if (note.links() == null) {
            return Optional.empty();
        }
        return note.links().stream()
                .map(Link::resource)
                .filter(Objects::nonNull)
                .filter(resource -> isExtensionInList(resource, extensions))
                .min((r1, r2) -> Long.compare(r2.resourceFile().length(), r1.resourceFile().length()));
    }
}
