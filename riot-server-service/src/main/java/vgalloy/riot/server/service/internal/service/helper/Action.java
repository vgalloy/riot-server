package vgalloy.riot.server.service.internal.service.helper;

import java.util.Objects;

/**
 * Created by Vincent Galloy on 10/06/17.
 *
 * @author Vincent Galloy
 */
final class Action {

    enum ActionType {
        BUY,
        SOLD,
        UNDO,
        DESTROY
    }

    private final long timestamp;
    private final ActionType actionType;
    private final Integer itemId;

    /**
     * Constructor.
     *
     * @param timestamp  the timestamp of the action
     * @param actionType the action type
     * @param itemId     the item id
     */
    Action(long timestamp, ActionType actionType, Integer itemId) {
        this.timestamp = timestamp;
        this.actionType = Objects.requireNonNull(actionType);
        this.itemId = Objects.requireNonNull(itemId);
    }

    long getTimestamp() {
        return timestamp;
    }

    ActionType getActionType() {
        return actionType;
    }

    Integer getItemId() {
        return itemId;
    }
}
