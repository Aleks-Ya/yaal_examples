package joplin.search_and_replace_link;

import joplin.Link;

class LinkReplacer {
    LinkReplacement replace(Link link) {
        var newTitle = link.text()
                .replaceAll("\n", " ")
                .replaceAll("\\s{2,}", " ")
                .trim();
        return new LinkReplacement(link, newTitle, link.url());
    }
}
