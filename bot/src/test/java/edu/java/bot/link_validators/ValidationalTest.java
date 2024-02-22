package edu.java.bot.link_validators;

import com.github.dockerjava.api.model.Link;
import edu.java.bot.commands.single_commands.Executable;
import edu.java.bot.utils.LinkTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Stack;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class ValidationalTest {

    private final static GithubLinkValidator GITHUB_LINK_VALIDATOR = new GithubLinkValidator();
    private final static StackOverflowLinkValidator STACK_OVERFLOW_LINK_VALIDATOR = new StackOverflowLinkValidator();

    public static Stream<Arguments> provideValidators() throws MalformedURLException {
        return Stream.of(
            Arguments.of(GITHUB_LINK_VALIDATOR, new URL("https://github.com"), LinkTypes.VALID),
            Arguments.of(GITHUB_LINK_VALIDATOR, new URL("https://stackoverflow.com"), LinkTypes.NOT_KNOWN_LINK),
            Arguments.of(STACK_OVERFLOW_LINK_VALIDATOR, new URL("https://stackoverflow.com"), LinkTypes.VALID),
            Arguments.of(STACK_OVERFLOW_LINK_VALIDATOR, new URL("https://github.com"), LinkTypes.NOT_KNOWN_LINK)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidators")
    void isValid(Validational validational, URL link, LinkTypes answer) {
        assertEquals(answer, validational.isValid(link));
    }
}
