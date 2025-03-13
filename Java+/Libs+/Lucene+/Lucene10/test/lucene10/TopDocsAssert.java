package lucene10;

import org.apache.lucene.document.Document;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TopDocsAssert extends AbstractAssert<TopDocsAssert, Collection<Document>> {
    public static TopDocsAssert assertThat(Collection<Document> actual) {
        return new TopDocsAssert(actual);
    }

    public TopDocsAssert(Collection<Document> indexableFields) {
        super(indexableFields, TopDocsAssert.class);
    }

    public TopDocsAssert contains(Collection<Document> docs) {
        isNotNull();
        var actStr = actual.stream().map(Objects::toString).toList();
        var expStr = docs.stream().map(Objects::toString).toList();
        Assertions.assertThat(actStr).containsAll(expStr);
        return this;
    }

    public TopDocsAssert contains(Document... docs) {
        return contains(Arrays.asList(docs));
    }

    public TopDocsAssert doesNotContain(Collection<Document> docs) {
        isNotNull();
        var actStr = actual.stream().map(Objects::toString).toList();
        var expStr = docs.stream().map(Objects::toString).toList();
        Assertions.assertThat(actStr).doesNotContainAnyElementsOf(expStr);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public TopDocsAssert doesNotContain(Document... docs) {
        return doesNotContain(Arrays.asList(docs));
    }

    public TopDocsAssert isNotEmpty() {
        isNotNull();
        Assertions.assertThat(actual).isNotEmpty();
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public TopDocsAssert isEmpty() {
        isNotNull();
        Assertions.assertThat(actual).isEmpty();
        return this;
    }
}
