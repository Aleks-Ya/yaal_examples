package joplin.common.link;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTypeTest {
    @Test
    void type() {
        var joplinLink = new Link("[Joplin link 1](:/db65929324925ccbfa789f95cdd293ba)",
                "Joplin link 1", ":/db65929324925ccbfa789f95cdd293ba", null);
        assertThat(joplinLink.type()).isEqualTo(LinkType.JOPLIN);

        var evernoteLink = new Link("[Plan Risk Management](evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/)",
                "Plan Risk Management", "evernote:///view/48821034/s241/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/2fb4b9dd-7fbd-414c-831c-fc4c75c25d90/", null);
        assertThat(evernoteLink.type()).isEqualTo(LinkType.EVERNOTE);

        var generalLink = new Link("[WinSCP](https://winscp.net)",
                "WinSCP", "https://winscp.net", null);
        assertThat(generalLink.type()).isEqualTo(LinkType.GENERAL);

        var nullLink = new Link("[WinSCP]()", "WinSCP", null, null);
        assertThat(nullLink.type()).isEqualTo(LinkType.GENERAL);
    }
}