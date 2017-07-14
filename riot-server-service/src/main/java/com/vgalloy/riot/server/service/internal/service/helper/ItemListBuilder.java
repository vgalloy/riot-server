package com.vgalloy.riot.server.service.internal.service.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vgalloy.riot.api.api.dto.mach.Event;

/**
 * Created by Vincent Galloy on 30/11/16.
 *
 * @author Vincent Galloy
 */
public final class ItemListBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemListBuilder.class);

    private final List<Action> actionList = new ArrayList<>();

    /**
     * Add an event.
     *
     * @param event the event
     * @return the builder
     */
    public ItemListBuilder addEvent(Event event) {
        Objects.requireNonNull(event, "event can not be null");

        try {
            switch (event.getEventType()) {
                case ITEM_PURCHASED:
                    actionList.add(new Action(event.getTimestamp(), Action.ActionType.BUY, event.getItemId()));
                    break;
                case ITEM_SOLD:
                    actionList.add(new Action(event.getTimestamp(), Action.ActionType.SOLD, event.getItemId()));
                    break;
                case ITEM_UNDO:
                    actionList.add(new Action(event.getTimestamp(), Action.ActionType.UNDO, event.getItemBefore()));
                    break;
                case ITEM_DESTROYED:
                    actionList.add(new Action(event.getTimestamp(), Action.ActionType.DESTROY, event.getItemId()));
                    break;
                default:
                    LOGGER.trace("Can not convert the event : {}", event);
                    break;
            }
        } catch (Exception e) {
            LOGGER.error("{}", event, e);
        }

        return this;
    }

    /**
     * Get the item List at the end of the game.
     *
     * @return the list of item at the end of the game
     */
    public List<Integer> getItemAtTheEnd() {
        Set<Integer> result = new HashSet<>();
        actionList.sort((o1, o2) -> Math.toIntExact(o1.getTimestamp() - o2.getTimestamp()));
        for (Action action : actionList) {
            if (Action.ActionType.BUY == action.getActionType()) {
                result.add(action.getItemId());
            } else {
                result.remove(action.getItemId());
            }
        }
        return new ArrayList<>(result);
    }
}
