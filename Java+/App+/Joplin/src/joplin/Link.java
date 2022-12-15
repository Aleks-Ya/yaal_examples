package joplin;

public record Link(NoteEntity note, String element, String text, String url) {
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
}
