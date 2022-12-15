package joplin;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTypeTest {
    @Test
    void type() {
        var joplinLink = new Link(null, "[Joplin link 1](:/db65929324925ccbfa789f95cdd293ba)",
                "Joplin link 1", ":/db65929324925ccbfa789f95cdd293ba");
        assertThat(joplinLink.type()).isEqualTo(LinkType.JOPLIN);

        var evernoteLink = new Link(null, "[Plan Risk Management](evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/)",
                "Plan Risk Management", "evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/");
        assertThat(evernoteLink.type()).isEqualTo(LinkType.EVERNOTE);

        var generalLink = new Link(null, "[WinSCP](https://winscp.net)",
                "WinSCP", "https://winscp.net");
        assertThat(generalLink.type()).isEqualTo(LinkType.GENERAL);

        var nullLink = new Link(null, "[WinSCP]()",
                "WinSCP", null);
        assertThat(generalLink.type()).isEqualTo(LinkType.GENERAL);
    }
}