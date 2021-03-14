package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TASK_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;

/**
 * Deletes a Todo identified using it's displayed index from the address book.
 */
public class DeleteTodoCommand extends Command {

    public static final String COMMAND_WORD = "deleteT";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the todo identified by it's index number within the displayed project.\n"
            + "Parameters: PROJECT_INDEX (must be a positive integer)"
            + PREFIX_REMOVE_TASK_INDEX + "TODO_INDEX \n"
            + "Example: " + COMMAND_WORD + " 1" + " "
            + PREFIX_REMOVE_TASK_INDEX + " 2";

    private final Index projectIndex;
    private final Index targetTodoIndex;

    public DeleteTodoCommand(Index projectIndex, Index targetTodoIndex) {
        this.projectIndex = projectIndex;
        this.targetTodoIndex = targetTodoIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteProject(projectToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_PROJECT_SUCCESS, projectToDelete));
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (projectIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        if (targetTodoIndex.getZeroBased() >= lastShownList.get(projectIndex.getZeroBased()).getTodos().getTodos().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TODO_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(projectIndex.getZeroBased());
        assert projectToEdit != null;

        projectToEdit(toAdd);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(Messages.MESSAGE_ADD_TODO_SUCCESS, toAdd));


        return new CommandResult(String.format(Messages.MESSAGE_DELETE_TODO_SUCCESS, personToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteTodoCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteTodoCommand) other).targetIndex)); // state check
    }
}
