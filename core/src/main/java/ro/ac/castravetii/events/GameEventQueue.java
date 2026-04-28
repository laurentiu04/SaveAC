package ro.ac.castravetii.events;

import java.util.*;

public class GameEventQueue extends ArrayDeque<GameEvent> {

    @SafeVarargs
    public final ArrayDeque<GameEvent> getEventsOfType(Class<? extends GameEvent>... types) {
        ArrayDeque<GameEvent> stack = new ArrayDeque<>();

        for (GameEvent event : this) {
            for (Class<? extends GameEvent> type : types) {
                if (type.isInstance(event)) {
                    stack.push(event);
                }
            }
        }

        return stack;
    }
}
