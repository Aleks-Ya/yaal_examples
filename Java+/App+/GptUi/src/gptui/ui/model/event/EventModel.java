package gptui.ui.model.event;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Singleton
public class EventModel {
    private static final Logger log = LoggerFactory.getLogger(EventModel.class);
    private final Fire fire = new Fire();

    public void subscribe(EventListener listener) {
        log.debug("ModelListener subscribed: {}", listener);
        fire.subscribe(listener);
    }

    public Fire fire() {
        return fire;
    }

    public class Fire {
        private final Set<EventListener> listeners = new HashSet<>();

        public void stageShowed() {
            listeners.forEach(EventListener::stageWasShowed);
        }

        public void interactionChosenFromHistory(EventSource source) {
            fire("InteractionChosenFromHistory", source, EventListener::interactionChosenFromHistory);
        }

        public void themeWasChosen() {
            log.trace("Fire themeWasChosen");
            listeners.forEach(EventListener::themeWasChosen);
        }

        public void isThemeFilterHistoryChanged() {
            listeners.forEach(EventListener::isThemeFilterHistoryChanged);
        }

        public void interactionCreated() {
            listeners.forEach(EventListener::interactionCreated);
        }

        public void interactionDeleted() {
            listeners.forEach(EventListener::interactionDeleted);
        }

        public void answerUpdated() {
            listeners.forEach(EventListener::answerUpdated);
        }

        void subscribe(EventListener listener) {
            fire.listeners.add(listener);
        }

        private void fire(String eventName, EventSource source, Consumer<? super EventListener> consumer) {
            var selfListeners = getSelfListeners(source);
            var notSelfListeners = getNotSelfListeners(source);
            log.debug("Firing interaction '{}' to {} listeners (skip {} self-listeners) by {}...",
                    eventName, notSelfListeners.size(), selfListeners.size(), source.getName());
            notSelfListeners.forEach(consumer);
        }

        private List<EventListener> getNotSelfListeners(EventSource source) {
            return listeners.stream().filter(listener -> listener != source).toList();
        }

        private List<EventListener> getSelfListeners(EventSource source) {
            return listeners.stream().filter(listener -> listener == source).toList();
        }
    }
}
