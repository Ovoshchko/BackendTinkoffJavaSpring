package edu.java.bot.link_validators;

import edu.java.bot.utils.LinkTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LinkValidationTest {

    private final static LinkValidation LINK_VALIDATION = new LinkValidation(List.of(
        new GithubLinkValidator(),
        new StackOverflowLinkValidator()
    ));

    public static Stream<Arguments> provideLinks() {
        return Stream.of(
            Arguments.of("https://www.digitalocean.com/community/tutorials/mockito-tutorial", LinkTypes.NOT_KNOWN_LINK),
            Arguments.of("https://stackoverflow.com", LinkTypes.VALID),
            Arguments.of("https://github.com", LinkTypes.VALID),
            Arguments.of("aboba", LinkTypes.NOT_LINK),
            Arguments.of("https://github.com/pengrad/java-telegram-bot-api", LinkTypes.VALID)
        );
    }

    @ParameterizedTest
    @MethodSource("provideLinks")
    void isValid(String link, LinkTypes answer) {
        LinkTypes result = LINK_VALIDATION.isValid(link);

        assertThat(result).isEqualTo(answer);
    }
}
