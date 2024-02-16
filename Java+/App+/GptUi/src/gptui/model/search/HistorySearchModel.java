package gptui.model.search;

import gptui.model.storage.Interaction;
import gptui.model.storage.InteractionId;

import java.util.List;

public interface HistorySearchModel {
    List<InteractionId> search(String text);

    void indexDocuments(List<Interaction> interactions);

    void indexDocument(Interaction interaction);
}
