package edu.java.bot.link_validators;

import edu.java.bot.utils.LinkTypes;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkValidation {

    LinkValidator linkValidator;

    @Autowired
    public LinkValidation(List<LinkValidator> validators) {
        linkValidator = createValidationLink(validators);
    }

    public LinkTypes isValid(String link) {
        try {
            URL url = new URL(link);
            if (linkValidator != null) {
                if (linkValidator.isValid(url) == LinkTypes.VALID) {
                    return LinkTypes.VALID;
                }
                if (linkValidator.getNext() != null) {
                    return linkValidator.getNext().isValid(url);
                }
            }
        } catch (MalformedURLException e) {
            return LinkTypes.NOT_LINK;
        }
        return LinkTypes.NOT_KNOWN_LINK;
    }

    private LinkValidator createValidationLink(List<LinkValidator> validators) {
        LinkValidator lastValidator = null;
        for (LinkValidator validator : validators) {
            validator.setNext(lastValidator);
            lastValidator = validator;
        }
        return lastValidator;
    }
}
