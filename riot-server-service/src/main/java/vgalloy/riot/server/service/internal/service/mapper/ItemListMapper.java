package vgalloy.riot.server.service.internal.service.mapper;

import java.util.List;
import java.util.Objects;

import vgalloy.riot.api.api.dto.mach.Frame;
import vgalloy.riot.api.api.dto.mach.Timeline;
import vgalloy.riot.server.service.internal.service.helper.ItemListBuilder;

/**
 * Created by Vincent Galloy on 30/11/16.
 *
 * @author Vincent Galloy
 */
public final class ItemListMapper {

    /**
     * Constructor.
     * To prevent instantiation
     */
    private ItemListMapper() {
        throw new AssertionError();
    }

    /**
     * Extract the last item.
     *
     * @param timeline      the timeline
     * @param participantId the participant id
     * @return the item id list at the end of the game
     */
    public static List<Integer> map(Timeline timeline, int participantId) {
        Objects.requireNonNull(timeline);
        ItemListBuilder itemListBuilder = new ItemListBuilder();

        if (timeline.getFrames() != null) {
            timeline.getFrames().stream()
                    .filter(Objects::nonNull)
                    .map(Frame::getEvents)
                    .filter(Objects::nonNull)
                    .forEach(events -> events.stream()
                            .filter(Objects::nonNull)
                            .filter(event -> event.getParticipantId() != null)
                            .filter(event -> event.getParticipantId() == participantId)
                            .forEach(itemListBuilder::addEvent));
        }

        return itemListBuilder.getItemAtTheEnd();
    }
}
