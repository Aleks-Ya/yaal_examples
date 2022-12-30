package joplin.common.note;

import joplin.Utils;
import joplin.common.link.Link;
import joplin.common.resource.Resource;
import joplin.common.resource.ResourceId;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class NoteServiceTest {

    @Test
    void findBiggestNotes() {
        try (var facade = Utils.createFacadeFake()) {
            var biggestNotes = facade.findBiggestNotes(2);
            assertThat(biggestNotes).extracting(note -> note.id().id(), Note::links)
                    .containsExactly(tuple("3ce4eb6d45d741718772f16c343b8ddd", List.of(
                            new Link("[Joplin link    1   ](:/db65929324925ccbfa789f95cdd293ba)",
                                    "Joplin link    1   ", ":/db65929324925ccbfa789f95cdd293ba",
                                    new Resource(new ResourceId("db65929324925ccbfa789f95cdd293ba"),
                                            new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/db65929324925ccbfa789f95cdd293ba.pdf"))),
                            new Link("[Русская ссылка 1](:/ddc9f19456f64ade383ecdd76cf5b90d)",
                                    "Русская ссылка 1", ":/ddc9f19456f64ade383ecdd76cf5b90d", null),
                            new Link("[WinSCP](:/da4added37344f07a5ff2b9b2e1fdef3)", "WinSCP",
                                    ":/da4added37344f07a5ff2b9b2e1fdef3",
                                    new Resource(new ResourceId("da4added37344f07a5ff2b9b2e1fdef3"),
                                            new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/da4added37344f07a5ff2b9b2e1fdef3.txt")))
                    )), tuple("bf941399ecf6497b98693f618d2798bd", List.of(new Link(
                            "[Word Document](:/014ad7b70d5ba80ff06b897cb3dd8db5)",
                            "Word Document", ":/014ad7b70d5ba80ff06b897cb3dd8db5",
                            new Resource(
                                    new ResourceId("014ad7b70d5ba80ff06b897cb3dd8db5"),
                                    new File("/home/aleks/pr/home/yaal_examples/Java+/App+/Joplin/build/resources/main/joplin/common/resource/resources/014ad7b70d5ba80ff06b897cb3dd8db5.docx")
                            ))
                    )));
        }
    }
}