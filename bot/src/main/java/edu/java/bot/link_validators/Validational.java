package edu.java.bot.link_validators;

import edu.java.bot.utils.LinkTypes;
import java.net.URL;

public interface Validational {
    LinkTypes isValid(URL url);
}
