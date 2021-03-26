package seedu.address.model.project;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.project.exceptions.DuplicateEventException;
import seedu.address.model.task.repeatable.Event;

/**
 * Represents a list of Events.
 */
public class EventList {

    private final ObservableList<Event> events = FXCollections.observableArrayList();

    /**
     * Constructs an empty {@code EventList}.
     */
    public EventList () {}

    /**
     * Constructs an {@code EventList}.
     *
     * @param events A list of {@code Event}.
     */
    public EventList (List<Event> events) {
        requireNonNull(events);

        this.events.addAll(events);
    }

    /**
     * Adds an event to this {@code EventList}. The {@code Event} must not already exist in the {@code EventList}.
     *
     * @param event {@code Event} to add.
     */
    public void addEvent(Event event) {
        requireNonNull(event);

        if (hasEvent(event)) {
            throw new DuplicateEventException();
        }

        this.events.add(event);
    }

    /**
     * Set the {@code Event} specified by index with a new {@code Event}.
     *
     * @param index index specifies the target {@code Event}.
     * @param event new {@code Event} for this index.
     */
    public void setEvent(Integer index, Event event) {
        requireNonNull(event);

        this.events.set(index, event);
    }

    /**
     * Deletes an event from this {@code EventList}.
     *
     * @param i Index of {@code Event} to be deleted.
     */
    public void deleteEvent(Integer i) {
        requireNonNull(i);
        this.events.remove((int) i);
    }

    /**
     * Marks an event from this {@code EventList} as done.
     *
     * @param i Index of {@code Event} to be marked as done.
     */
    public void markAsDone(Integer i) {
        requireNonNull(i);
        this.events.get(i).markAsDone();
    }

    /**
     * Returns a copy of this {@code EventList}
     *
     * @return A copy of this {@code EventList}
     */
    public EventList getCopy() {
        return new EventList(getEvents());
    }

    /**
     * Returns a sequential stream with this {@code EventList} as its source.
     *
     * @return a sequential Stream over the events in this {@code EventList}.
     */
    public Stream<Event> stream() {
        return events.stream();
    }

    /**
     * Returns {@code events} as an {@code ObservableList<Event>}
     *
     * @return An {@code ObservableList<Event>}
     */
    public ObservableList<Event> getEvents() {
        return events;
    }

    /**
     * Returns all {@code Events} that fall on a specific {@code LocalDate}
     *
     * @param dateOfEvent The {@code LocalDate} which the events occur on.
     * @return A {@code FilteredList<Event>}
     */
    public FilteredList<Event> getEventsOnDate(LocalDate dateOfEvent) {
        requireNonNull(dateOfEvent);
        Predicate<Event> predicate = event -> event.getAt().isEqual(dateOfEvent);
        return events.filtered(predicate);
    }

    /**
     * Checks if the {@code EventList} already contains the specified {@code eventToCheck}.
     *
     * @param eventToCheck The event that is to be checked.
     * @return true if this project contains the specified event, false otherwise.
     */
    public boolean hasEvent(Event eventToCheck) {
        for (Event event: events) {
            if (eventToCheck.equals(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the size of the {@code EventList}.
     *
     * @return size of the {@code EventList}.
     */
    public int size() {
        return events.size();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && events.equals(((EventList) other).events)); // state check
    }

    @Override
    public int hashCode() {
        return events.hashCode();
    }

}
