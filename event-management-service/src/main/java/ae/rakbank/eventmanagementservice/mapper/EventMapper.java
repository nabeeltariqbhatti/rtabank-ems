package ae.rakbank.eventmanagementservice.mapper;

import ae.rakbank.eventmanagementservice.dtos.event.UpdateEvent;
import ae.rakbank.eventmanagementservice.dtos.request.EventRequest;
import ae.rakbank.eventmanagementservice.dtos.response.EventResponse;
import ae.rakbank.eventmanagementservice.model.Event;
import org.springframework.stereotype.Component;


public class EventMapper {

    public static Event toEvent(EventRequest request) {
        return Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .venue(request.getVenue())
                .organizer(request.getOrganizer())
                .startDateTime(request.getStartDateTime())
                .endDateTime(request.getEndDateTime())
                .capacity(request.getCapacity())
                .ticketPrice(request.getTicketPrice())
                .tags(request.getTags())
                .status(Event.Status.ACTIVE)
                .stopBookingsBeforeMinutes(request.getStopBookingsBeforeMinutes())
                .build();
    }

    public static EventRequest toEventRequest(Event event) {
        EventRequest request = new EventRequest();
        request.setName(event.getName());
        request.setDescription(event.getDescription());
        request.setVenue(event.getVenue());
        request.setOrganizer(event.getOrganizer());
        request.setStartDateTime(event.getStartDateTime());
        request.setEndDateTime(event.getEndDateTime());
        request.setCapacity(event.getCapacity());
        request.setTicketPrice(event.getTicketPrice());
        request.setTags(event.getTags());
        request.setStopBookingsBeforeMinutes(event.getStopBookingsBeforeMinutes());
        return request;
    }


    public static EventResponse toEventResponse(Event event) {
        return EventResponse.builder()
                .id(event.getId())
                .name(event.getName())
                .code(event.getCode())
                .description(event.getDescription())
                .venue(event.getVenue())
                .organizer(event.getOrganizer())
                .startDateTime(event.getStartDateTime())
                .endDateTime(event.getEndDateTime())
                .capacity(event.getCapacity())
                .ticketPrice(event.getTicketPrice())
                .tags(event.getTags())
                .status(event.getStatus().name())
                .stopBookingsBeforeMinutes(event.getStopBookingsBeforeMinutes())
                .build();
    }

    public static UpdateEvent toUpdateEvent(Event event) {
        return UpdateEvent.builder()
                .eventName(event.getName())
                .code(event.getCode())
                .status(Event.Status.valueOf(event.getStatus().name()))
                .startDateTime(event.getStartDateTime().toString())
                .endDateTime(event.getEndDateTime().toString())
                .ticketPrice(event.getTicketPrice())
                .venue(event.getVenue())
                .eventId(event.getId())
                .capacity(event.getCapacity())
                .stopBookingBeforeMinutes(event.getStopBookingsBeforeMinutes())
                .build();
    }
}
