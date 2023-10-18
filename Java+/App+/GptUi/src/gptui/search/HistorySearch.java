package gptui.search;

import gptui.storage.Interaction;
import gptui.storage.InteractionId;

import java.util.List;

public interface HistorySearch {
    List<InteractionId> search(String text);

    void indexDocuments(List<Interaction> interactions);

    void indexDocument(Interaction interaction);
}
