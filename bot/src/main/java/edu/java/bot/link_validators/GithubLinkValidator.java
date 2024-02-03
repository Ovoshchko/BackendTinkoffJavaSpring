package edu.java.bot.link_validators;

import edu.java.bot.utils.LinkTypes;
import java.net.URL;
import org.springframework.stereotype.Component;

@Component
class GithubLinkValidator extends LinkValidator {

    private final String host = "github.com";

    GithubLinkValidator() {
        super(null);
    }

    @Override
    public LinkTypes isValid(URL url) {
        return url.getHost().equals(host) ? LinkTypes.VALID : LinkTypes.NOT_KNOWN_LINK;
    }
}
