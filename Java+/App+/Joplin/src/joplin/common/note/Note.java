package joplin.common.note;

import joplin.common.link.Link;
import joplin.common.resource.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record Note(NoteId noteId,
                   NotebookId notebookId,
                   String title,
                   String body,
                   MarkupLanguage markupLanguage,
                   Long updatedTime,
                   List<Link> links) {

    public Note withLinks(List<Link> links) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withTitle(String title) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withBody(String body) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public Note withMarkupLanguage(MarkupLanguage markupLanguage) {
        return new Note(noteId, notebookId, title, body, markupLanguage, updatedTime, links);
    }

    public long getNoteSize() {
        var bodySize = body().length();
        var resourceSize = links().stream()
                .mapToLong(Link::getResourceSize)
                .sum();
        return bodySize + resourceSize;
    }

    public long getResourceNumber() {
        if (links == null) {
            return 0;
        }
        return links.stream().map(Link::resource).filter(Objects::nonNull).count();
    }

    public Optional<Resource> getBiggestResource() {
        return links.stream()
                .map(Link::resource)
                .filter(Objects::nonNull)
                .max(Comparator.comparing(Resource::getSize));
    }

    public Optional<Resource> getBiggestResource(List<String> extensions) {
        return links().stream()
                .map(Link::resource)
                .filter(Objects::nonNull)
                .filter(resource -> resource.hasExtension(extensions))
                .max(Comparator.comparing(Resource::getSize));
    }
}
