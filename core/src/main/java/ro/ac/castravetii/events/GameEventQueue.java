package ro.ac.castravetii.events;

import java.util.*;

public final class GameEventQueue extends HashMap<Class<? extends GameEvent>, ArrayDeque<GameEvent>> {
    public <T extends GameEvent> void post(T event) {
        this.computeIfAbsent(event.getClass(), k -> new ArrayDeque<>()).addLast(event);
    }

    @SafeVarargs
    public final ArrayDeque<GameEvent> getEvents(Class<? extends GameEvent>... types) {

        ArrayDeque<GameEvent> result = new ArrayDeque<>();
        for (Class<? extends GameEvent> type : types) {
            ArrayDeque<GameEvent> events = this.get(type);
            if (events != null) result.addAll(events);
        }

        return result;
    }

    public void clearAll() {
        this.clear();
    }
}
