package seedu.address.model.project;

import seedu.address.model.task.CompletableTodo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Represents a list of Todos.
 */
public class TodoList {

    private final List<CompletableTodo> todos = new ArrayList<>();

    /**
     * Constructs a empty {@code TodoList}.
     */
    public TodoList() {}

    /**
     * Constructs a {@code TodoList}.
     *
     * @param todos A list of {@code Todos}.
     */
    public TodoList(List<CompletableTodo> todos) {
        requireNonNull(todos);

        this.todos.addAll(todos);
    }

    public List<CompletableTodo> getCompletableTasks() {
        return this.todos;
    }

    /**
     * Returns a sequential stream with this {@code TodoList} as its source.
     * @return a sequential Stream over the Todos in this {@code TodoList}.
     */
    public Stream<CompletableTodo> stream() {
        return this.todos.stream();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TodoList // instanceof handles nulls
                && this.todos.equals(((TodoList) other).todos)); // state check
    }

    @Override
    public int hashCode() {
        return todos.hashCode();
    }
}
