package joplin.search_and_replace_link;

import joplin.Link;

import static java.lang.String.format;

record LinkReplacement(Link link, String newTitle, String newUrl) {
    String newSubstring() {
        return format("[%s](%s)", newTitle, newUrl);
    }
}
