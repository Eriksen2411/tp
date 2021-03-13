package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.todo.Todo;

import java.util.List;

public class AddTodoCommand extends Command {

    public static final String COMMAND_WORD = "addTto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to CoLAB. "
            + "Parameters: "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DESCRIPTION + "Submit project report ";

    public static final String MESSAGE_TODO_ADDED_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This todo already exists in CoLAB";

    private final Index index;
    private final Todo toAdd;

    /**
     * Creates an AddTodoCommand to add the specified {@code Task}
     * @param index index of {@code Project} to add event in the list.
     * @param todo {@code Todo} to add.
     */
    public AddTodoCommand(Index index, Todo todo) {
        requireNonNull(todo);
        this.index = index;
        this.toAdd = todo;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (this.index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        if (this.index.getZeroBased() < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(this.index.getZeroBased());
        assert projectToEdit != null;
        projectToEdit.addTodo(toAdd);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_TODO_ADDED_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTodoCommand // instanceof handles nulls
                && toAdd.equals(((AddTodoCommand) other).toAdd));
    }

}
