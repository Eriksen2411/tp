package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteTodoCommand;

public class DeleteTodoCommandParserTest {

    private DeleteTodoCommandParser parser = new DeleteTodoCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index expectedProjectIndex = Index.fromOneBased(1);
        Index expectedTodoIndex = Index.fromOneBased(1);

        String userInput = INDEX_FIRST.getOneBased() + " " + PREFIX_INDEX + " " + INDEX_FIRST.getOneBased();

        DeleteTodoCommand command = new DeleteTodoCommand(expectedProjectIndex, expectedTodoIndex);

        // all field appear once
        assertParseSuccess(parser, userInput, command);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTodoCommand.MESSAGE_USAGE);

        String userInputMissingProject = PREFIX_INDEX + " " + INDEX_FIRST.getOneBased();

        // missing project index
        assertParseFailure(parser, userInputMissingProject, expectedMessage);

        String userInputMissingPrefix = INDEX_FIRST.getOneBased() + " " + INDEX_FIRST.getOneBased();

        // missing remove prefix
        assertParseFailure(parser, userInputMissingPrefix, expectedMessage);

        String userInputMissingTodo = INDEX_FIRST.getOneBased() + " " + PREFIX_INDEX;
        String expectedMessageMissingTodo = "Index is not a non-zero unsigned integer.";

        // missing remove prefix
        assertParseFailure(parser, userInputMissingTodo, expectedMessageMissingTodo);
    }

    @Test
    public void parse_invalidValue_failure() {
        Index validProjectIndex = Index.fromOneBased(1);
        Index validTodoIndex = Index.fromOneBased(1);

        String userInputInvalidProjectIndex = "0 " + PREFIX_INDEX + " "
                + validTodoIndex;

        // invalid project index
        assertParseFailure(parser, userInputInvalidProjectIndex, MESSAGE_INVALID_INDEX);

        String userInputInvalidTodoIndex = validProjectIndex + " " + PREFIX_INDEX + " 0";

        // invalid remove todo index
        assertParseFailure(parser, userInputInvalidTodoIndex,
                MESSAGE_INVALID_INDEX);

        String userInputInvalidProjectIndexExceeded = "10 " + PREFIX_INDEX + " "
                + validTodoIndex;

        // invalid project index
        assertParseFailure(parser, userInputInvalidProjectIndexExceeded, MESSAGE_INVALID_INDEX);

        String userInputInvalidTodoIndexExceeded = validProjectIndex + " " + PREFIX_INDEX + " 10";

        // invalid remove todo index
        assertParseFailure(parser, userInputInvalidTodoIndexExceeded,
                MESSAGE_INVALID_INDEX);
    }

}
