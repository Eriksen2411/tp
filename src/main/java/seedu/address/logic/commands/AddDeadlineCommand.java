package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTERVAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REPEATABLE_DATE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.project.Project;
import seedu.address.model.task.repeatable.Event;

/**
 * Adds an deadline to a specified project in CoLAB.
 */
public class AddDeadlineCommand extends Command {

    public static final String COMMAND_WORD = "addDto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits event list of project identified by index.\n"
            + "Existing list will be appended with the input event.\n"
            + "Parameters:\nPROJECT_INDEX\n"
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_INTERVAL + "INTERVAL "
            + PREFIX_REPEATABLE_DATE + "REPEATABLE_DATE\n"
            + "Example:\n" + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Project meeting "
            + PREFIX_INTERVAL + "DAILY "
            + PREFIX_REPEATABLE_DATE + "24-04-2021";

    public static final String MESSAGE_EVENT_ADDED_SUCCESS = "New event added: %1$s";

    private final Index index;
    private final Event eventToAdd;


    /**
     * Creates an AddEventCommand to add specified {@code Event} to {@code Project} with {@code Index}.
     *
     * @param index index of {@code Project} to add event in the list.
     * @param event {@code Event} to add.
     */
    public AddDeadlineCommand(Index index, Event event) {
        requireAllNonNull(index, event);


        this.index = index;
        this.eventToAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Project> lastShownList = model.getFilteredProjectList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PROJECT_DISPLAYED_INDEX);
        }

        Project projectToEdit = lastShownList.get(index.getZeroBased());
        assert projectToEdit != null;
        projectToEdit.addEvent(eventToAdd);
        model.updateFilteredProjectList(Model.PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_EVENT_ADDED_SUCCESS, projectToEdit));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDeadlineCommand // instanceof handles nulls
                && eventToAdd.equals(((AddDeadlineCommand) other).eventToAdd));
    }
}
