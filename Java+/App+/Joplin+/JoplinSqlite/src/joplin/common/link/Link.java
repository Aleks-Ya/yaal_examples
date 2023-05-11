package joplin.common.link;

import joplin.common.resource.Resource;

public record Link(String element, String text, String url, Resource resource) {
    public LinkType type() {
        if (url == null) {
            return LinkType.GENERAL;
        }
        if (url.startsWith("evernote:")) {
            return LinkType.EVERNOTE;
        }
        if (url.startsWith(":")) {
            return LinkType.JOPLIN;
        }
        return LinkType.GENERAL;
    }

    public Link withResource(Resource resource) {
        return new Link(element, text, url, resource);
    }

    public long getResourceSize() {
        return resource != null ? resource.resourceFile().length() : 0;
    }
}
