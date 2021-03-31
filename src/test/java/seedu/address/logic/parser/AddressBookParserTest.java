package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_WEEKLY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.AddDeadlineCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddGroupmateCommand;
import seedu.address.logic.commands.AddTodoCommand;
import seedu.address.logic.commands.ClearContactCommand;
import seedu.address.logic.commands.DeleteContactCommand;
import seedu.address.logic.commands.DeleteDeadlineCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.DeleteGroupmateCommand;
import seedu.address.logic.commands.DeleteProjectCommand;
import seedu.address.logic.commands.DeleteTodoCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.UpdateContactCommand;
import seedu.address.logic.commands.UpdateContactCommand.UpdateContactDescriptor;
import seedu.address.logic.commands.ViewContactsCommand;
import seedu.address.logic.commands.ViewOverviewCommand;
import seedu.address.logic.commands.ViewTodayCommand;
import seedu.address.logic.commands.ViewTodosCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NameContainsKeywordsPredicate;
import seedu.address.model.groupmate.Groupmate;
import seedu.address.model.task.deadline.Deadline;
import seedu.address.model.task.repeatable.Event;
import seedu.address.model.task.todo.Todo;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.ContactUtil;
import seedu.address.testutil.DeadlineBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.GroupmateBuilder;
import seedu.address.testutil.GroupmateUtil;
import seedu.address.testutil.TodoBuilder;
import seedu.address.testutil.UpdateContactDescriptorBuilder;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Contact contact = new ContactBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(ContactUtil.getAddCommand(contact));
        assertEquals(new AddContactCommand(contact), command);
    }

    @Test
    public void parseCommand_addCto() throws Exception {
        Groupmate contact = new GroupmateBuilder().build();
        Index projectIndex = Index.fromOneBased(1);
        AddGroupmateCommand command = (AddGroupmateCommand) parser.parseCommand(
                GroupmateUtil.getAddGroupmateCommand(projectIndex, contact)
        );
        assertEquals(new AddGroupmateCommand(projectIndex, contact), command);
    }

    @Test
    public void parseCommand_addDto_success() throws Exception {
        Deadline deadline = new DeadlineBuilder().withDescription("CS2106 Tutorial")
                .withByDate(LocalDate.of(2020, 01, 01)).build();
        Index projectIndex = Index.fromOneBased(1);
        String addDToCommand = AddDeadlineCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial" + " " + PREFIX_DEADLINE_DATE + "01-01-2020";
        AddDeadlineCommand command = (AddDeadlineCommand) parser.parseCommand(addDToCommand);
        assertEquals(new AddDeadlineCommand(projectIndex, deadline), command);
    }

    @Test
    public void parseCommand_addEto_success() throws Exception {
        Event event = new EventBuilder().withDescription("CS2106 Tutorial")
                .withDate(LocalDate.of(2020, 01, 01)).withTime(LocalTime.of(17, 30)).withIsWeekly(false).build();
        Index projectIndex = Index.fromOneBased(1);
        String addEToCommand = AddEventCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial" + " "
                + PREFIX_EVENT_DATE + "01-01-2020" + " "
                + PREFIX_EVENT_TIME + "17:30" + " "
                + PREFIX_EVENT_WEEKLY + "n";
        AddEventCommand command = (AddEventCommand) parser.parseCommand(addEToCommand);
        assertEquals(new AddEventCommand(projectIndex, event), command);
    }

    @Test
    public void parseCommand_addTto_success() throws Exception {
        Todo todo = new TodoBuilder().withDescription("CS2106 Tutorial").build();
        Index projectIndex = Index.fromOneBased(1);
        String inputCommand = AddTodoCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased() + " "
                + PREFIX_DESCRIPTION + "CS2106 Tutorial";
        AddTodoCommand command = (AddTodoCommand) parser.parseCommand(inputCommand);
        assertEquals(new AddTodoCommand(projectIndex, todo), command);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
                DeleteContactCommand.COMMAND_WORD + " " + INDEX_FIRST.getOneBased());
        assertEquals(new DeleteContactCommand(INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteP() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        String deleteProjectCommandLine = DeleteProjectCommand.COMMAND_WORD + " " + projectIndex.getOneBased();
        DeleteProjectCommand command = (DeleteProjectCommand) parser.parseCommand(deleteProjectCommandLine);
        assertEquals(new DeleteProjectCommand(projectIndex), command);
    }

    @Test
    public void parseCommand_deleteCfrom() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        DeleteGroupmateCommand command = (DeleteGroupmateCommand) parser.parseCommand(
                DeleteGroupmateCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                + PREFIX_INDEX + " " + projectIndex.getOneBased()
        );
        assertEquals(new DeleteGroupmateCommand(INDEX_FIRST, INDEX_FIRST), command);
    }

    @Test
    public void parseCommand_deleteD() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index deadlineIndex = Index.fromOneBased(1);

        DeleteDeadlineCommand command = (DeleteDeadlineCommand) parser.parseCommand(
                DeleteDeadlineCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_INDEX + " " + deadlineIndex.getOneBased()
        );

        assertEquals(new DeleteDeadlineCommand(projectIndex, deadlineIndex), command);
    }

    @Test
    public void parseCommand_deleteT() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index todoIndex = Index.fromOneBased(1);

        DeleteTodoCommand command = (DeleteTodoCommand) parser.parseCommand(
                DeleteTodoCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_INDEX + " " + todoIndex.getOneBased()
        );

        assertEquals(new DeleteTodoCommand(projectIndex, todoIndex), command);
    }

    @Test
    public void parseCommand_deleteE() throws Exception {
        Index projectIndex = Index.fromOneBased(1);
        Index eventIndex = Index.fromOneBased(1);

        DeleteEventCommand command = (DeleteEventCommand) parser.parseCommand(
                DeleteEventCommand.COMMAND_WORD + " " + projectIndex.getOneBased() + " "
                        + PREFIX_INDEX + " " + eventIndex.getOneBased()
        );

        assertEquals(new DeleteEventCommand(projectIndex, eventIndex), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD) instanceof ClearContactCommand);
        assertTrue(parser.parseCommand(ClearContactCommand.COMMAND_WORD + " 3") instanceof ClearContactCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Contact contact = new ContactBuilder().build();
        UpdateContactDescriptor descriptor = new UpdateContactDescriptorBuilder(contact).build();
        UpdateContactCommand command = (UpdateContactCommand) parser.parseCommand(
                UpdateContactCommand.COMMAND_WORD + " "
                + INDEX_FIRST.getOneBased() + " " + ContactUtil.getUpdateContactDescriptorDetails(descriptor));
        assertEquals(new UpdateContactCommand(INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindContactCommand command = (FindContactCommand) parser.parseCommand(
                FindContactCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindContactCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ViewContactsCommand.COMMAND_WORD) instanceof ViewContactsCommand);
        assertTrue(parser.parseCommand(ViewContactsCommand.COMMAND_WORD + " 3") instanceof ViewContactsCommand);
    }

    @Test
    public void parseCommand_today() throws Exception {
        assertTrue(parser.parseCommand(ViewTodayCommand.COMMAND_WORD) instanceof ViewTodayCommand);
        assertTrue(parser.parseCommand(ViewTodayCommand.COMMAND_WORD + " 3") instanceof ViewTodayCommand);
    }

    @Test
    public void parseCommand_tabO() throws Exception {
        assertTrue(parser.parseCommand(ViewOverviewCommand.COMMAND_WORD) instanceof ViewOverviewCommand);
        assertTrue(parser.parseCommand(ViewOverviewCommand.COMMAND_WORD + " 3")
                instanceof ViewOverviewCommand);
    }

    @Test
    public void parseCommand_tabT() throws Exception {
        assertTrue(parser.parseCommand(ViewTodosCommand.COMMAND_WORD) instanceof ViewTodosCommand);
        assertTrue(parser.parseCommand(ViewTodosCommand.COMMAND_WORD + " 3")
                instanceof ViewTodosCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
